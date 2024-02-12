package pro.sky.javacoursepart3.hw31.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.javacoursepart3.hw31.model.Faculty;
import pro.sky.javacoursepart3.hw31.model.Student;
import pro.sky.javacoursepart3.hw31.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public Collection<Faculty> getFaculties(@RequestParam(required = false) String name, @RequestParam(required = false) String color) {
        if (name == null && color == null) {
            return facultyService.getAll();
        }
        return facultyService.getFacultyByNameOrColor(name, color);
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return facultyService.add(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> find(@PathVariable("id") Long id) {
        Faculty faculty = facultyService.find(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("{id}/students")
    Collection<Student> findStudents(@PathVariable("id") Long id) {
        return facultyService.getStudents(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<Faculty> edit(@PathVariable Long id, @RequestBody Faculty faculty) {
        Faculty fc = facultyService.edit(id, faculty);
        if (fc == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fc);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> delete(@PathVariable("id") Long id) {
        Faculty fc = facultyService.delete(id);
        if (fc == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fc);
    }
}