package pro.sky.javacoursepart3.hw31.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pro.sky.javacoursepart3.hw31.model.Faculty;
import pro.sky.javacoursepart3.hw31.model.Student;
import pro.sky.javacoursepart3.hw31.repository.StudentRepository;

import java.util.Collection;


@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

//    If spring.jpa.hibernate.ddl-auto= update, this method will add redundant entries to hogwarts Db
//    @PostConstruct
//    public void init() {
//        add(new Student("Гарри Поттер", 12));
//        add(new Student("Драко Малфой", 13));
//        add(new Student("Седрик Диггори", 16));
//        add(new Student("Падма Патил", 15));
//        add(new Student("Питер Петтигрю", 27));
//    }

    @Override
    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAge(Integer age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> getStudentsBetweenAge(Integer fromAge, Integer toAge) {
        if (fromAge == null) fromAge = Integer.MIN_VALUE;
        if (toAge == null) toAge = Integer.MAX_VALUE;
        return studentRepository.findByAgeBetween(fromAge, toAge);
    }

    @Override
    public Student add(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student find(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Faculty getFaculty(Long id) {
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .orElse(null);
    }

    @Override
    public Student edit(Long id, Student student) {
        Student st = studentRepository.findById(id).orElse(null);
        if (st == null || student == null) {
            return null;
        } else {
            st.setName(student.getName());
            st.setAge(student.getAge());
            return st;
        }
    }

    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}
