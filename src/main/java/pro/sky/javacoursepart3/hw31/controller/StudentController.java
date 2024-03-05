package pro.sky.javacoursepart3.hw31.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.javacoursepart3.hw31.model.Faculty;
import pro.sky.javacoursepart3.hw31.model.Student;
import pro.sky.javacoursepart3.hw31.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return studentService.add(student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getStudents(@RequestParam(required = false) Integer age,
                                                           @RequestParam(required = false) Integer fromAge,
                                                           @RequestParam(required = false) Integer toAge) {
        Collection<Student> st;
        if (age == null && fromAge == null && toAge == null) {
            st = studentService.getAll();
        } else if (age != null) {
            st = studentService.getStudentsByAge(age);
        } else {
            st = studentService.getStudentsBetweenAge(fromAge, toAge);
        }
        if (st == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(st);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> find(@PathVariable("id") Long id) {
        Student student = studentService.find(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("{id}/faculty")
    public Faculty findFaculty(@PathVariable("id") Long id) {
        return studentService.getFaculty(id);
    }


    @PutMapping("{id}")
    public ResponseEntity<Student> edit(@PathVariable Long id, @RequestBody Student student) {
        Student st = studentService.edit(id, student);
        if (st == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(st);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable("id") Long id) {
        Student st = studentService.delete(id);
        if (st == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(st);
    }

    @GetMapping("quantity")
    public Long getStudentsQuantity() {
        return studentService.getStudentsQuantity();
    }

    @GetMapping("averageAge")
    public Integer getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("last5")
    ResponseEntity<Collection<Student>> getLast5() {
        Collection<Student> st = studentService.getLast5();
        if (st.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(st);
    }

    @GetMapping("letterA")
    ResponseEntity<List<String>> getAStudents() {
        List<String> names = studentService.getA();
        if (names.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }

    @GetMapping("averageAgeDouble")
    ResponseEntity<Double> getAverageAgeAsDouble() {
        return ResponseEntity.ok(studentService.getAverageAgeDouble());
    }

    @GetMapping("print-parallel")
    public void printParallel() {
        studentService.printParallel();
    }

    @GetMapping("print-synchronized")
    public void printSynchronized() {
        studentService.printSynchronized();
    }
}