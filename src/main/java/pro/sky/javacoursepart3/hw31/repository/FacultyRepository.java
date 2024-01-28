package pro.sky.javacoursepart3.hw31.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.javacoursepart3.hw31.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}
