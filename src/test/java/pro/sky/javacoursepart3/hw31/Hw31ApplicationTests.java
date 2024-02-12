package pro.sky.javacoursepart3.hw31;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import pro.sky.javacoursepart3.hw31.controller.AvatarController;
import pro.sky.javacoursepart3.hw31.controller.FacultyController;
import pro.sky.javacoursepart3.hw31.controller.StudentController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Hw31ApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private AvatarController avatarController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
        Assertions.assertThat(facultyController).isNotNull();
        Assertions.assertThat(avatarController).isNotNull();
    }
}
