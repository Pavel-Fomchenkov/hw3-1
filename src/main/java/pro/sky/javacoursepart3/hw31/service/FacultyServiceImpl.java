package pro.sky.javacoursepart3.hw31.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pro.sky.javacoursepart3.hw31.model.Faculty;
import pro.sky.javacoursepart3.hw31.repository.FacultyRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
//    If spring.jpa.hibernate.ddl-auto= update (application.properties), this method will add redundant entries to myDb
//    @PostConstruct
//    public void init() {
//        add(new Faculty("Не выбран", "не имеет"));
//        add(new Faculty("Гриффиндор", "Красный"));
//        add(new Faculty("Слизерин", "Зелёный"));
//        add(new Faculty("Когтевран", "Синий"));
//        add(new Faculty("Пуффендуй", "Жёлтый"));
//    }

    @Override
    public List<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    @Override
    public List<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    @Override
    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty find(Long id) {
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty edit(Long id, Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public void delete(Long id) {
        facultyRepository.deleteById(id);
    }
}
