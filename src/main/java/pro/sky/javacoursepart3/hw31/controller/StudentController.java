package pro.sky.javacoursepart3.hw31.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.javacoursepart3.hw31.model.Student;
import pro.sky.javacoursepart3.hw31.service.StudentService;

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
    public List<Student> getStudents(@RequestParam(required = false) Integer age) {
        if (age == null) {
            return studentService.getAll();
        }
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> find(@PathVariable("id") Long id) {
        Student student = studentService.find(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
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
    public Student delete(@PathVariable("id") Long id) {
        return studentService.delete(id);
    }
}