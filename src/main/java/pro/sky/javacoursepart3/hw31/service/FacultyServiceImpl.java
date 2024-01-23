package pro.sky.javacoursepart3.hw31.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pro.sky.javacoursepart3.hw31.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long lastId = 0L;

    @PostConstruct
    public void init() {
        add(new Faculty("Не выбран", "не имеет"));
        add(new Faculty("Гриффиндор", "Красный"));
        add(new Faculty("Слизерин", "Зелёный"));
        add(new Faculty("Когтевран", "Синий"));
        add(new Faculty("Пуффендуй", "Жёлтый"));
    }

    @Override
    public List<Faculty> getAll() {
        return faculties.values().stream().toList();
    }

    @Override
    public List<Faculty> getFacultyByColor(String color) {
        return faculties.values().stream()
                .filter(v -> v.getColor().equals(color))
                .toList();
    }

    @Override
    public Faculty add(Faculty faculty) {
        faculty.setId(++lastId);
        faculties.put(lastId, faculty);
        return faculties.get(lastId);
    }

    @Override
    public Faculty find(Long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty edit(Long id, Faculty faculty) {
        if (faculties.containsKey(id)) {
            faculty.setId(id);
            faculties.put(id, faculty);
            return faculties.get(id);
        }
        return null;
    }

    @Override
    public Faculty delete(Long id) {
        return faculties.remove(id);
    }
}
