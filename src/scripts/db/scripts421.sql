/*
 Возраст студента не может быть меньше 16 лет.
 Имена студентов должны быть уникальными и не равны нулю.
 Пара “значение названия” - “цвет факультета” должна быть уникальной.
 При создании студента без возраста ему автоматически должно присваиваться 20 лет.*/

alter table students add CONSTRAINT age_check CHECK (age >= 12);
alter table students add CONSTRAINT name_check UNIQUE (name);
alter table students alter COLUMN name SET NOT NULL;
alter table faculties add CONSTRAINT name_color_unique UNIQUE (name, color);
alter table students alter COLUMN age SET DEFAULT 20;

