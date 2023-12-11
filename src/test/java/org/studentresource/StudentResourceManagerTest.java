package org.studentresource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
class StudentResourceManagerTest {
    private StudentResourceManager<Course> manager;

    @BeforeEach
    void setUp() {
        manager = new StudentResourceManager<>();
    }

    @Test
    void addAndRetrieveResourceTest() {
        Course course = new Course("CS101", "Introduction to Computer Science");
        manager.addResource(course);

        Course retrieved = manager.getResource("CS101");
        assertNotNull(retrieved, "Retrieved course should not be null.");
        assertEquals("Introduction to Computer Science", retrieved.getName(), "Course name should match.");
    }

    @Test
    void removeResourceTest() {
        Course course = new Course("CS101", "Introduction to Computer Science");
        Course course1 = new Course("CS102", "Introduction to Pointers");
        manager.addResource(course);
        manager.addResource(course1);
       int done= manager.remove("CS101");
       int done1=manager.remove("CS102");
        assertEquals(1,done, "We didn't remove");
        assertEquals(1,done1, "We didn't remove");
    }
    private StudentResourceManager<StudyMaterial> manager1;
    @BeforeEach
    void setUp1() {
        manager1 = new StudentResourceManager<>();
    }
    @Test
    void removestudymaterialTest(){
        StudyMaterial material1=new StudyMaterial("no1","Ksiazka");
        manager1.addResource(material1);
        int done= manager1.remove("no1");
        assertEquals(1,done, "We didn't remove");
    }
}
