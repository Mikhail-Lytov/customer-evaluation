# Инструкции по сборке и настройке

##  Инструкция по сборке 

### Инструкция по сборке через исходники

Обязательные условия
- Java 17
- PostgreSQL версии 14 и выше
- Maven сборщик

Сборка приложения:
```bash
mvn clean package
```

Запуск приложения:
```bash
java -jar /{you_dir}.jar -Xmx4g -Xms4g  -Dfile.encoding=UTF-8 -Dcom.sun.net.ssl.checkRevocation=false
```

### Инструкция по сборке через docker

dockerFIle:
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

docker-compose.yml:

```dockerfile
version: '3'

services:
  demo-project-camunda:
    build: ./customer-evaluation
    container_name: customer
    ports:
      - "9999:9999"
    restart: unless-stopped
    environment:
      SPRING_APPLICATION_JSON: '{
                                     "SWAGGER_BASE_URL":"http://localhost:",
                                     "BD_USER_NAME":"postgres",
                                     "BD_PASSWORD":"8267",
                                     "DB_HOST":"postgres_bd",
                                     "DB_PORT":"5432",
                                     "DB_NAME":"demo_project_camunda",
                                     "DRIVER":"org.postgresql.Driver",
                                     "PORT":"9999",
                                     "DDL_AUTO":"create"
      }'
    depends_on:
      - postgresdb
    volumes:
      - .m2:/root/.m2

    stdin_open: true
    tty: true


  postgresdb:
    container_name: postgres_bd
    # образ базы данных
    image: postgres:15
    ports:
      - "5433:5432"
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=8267
      - POSTGRES_DB=demo_project_camunda
    # хранить в не контейнера
    volumes:
      - db:/var/lib/postgres

volumes:
  db:
```

```bash
docker-compose up
```

## Инструкция по настройке

**PORT** - порт на котором расположено приложение

**SWAGGER_URL** - url по какой ссылке расположен swagger 

**SWAGGER_TITLE** - Описание титульной страницы swagger

**CAMUNDA_USER** - Базовый логин пользователя Camunda

**CAMUNDA_PASSWORD** - Базовый пароль пользователя Camunda

**AUTHORIZATION_ENABLED** - Включает или отключает механизм авторизации для Camunda BPM. По умолчанию включен

**MAX-jobs-per-acquisition** - Устанавливает формат сериализации по умолчанию для данных в Camunda BPM. По умолчанию установлен application/json.

**MAX-POOL-SIZE** - Устанавливает максимальный размер пула потоков для обработки задач.

**ID-GENERATOR** -  Определяет стратегию генерации идентификаторов для объектов в Camunda BPM.

**HISTORY-TIME-TO-LIVE** - Устанавливает время хранения исторических данных в Camunda BPM.

**HISTORY-CLEANANUP-BATCH-WINDOW-START-TIME** - Устанавливает время начала окна, в котором Camunda BPM будет выполнять очистку старых исторических данных.

**HISTORY-CLEANANUP-BATCH-WINDOW-END-TIME** - Устанавливает время окончания окна, в котором Camunda BPM будет выполнять очистку старых исторических данных.

**HISTORY-CLEANUP-STRATEGY** - Определяет стратегию очистки старых исторических данных.

**SERVICE_NAME** - Имя Сервиса

**SERVICE_VERSION** - Версия сервиса

**DB_HOST** - хост вашей бд

**DB_PORT** - порт вашей бд

**DB_NAME** - имя вашей бд

**SECRET_DB_USER_NAME** - пользователь вашей бд

**SECRET_DB_USER_PASSWORD** - пароль от пользователя бд

**DRIVER_BD** - Драйвер базы данных

**DDL_AUTO** - Задает режим автоматического создания/обновления таблиц в базе данных.
- `create`: Создайте схему базы данных при запуске приложения. (При создании будет удалена старая версия)
- `create-drop`: Создайте схему базы данных при запуске приложения и удалите ее при завершении работы приложения.
- `update`: Обновляйте схему базы данных при внесении изменений в свои объекты.
- `validate`: Проверьте соответствие схемы базы данных метаданным
- `none`: отключение функции

**TOKEN_KEY** - Токен для авторизации в систему