package pro.sky.javacoursepart3.hw31.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.javacoursepart3.hw31.model.Faculty;
import pro.sky.javacoursepart3.hw31.model.Student;
import pro.sky.javacoursepart3.hw31.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

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
        logger.info("Was invoked method StudentService.getAll()");
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAge(Integer age) {
        logger.info("Was invoked method StudentService.getStudentsByAge({})", age);
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> getStudentsBetweenAge(Integer fromAge, Integer toAge) {
        if (fromAge == null) fromAge = Integer.MIN_VALUE;
        if (toAge == null) toAge = Integer.MAX_VALUE;
        logger.info("Was invoked method StudentService.getStudentsBetweenAge({},{})", fromAge, toAge);
        return studentRepository.findByAgeBetween(fromAge, toAge);
    }

    @Override
    public Student add(Student student) {
        logger.info("Was invoked method StudentService.add({})", student);
        return studentRepository.save(student);
    }

    @Override
    public Student find(Long id) {
        logger.info("Was invoked method StudentService.find({})", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Faculty getFaculty(Long id) {
        logger.info("Was invoked method getFaculty({})", id);
        logger.debug("Method getFaculty(id) executes findById(id)");
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .orElse(null);
    }

    @Override
    public Student edit(Long id, Student student) {
        logger.info("Was invoked method StudentService.edit({}, {})", id, student);
        logger.debug("Method edit(id, student) executes findById(id)");
        return studentRepository.findById(id)
                .map(found -> {
                    found.setName(student.getName());
                    found.setAge(student.getAge());
                    return studentRepository.save(found);
                })
                .orElse(null);
    }

    @Override
    public Student delete(Long id) {
        logger.info("Was invoked method StudentService.delete({})", id);
        logger.debug("Method delete(id) executes findById(id)");
        Student studentToDelete = studentRepository.findById(id).orElse(null);
        studentRepository.deleteById(id);
        return studentToDelete;
    }

    @Override
    public Long getStudentsQuantity() {
        logger.info("Was invoked method StudentService.getStudentsQuantity()");
        return studentRepository.getStudentsQuantity();
    }

    @Override
    public Integer getAverageAge() {
        logger.info("Was invoked method StudentService.getAverageAge()");
        return studentRepository.getAverageAge();
    }

    @Override
    public Collection<Student> getLast5() {
        logger.info("Was invoked method StudentService.getLast5()");
        return studentRepository.getLast5();
    }

    @Override
    public List<String> getA() {
       return studentRepository.findAll()
               .parallelStream()
               .map(s -> s.getName().toUpperCase())
               .sorted()
               .filter(s-> s.startsWith("А") || s.startsWith("A")) // Latin or Cyrillic A
               .collect(Collectors.toList());
    }

    @Override
    public Double getAverageAgeDouble() {
        return studentRepository.findAll()
                .parallelStream()
                .mapToInt(s -> s.getAge())
                .average().orElse(0.0);
    }
}
