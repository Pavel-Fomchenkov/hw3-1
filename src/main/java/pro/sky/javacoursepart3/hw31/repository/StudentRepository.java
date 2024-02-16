package pro.sky.javacoursepart3.hw31.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.javacoursepart3.hw31.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    //    @Query("SELECT pos FROM students pos WHERE pos.age = age") запрос, вероятно, не верен
    Collection<Student> findByAge(int age);

    //    @Query("SELECT * FROM students WHERE age >= fromAge AND age < toAge")
    Collection<Student> findByAgeBetween(int fromAge, int toAge);

    @Query(value = "SELECT COUNT(*) FROM students", nativeQuery = true)
    Long getStudentsQuantity();

    @Query(value = "SELECT ROUND (AVG(age), 0) FROM students", nativeQuery = true)
    Integer getAverageAge();

    //    @Query(value = "SELECT * FROM students WHERE id IN (SELECT id FROM students ORDER BY id DESC LIMIT 5) ORDER BY id asc", nativeQuery = true)
    @Query(value = "SELECT * FROM students ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> getLast5();
}
