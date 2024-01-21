package pro.sky.javacoursepart3.hw31.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.javacoursepart3.hw31.model.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceImplTest {

    private final FacultyServiceImpl out = new FacultyServiceImpl();

    @BeforeEach
    private void setFaculties() {
        out.add(new Faculty("faculty0", "color0"));
        out.add(new Faculty("faculty1", "color1"));
        out.add(new Faculty("faculty2", "color2"));
        out.add(new Faculty("faculty3", "color3"));
        out.add(new Faculty("faculty4", "color4"));
    }

    @Test
    void getAllReturnsListOfFaculties() {
        int facultiesCountAfterSetFaculties = out.getAll().size();
        assertEquals(5, facultiesCountAfterSetFaculties);
    }

    @Test
    void getFacultyByColorReturnsListOfFacultiesWithSpecifiedColorOrEmptyList() {
        List<Faculty> found = out.getFacultyByColor("color3");
        List<Faculty> empty = out.getFacultyByColor("color9");
        assertEquals(1, found.size());
        assertEquals(0, empty.size());
        assertTrue(found.contains(new Faculty("faculty3", "color3")));
    }

    @Test
    void addAddFacultyToMapAndReturnAddedFaculty() {
        Faculty toAdd = new Faculty("faculty5", "color5");
        assertEquals(toAdd, out.add(toAdd));
        assertEquals(6, out.getAll().size());
    }

    @Test
    void findReturnsFacultyWithSpecifiedIdOrElseNull() {
        Faculty found = out.find(2L);
        Faculty notFound = out.find(9L);
        assertEquals(new Faculty("faculty1", "color1"), found);
        assertNull(notFound);
    }

    @Test
    void editReturnsModifiedFaculty() {
        Faculty modified = new Faculty("modifiedFaculty", "newColor");
        out.edit(4L, modified);
        assertTrue(out.getAll().contains(modified)
                && !out.getAll().contains(new Faculty("faculty3", "color3")));
    }

    @Test
    void deleteRemovesSpecifiedFacultyAndReturnsThatFaculty() {
        Faculty removed = out.find(1L);
        assertEquals(removed, out.delete(1L));
        assertNull(out.find(1L));
    }
}