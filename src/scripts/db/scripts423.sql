/*
Составить первый JOIN-запрос, чтобы получить информацию обо всех студентах
(достаточно получить только имя и возраст студента) школы Хогвартс вместе с названиями факультетов.

Составить второй JOIN-запрос, чтобы получить только тех студентов, у которых есть аватарки.

В корне проекта создать файл scripts423.sql и поместить в него запрос.*/

SELECT s.name, s.age, f.name FROM students s LEFT JOIN faculties f ON s.faculty_id = f.id;
SELECT s.* FROM students s JOIN avatars a ON a.student_id= s.id;