package pro.sky.javacoursepart3.hw31.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import pro.sky.javacoursepart3.hw31.model.Faculty;
import pro.sky.javacoursepart3.hw31.model.Student;
import pro.sky.javacoursepart3.hw31.repository.FacultyRepository;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTemplateTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    private Long studentId = 0L;
    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    public void testPostStudent() throws Exception {
        // given
        Student student = new Student("Test student", 99);
        // when
        ResponseEntity<Student> responseEntity = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );
        // then
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Student studentFromDb = responseEntity.getBody();
        Assertions.assertThat(studentFromDb.getName()).isEqualTo("Test student");
        Assertions.assertThat(studentFromDb.getAge()).isEqualTo(99);
        Assertions.assertThat(studentFromDb.getFaculty()).isNull();
        // delete test data
        deleteStudent(studentFromDb.getId());
    }

    @Test
    void getStudentsNoParameters() throws Exception {
        // given
        for (int i = 0; i < 2; i++) {
            this.testRestTemplate.postForEntity(
                    "http://localhost:" + port + "/student",
                    new Student("Test student", 98),
                    Student.class
            );
        }
        // when
        ResponseEntity<Collection<Student>> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/student",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Student>>() {
                });
        // then
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Collection<Student> students = responseEntity.getBody();
        Assertions.assertThat(
                        students.stream()
                                .filter(student -> student.getName().equals("Test student"))
                                .toList()
                                .size())
                .isEqualTo(2);
        // delete test data
        deleteTestData();
    }

    @Test
    void getStudentsIntegerAgeParameter() throws Exception {
        // given
        for (int i = 0; i < 2; i++) {
            this.testRestTemplate.postForEntity(
                    "http://localhost:" + port + "/student",
                    new Student("Test student", 97),
                    Student.class
            );
        }
        this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                new Student("Test student", 98),
                Student.class
        );
        // when
        ResponseEntity<Collection<Student>> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/student?age=97",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Student>>() {
                });
        // then
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Collection<Student> students = responseEntity.getBody();
        Assertions.assertThat(
                        students.stream()
                                .filter(student -> student.getName().equals("Test student"))
                                .toList()
                                .size())
                .isEqualTo(2);
        // delete test data
        deleteTestData();
    }

    @Test
    void getStudentsIntegerFromAgeAndToAgeParameters() throws Exception {
        // given
        this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                new Student("Test student", 93),
                Student.class
        );
        for (int i = 0; i < 2; i++) {
            this.testRestTemplate.postForEntity(
                    "http://localhost:" + port + "/student",
                    new Student("Test student", 94),
                    Student.class
            );
        }
        this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                new Student("Test student", 95),
                Student.class
        );
        // when
        ResponseEntity<Collection<Student>> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/student?fromAge=94&toAge=95",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Student>>() {
                });
        // then
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Collection<Student> students = responseEntity.getBody();
        Assertions.assertThat(
                        students.stream()
                                .filter(student -> student.getName().equals("Test student"))
                                .toList()
                                .size())
                .isEqualTo(3);
        // delete test data
        deleteTestData();
    }

    @Test
    void find() throws Exception {
        // given
        createTestStudentAndSetStudentId();
        // when
        ResponseEntity<Student> responseEntity = this.testRestTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + studentId,
                Student.class);
        // then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Student studentFound = responseEntity.getBody();
        Assertions.assertThat(studentFound.getId()).isEqualTo(studentId);
        Assertions.assertThat(studentFound.getName()).isEqualTo("Test student");
        // delete test data
        deleteStudent(studentId);
        responseEntity = this.testRestTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + studentId,
                Student.class);
        Assertions.assertThat(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)).isTrue();
    }

    @Test
    void findFaculty() throws Exception {
        // given
        Student testStudent = new Student("Test student", 92);
        testStudent = this.testRestTemplate.postForObject("http://localhost:" + port + "/student", testStudent, Student.class);
        Long studentId = testStudent.getId();
        Faculty testFaculty = new Faculty("Test Faculty", "Test color");
        testFaculty = facultyRepository.save(testFaculty);
        testStudent.setFaculty(testFaculty);
        this.testRestTemplate.postForObject("http://localhost:" + port + "/student", testStudent, Student.class);
        // when
        ResponseEntity<Faculty> responseEntity = this.testRestTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + studentId + "/faculty",
                Faculty.class);
        // then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Faculty facultyFound = responseEntity.getBody();
        Assertions.assertThat(facultyFound.getName()).isEqualTo("Test Faculty");
        Assertions.assertThat(facultyFound.getColor()).isEqualTo("Test color");
        // delete test data
        deleteTestData();
        Long facultyId = facultyFound.getId();
        facultyRepository.deleteById(facultyId);
    }

    @Test
    void edit() throws Exception {
        // given
        createTestStudentAndSetStudentId();
        Student upToDateStudent = new Student("Test student", 15);
        // when
        HttpEntity<Student> entity = new HttpEntity<Student>(upToDateStudent);
        ResponseEntity<Student> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/student/" + studentId,
                HttpMethod.PUT,
                entity,
                Student.class
        );
        // then
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Student studentFromDB = responseEntity.getBody();
        Assertions.assertThat(upToDateStudent.getAge()).isEqualTo(studentFromDB.getAge());
        // delete test data
        deleteTestData();
    }

    @Test
    void testDeleteStudentById() throws Exception {
        // given
        createTestStudentAndSetStudentId();
        // when
        ResponseEntity<Student> responseEntity = this.testRestTemplate.exchange(
                "http://localhost:" + port + "/student/" + studentId,
                HttpMethod.DELETE,
                null,
                Student.class);
        // then
        Student deletedStudent = responseEntity.getBody();
        Assertions.assertThat(deletedStudent.getId()).isEqualTo(studentId);
        Assertions.assertThat(deletedStudent.getName()).isEqualTo("Test student");
        Assertions.assertThat(deletedStudent.getAge()).isEqualTo(99);
        Assertions.assertThat(testRestTemplate.getForEntity(
                                "http://localhost:" + port + "/student/" + studentId, Student.class)
                        .getStatusCode()
                        .equals(HttpStatus.NOT_FOUND))
                .isTrue();
        // delete test data
        deleteTestData();
    }

    private void createTestStudentAndSetStudentId() {
        Student student = new Student();
        student.setName("Test student");
        student.setAge(99);
        this.studentId = this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class).getId();
    }

    private void deleteStudent(Long id) {
        this.testRestTemplate.delete("http://localhost:" + port + "/student/" + id);
    }

    private void deleteTestData() {
        testRestTemplate.exchange(
                        "http://localhost:" + port + "/student",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Collection<Student>>() {
                        })
                .getBody()
                .stream()
                .filter(student -> student.getName().equals("Test student"))
                .forEach(student -> deleteStudent(student.getId()));
    }

}