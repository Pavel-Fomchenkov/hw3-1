package pro.sky.javacoursepart3.hw31.service;

import pro.sky.javacoursepart3.hw31.model.Faculty;
import pro.sky.javacoursepart3.hw31.model.Student;

import java.util.Collection;

public interface StudentService {

    Collection<Student> getAll();

    Collection<Student> getStudentsByAge(Integer age);

    Collection<Student> getStudentsBetweenAge(Integer fromAge, Integer toAge);

    Student add(Student student);

    Student find(Long id);

    Faculty getFaculty(Long id);

    Student edit(Long id, Student student);

    void delete(Long id);

}
