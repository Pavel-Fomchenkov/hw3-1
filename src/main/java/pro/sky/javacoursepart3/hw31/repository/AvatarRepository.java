package pro.sky.javacoursepart3.hw31.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.javacoursepart3.hw31.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findByStudentId(Long studentId);
}
