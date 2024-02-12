package pro.sky.javacoursepart3.hw31.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.javacoursepart3.hw31.model.Faculty;
import pro.sky.javacoursepart3.hw31.model.Student;
import pro.sky.javacoursepart3.hw31.service.AvatarService;
import pro.sky.javacoursepart3.hw31.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerWebMVCTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    @MockBean
    private AvatarService avatarService;
    @InjectMocks
    private StudentController studentController;

    @Test
    void add() throws Exception {
        // given
        Long studentId = 1L;
        Student student = new Student("Петр Первый", 10);
        Student savedStudent = new Student("Петр Первый", 10);
        savedStudent.setId(studentId);
        when(studentService.add(student)).thenReturn(savedStudent);
        // when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)));
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedStudent.getId()))
                .andExpect(jsonPath("$.name").value(savedStudent.getName()))
                .andExpect(jsonPath("$.age").value(savedStudent.getAge()))
                .andDo(print());
    }

    @Test
    void getStudents() throws Exception {
        // given
        Student student0 = new Student("Петр Первый", 15);
        Student student1 = new Student("Александр Второй", 14);
        Long student0Id = 1L;
        Long student1Id = 2L;
        student0.setId(student0Id);
        student1.setId(student1Id);
        int age = 15;
        int fromAge = 14;
        int toAge = 15;
        when(studentService.getAll()).thenReturn(new ArrayList<>(List.of(student0, student1)));
        when(studentService.getStudentsByAge(age)).thenReturn(new ArrayList<>(List.of(student0)));
        when(studentService.getStudentsBetweenAge(fromAge, toAge)).thenReturn(new ArrayList<>(List.of(student0)));
        // when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get("/student")
        );
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student0.getName()))
                .andExpect(jsonPath("$[1].name").value(student1.getName()))
                .andExpect(jsonPath("$[0].age").value(student0.getAge()))
                .andExpect(jsonPath("$[1].age").value(student1.getAge()))
                .andDo(print());
        // when
        perform = mockMvc.perform(
                MockMvcRequestBuilders.get("/student?age=" + age)
        );
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student0.getName()))
                .andExpect(jsonPath("$[0].age").value(student0.getAge()))
                .andDo(print());
        // when
        perform = mockMvc.perform(
                MockMvcRequestBuilders.get("/student?fromAge=" + fromAge + "&toAge=" + toAge)
        );
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student0.getName()))
                .andExpect(jsonPath("$[0].age").value(student0.getAge()))
                .andDo(print());
    }

    @Test
    void find() throws Exception {
        // given
        Long studentId = 1L;
        Student student = new Student("Петр Первый", 10);
        Student savedStudent = new Student("Петр Первый", 10);
        savedStudent.setId(studentId);
        when(studentService.find(studentId)).thenReturn(savedStudent);
        // when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get("/student/" + studentId)
        );
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedStudent.getId()))
                .andExpect(jsonPath("$.name").value(savedStudent.getName()))
                .andExpect(jsonPath("$.age").value(savedStudent.getAge()))
                .andDo(print());
    }

    @Test
    void findFaculty() throws Exception {
        // given
        Long studentId = 1L;
        Faculty faculty = new Faculty("факультет", "цвет");
        Long facultyId = 2L;
        faculty.setId(facultyId);
        when(studentService.getFaculty(studentId)).thenReturn(faculty);
        // when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get("/student/" + studentId + "/faculty"));
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test
    void edit() throws Exception {
        // given
        Student student = new Student("Петр Третий", 7);
        Long studentId = 1L;
        student.setId(studentId);
        when(studentService.edit(studentId, student)).thenReturn(student);
        // when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.put("/student/" + studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student))
        );
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        // given
        Student student = new Student("Петр Третий", 7);
        Long studentId = 1L;
        student.setId(studentId);
        when(studentService.delete(studentId)).thenReturn(student);
        // when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.delete("/student/" + studentId)
        );
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }
}