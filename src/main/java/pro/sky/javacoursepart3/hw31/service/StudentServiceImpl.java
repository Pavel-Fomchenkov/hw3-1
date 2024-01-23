package pro.sky.javacoursepart3.hw31.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pro.sky.javacoursepart3.hw31.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private Long lastId = 0L;

    @PostConstruct
    public void init() {
        add(new Student("Гарри Поттер", 12));
        add(new Student("Драко Малфой", 13));
        add(new Student("Седрик Диггори", 16));
        add(new Student("Падма Патил", 15));
        add(new Student("Питер Петтигрю", 27));
    }

    @Override
    public List<Student> getAll() {
        return students.values().stream().toList();
    }

    public List<Student> getStudentsByAge(int age) {
        return students.values().stream()
                .filter(v -> v.getAge() == age)
                .toList();
    }

    @Override
    public Student add(Student student) {
        student.setId(++lastId);
        students.put(lastId, student);
        return students.get(lastId);
    }

    @Override
    public Student find(Long id) {
        return students.get(id);
    }

    @Override
    public Student edit(Long id, Student student) {
        if (students.containsKey(id)) {
            student.setId(id);
            students.put(id, student);
            return students.get(id);
        }
        return null;
    }

    @Override
    public Student delete(Long id) {
        return students.remove(id);
    }
}
