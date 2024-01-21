package pro.sky.javacoursepart3.hw31.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.javacoursepart3.hw31.model.Student;
import pro.sky.javacoursepart3.hw31.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {
    private final StudentServiceImpl out = new StudentServiceImpl();

    @BeforeEach
    private void setStudents() {
        out.add(new Student("Student0", 1));
        out.add(new Student("Student1", 10));
        out.add(new Student("Student2", 20));
        out.add(new Student("Student3", 30));
        out.add(new Student("Student4", 40));
    }

    @Test
    void getAllReturnsListOfStudents() {
        int studentsCountAfterSetFaculties = out.getAll().size();
        assertEquals(5, studentsCountAfterSetFaculties);
    }

    @Test
    void getStudentsByAgeReturnsListOfStudentsWithSpecifiedAgeOrEmptyList() {
        List<Student> found = out.getStudentsByAge(30);
        List<Student> empty = out.getStudentsByAge(999);
        assertEquals(1, found.size());
        assertEquals(0, empty.size());
        assertTrue(found.contains(new Student("Student3", 30)));
    }

    @Test
    void addAddStudentToMapAndReturnAddedStudent() {
        Student toAdd = new Student("Student5", 50);
        assertEquals(toAdd, out.add(toAdd));
        assertEquals(6, out.getAll().size());
    }

    @Test
    void findReturnsStudentWithSpecifiedIdOrElseNull() {
        Student found = out.find(2L);
        Student notFound = out.find(9L);
        assertEquals(new Student("Student1", 10), found);
        assertNull(notFound);
    }

    @Test
    void editReturnsModifiedStudent() {
        Student modified = new Student("modifiedStudent", 42);
        out.edit(4L, modified);
        assertTrue(out.getAll().contains(modified)
                && !out.getAll().contains(new Student("Student3", 42)));
        assertEquals(out.find(4L), modified);
    }

    @Test
    void deleteRemovesSpecifiedStudentAndReturnsThatStudent() {
        Student removed = out.find(1L);
        assertEquals(removed, out.delete(1L));
        assertNull(out.find(1L));
    }
}