package pro.sky.javacoursepart3.hw31.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pro.sky.javacoursepart3.hw31.model.Faculty;
import pro.sky.javacoursepart3.hw31.model.Student;
import pro.sky.javacoursepart3.hw31.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
//    If spring.jpa.hibernate.ddl-auto= update in application.properties, this method will add additional entries to hogwarts Db
//    @PostConstruct
//    public void init() {
//        add(new Faculty("Не выбран", "не имеет"));
//        add(new Faculty("Гриффиндор", "Красный"));
//        add(new Faculty("Слизерин", "Зелёный"));
//        add(new Faculty("Когтевран", "Синий"));
//        add(new Faculty("Пуффендуй", "Жёлтый"));
//    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getFacultyByNameOrColor(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public Collection<Student> getStudents(Long id) {
        return facultyRepository.findById(id)
                .map(Faculty::getStudents).orElse(null);
    }

    @Override
    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty find(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty edit(Long id, Faculty faculty) {
        return facultyRepository.findById(id)
                .map(found -> {
                    found.setName(faculty.getName());
                    found.setColor(faculty.getColor());
                    return facultyRepository.save(found);
                })
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        facultyRepository.deleteById(id);
    }
}
