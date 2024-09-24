# Локальная сборка

## Локальный запуск через исходники
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

