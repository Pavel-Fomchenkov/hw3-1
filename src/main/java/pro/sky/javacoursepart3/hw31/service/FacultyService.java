package pro.sky.javacoursepart3.hw31.service;

import pro.sky.javacoursepart3.hw31.model.Faculty;
import pro.sky.javacoursepart3.hw31.model.Student;

import java.util.Collection;

public interface FacultyService {
    Collection<Faculty> getAll();

    Collection<Faculty> getFacultyByNameOrColor(String name, String color);

    Collection<Student> getStudents(Long id);

    Faculty add(Faculty faculty);

    Faculty find(Long id);

    Faculty edit(Long id, Faculty faculty);

    void delete(Long id);
}

