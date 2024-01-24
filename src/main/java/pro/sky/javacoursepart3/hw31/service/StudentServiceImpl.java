package pro.sky.javacoursepart3.hw31.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pro.sky.javacoursepart3.hw31.model.Student;
import pro.sky.javacoursepart3.hw31.repository.StudentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

//    If spring.jpa.hibernate.ddl-auto= update, this method will add redundant entries to myDb
//    @PostConstruct
//    public void init() {
//        add(new Student("Гарри Поттер", 12));
//        add(new Student("Драко Малфой", 13));
//        add(new Student("Седрик Диггори", 16));
//        add(new Student("Падма Патил", 15));
//        add(new Student("Питер Петтигрю", 27));
//    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public Student add(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student find(Long id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public Student edit(Long id, Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}
