/*
В этом задании по описанию необходимо спроектировать таблицы,
связи между ними и корректно определить типы данных.
Здесь не важно, какой тип вы выберете, например, для данных,
представленных в виде строки (varchar или text).
Важно, что вы выберете один из строковых типов, а не числовых, например.

Описание структуры: у каждого человека есть машина.
Причем несколько человек могут пользоваться одной машиной.
У каждого человека есть имя, возраст и признак того, что у него есть права (или их нет).
У каждой машины есть марка, модель и стоимость.
Также не забудьте добавить таблицам первичные ключи и связать их.

В корне проекта нужно создать файл scripts422.sql и поместить в него запросы для создания таблиц.*/

CREATE TABLE people (
id long Primary Key,
licence boolean,
age integer NOT NULL,
);

CREATE TABLE cars (
id bigint Primary Key,
title varchar(25) NOT NULL,
model varchar(25) NOT NULL,
cost integer,
human_id bigint REFERENCES people(id)
);