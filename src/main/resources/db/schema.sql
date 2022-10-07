create table if not exists passports(
    id serial primary key,
    series int,
    number int,
    name varchar(255) not null,
    surname varchar(255) not null,
    patronymic varchar(255) not null,
    date_of_issue timestamp not null,
    due_date timestamp not null
);