-- liquibase formatted sql

-- changeset pavelf:1
CREATE INDEX students_name_index ON students (name);

-- changeset pavelf:2
CREATE INDEX faculties_name_index ON faculties (name);
CREATE INDEX faculties_name_color_index ON faculties (name, color);