# Инструкция по локальному запуску

## Локальный запуск через исходники

Обязательные условия
- java 17
- postgres version > 14
- Maven сборщик

### 1. Создание бд

Создайте базу данных в Postgres

Примените скрипт в бд для создании таблицы пользователей

```sql
create table users
(
    id       bigserial       not null
        constraint users_pkey
            primary key,
    email    varchar(255) not null
        constraint users_email_key
            unique,
    password varchar(255) not null,
    role     varchar(255) not null
        constraint users_role_check
            check ((role)::text = ANY
                   ((ARRAY ['ROLE_USER'::character varying, 'ROLE_ADMIN'::character varying])::text[])),
    username varchar(255) not null
        constraint users_username_key
            unique
);

alter table users
    owner to postgres;
```
Примените скрипт в бд для создании таблицы результатов

```sql
create table result_entity
(
    id       bigserial
        constraint result_entity_pk
            primary key,
    inn      boolean,
    region   boolean,
    capital  boolean,
    resident boolean,
    result   boolean,
    users    bigint
        constraint result_entity_users_id_fk
            references users
);
```
В конфигурацию **application.yml** впишите следующие переменные

- DB_HOST - хост вашей бд
- DB_PORT - порт вашей бд
- DB_NAME - имя вашей бд
- SECRET_DB_USER_NAME - пользователь вашей бд
- SECRET_DB_USER_PASSWORD - пароль от пользователя бд

### 2. Сборка проекта

С использованием mvn выполните следующий скрипт 
```bash
mvn clean package
```

Затем запустите своё приложение
```bash
java -jar /{you_dir}.jar -Xmx4g -Xms4g  -Dfile.encoding=UTF-8 -Dcom.sun.net.ssl.checkRevocation=false
```
**you_dir** - Директория где лежит ваш jar 

## Запуск через docker

```bash
docker build
```

```dockerfile
FROM maven:3.8.5-openjdk-18-slim as build-deps
WORKDIR /usr/scr/app
COPY . ./
RUN mvn clean package

FROM openjdk:18-slim-buster as base
ENV TZ=Europe/Moscow
RUN apt-get update && apt-get install -yy tzdata
RUN cp /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

FROM base as app
ARG JAR_FILE=/usr/scr/app/target/*.jar
COPY --from=build-deps ${JAR_FILE} app.jar
CMD ["java","-jar","/app.jar","-Xmx4g", "-Xms4g", "-Dfile.encoding=UTF-8","-Dcom.sun.net.ssl.checkRevocation=false"]

```

## Полезная информация

По дефолту swagger работы с приложением расположен по url: http://localhost:12001/swagger-ui/index.html