package pro.sky.javacoursepart3.hw31.service;

import pro.sky.javacoursepart3.hw31.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAll();

    List<Student> getStudentsByAge(int age);

    Student add(Student student);

    Student find(Long id);

    Student edit(Long id, Student student);

    void delete(Long id);

}
