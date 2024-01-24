package pro.sky.javacoursepart3.hw31.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.javacoursepart3.hw31.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

//    @Query("SELECT pos FROM Student pos WHERE pos.age = age") запрос, вероятно, не верен
    List<Student> findByAge(int age);
}
