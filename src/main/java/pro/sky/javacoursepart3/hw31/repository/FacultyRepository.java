package pro.sky.javacoursepart3.hw31.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.javacoursepart3.hw31.model.Faculty;
import pro.sky.javacoursepart3.hw31.model.Student;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByColor(String color);
}
