package pro.sky.javacoursepart3.hw31.controller;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import pro.sky.javacoursepart3.hw31.model.Faculty;
import pro.sky.javacoursepart3.hw31.model.Student;
import pro.sky.javacoursepart3.hw31.repository.FacultyRepository;
import pro.sky.javacoursepart3.hw31.repository.StudentRepository;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerRestTemplateTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EntityManager session;

    @BeforeEach
    private void clearDb() {
        facultyRepository.deleteAll();
    }

    @Test
    void getFaculties() throws Exception {
        // given
        facultyRepository.save(new Faculty("Faculty0", "Color0"));
        facultyRepository.save(new Faculty("Faculty1", "Color1"));
        facultyRepository.save(new Faculty("Faculty2", "Color2"));
        // when
        ResponseEntity<Collection<Faculty>> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Faculty>>() {
                });
        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(3, responseEntity.getBody().size());
        assertEquals(1, responseEntity.getBody().stream()
                .filter(faculty -> faculty.getName().contains("Faculty1"))
                .toList()
                .size());
        assertEquals(1, responseEntity.getBody().stream()
                .filter(faculty -> faculty.getColor().contains("Color2"))
                .toList()
                .size());
    }

    @Test
    void getFacultyByNameOrColor() {
        // given
        facultyRepository.save(new Faculty("Faculty0", "Color0"));
        facultyRepository.save(new Faculty("Faculty1", "Color1"));
        facultyRepository.save(new Faculty("Faculty2", "Color2"));
        // when
        ResponseEntity<Collection<Faculty>> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty" + "?name=Faculty1&color=Color2",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Faculty>>() {
                });
        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(2, responseEntity.getBody().size());
        assertEquals(1, responseEntity.getBody().stream()
                .filter(faculty -> faculty.getColor().contains("Color1"))
                .toList()
                .size());
        assertEquals(1, responseEntity.getBody().stream()
                .filter(faculty -> faculty.getName().contains("Faculty2"))
                .toList()
                .size());
    }

    @Test
    void add() throws Exception {
        // given
        Faculty faculty = new Faculty("Faculty", "Color");
        HttpEntity<Faculty> entity = new HttpEntity<>(faculty);
        // when
        ResponseEntity<Faculty> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                entity,
                Faculty.class
        );
        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertTrue(responseEntity.getBody().getName().equals("Faculty") &&
                responseEntity.getBody().getColor().equals("Color"));
    }

    @Test
    void find() {
        // given
        Faculty faculty = new Faculty("Faculty", "Color");
        HttpEntity<Faculty> entity = new HttpEntity<>(faculty);
        ResponseEntity<Faculty> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                entity,
                Faculty.class
        );
        Long id = responseEntity.getBody().getId();
        // when
        ResponseEntity<Faculty> responseEntityF = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + id,
                Faculty.class
        );
        // then
        assertNotNull(responseEntityF);
        assertEquals(responseEntityF.getStatusCode(), HttpStatusCode.valueOf(200));
        assertTrue(responseEntityF.getBody().getName().equals("Faculty") &&
                responseEntityF.getBody().getColor().equals("Color"));
    }

    @Test
    void findStudents() {
        // given
        Faculty faculty = new Faculty("Test Faculty", "Test color");
        facultyRepository.save(faculty);
        Student student0 = new Student("Test student0", 10);
        studentRepository.save(student0);
        Student student1 = new Student("Test student1", 11);
        studentRepository.save(student1);
        student0.setFaculty(faculty);
        student1.setFaculty(faculty);
        studentRepository.save(student0);
        studentRepository.save(student1);
        // when
        ResponseEntity<Collection<Student>> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + faculty.getId() + "/students",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Student>>() {
                });
        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(2, responseEntity.getBody().size());
        assertEquals(1, responseEntity.getBody().stream()
                .filter(student -> student.getName().contains("Test student1"))
                .toList()
                .size());
    }

    @Test
    void edit() {
        // given
        Faculty faculty = new Faculty("Faculty", "Color");
        faculty = facultyRepository.save(faculty);
        Faculty facultyUpToDate = new Faculty("New name", "New color");
        HttpEntity<Faculty> entity = new HttpEntity<>(facultyUpToDate);
        // when
        ResponseEntity<Faculty> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                HttpMethod.PUT,
                entity,
                Faculty.class
        );
        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(faculty.getId(), responseEntity.getBody().getId());
        assertEquals(facultyUpToDate.getName(), responseEntity.getBody().getName());
        assertEquals(facultyUpToDate.getColor(), responseEntity.getBody().getColor());
    }

    @Test
    void delete() {
        // given
        Faculty faculty = new Faculty("Faculty", "Color");
        faculty = facultyRepository.save(faculty);
        // when
        ResponseEntity<Faculty> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                HttpMethod.DELETE,
                null,
                Faculty.class
        );
        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(faculty.getId(), responseEntity.getBody().getId());
        assertEquals(faculty.getName(), responseEntity.getBody().getName());
        assertEquals(faculty.getColor(), responseEntity.getBody().getColor());
    }
}