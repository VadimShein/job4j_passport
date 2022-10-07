[![Build Status](https://travis-ci.org/VadimShein/job4j_passport.svg?branch=master)](https://travis-ci.org/VadimShein/job4j_passport)

# <p align="center">Passport service</p>


## Описание:
Приложение реализует сервис для управления паспортами. 
Выполняет основные операции добавление, редактирование, обновление и просмотр данных о паспортах. 
Сервис периодически проверяет наличие просроченных паспортов и при нахождении таковых отправляет запрос 
через брокер сообщений к имитируемому сервису рассылки почты для уведомления
граждан о просроченности паспортов и необходимости их замены.


## Используемые технологии:
* Java 13, Rest API
* Spring Boot, Spring Data
* PostgreSQL, Hibernate
* Maven, Kafka


## Использование REST API
1. Сохранить данные паспорта
POST /passports/save

2. Обновить данные паспорта
PUT /passports/update

3. Удалить данные паспорта
DELETE /passports/delete/{id}

4. Загрузить все паспорта
GET /passports/find

5. Загрузить паспорт с определенной серией
GET /passports/find?series={series}

6. Загрузить паспорт с определенным номером
GET /passports/find?number={number}

7. Загрузить паспорт с определенной серией и определенным номером
GET /passports/find?series={series}&number={number}

8. Загрузить паспорта чей срок вышел
GET /passports/unavailable

9. Загрузить паспорта, которые нужно заменить в ближайшие 3 месяца
GET /passports/replaceable


## Запуск проекта:
1. Скопировать проект 
```
git clone https://github.com/VadimShein/job4j_passport
```

2. Создать базу данных passport и таблицы из файла src/main/resources/db/schema.sql

3. Собрать проект
```
mvn clean package -DskipTests
```

4. Запустить приложение, адрес по умолчанию  [http://localhost:8080/](http://localhost:8080/)
```
mvn spring-boot:run
```


## Контакты:
[![alt-text](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/SheinVadim)
[![alt-text](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:shein.v94@mail.ru)
