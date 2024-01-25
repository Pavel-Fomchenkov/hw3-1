package pro.sky.javacoursepart3.hw31.service;

import pro.sky.javacoursepart3.hw31.model.Faculty;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    Collection<Faculty> getAll();

    Collection<Faculty> getFacultyByColor(String color);

    Faculty add(Faculty faculty);

    Faculty find(Long id);

    Faculty edit(Long id, Faculty faculty);

    void delete(Long id);
}

