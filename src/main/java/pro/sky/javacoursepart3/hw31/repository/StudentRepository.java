package pro.sky.javacoursepart3.hw31.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.javacoursepart3.hw31.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    //    @Query("SELECT pos FROM students pos WHERE pos.age = age") запрос, вероятно, не верен
    Collection<Student> findByAge(int age);

    //    @Query("SELECT * FROM students WHERE age >= fromAge AND age < toAge")
    Collection<Student> findByAgeBetween(int fromAge, int toAge);
}
