package org.example;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.example.models.Student;
import org.example.utils.CommandLine;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class StudentServiceTest {

    static org.example.services.StudentService studentService;

    //load data
    @BeforeAll
    static void beforeAll() {
        studentService = new org.example.services.StudentService();
        CommandLine.addData();
    }

    //JUnit framework
    @Test
    void getAllStudents() {
        //variable containing a list of student objs
        List<Student> expectedResult = Arrays.asList(
                new Student("reema@gmail.com", "reema brown", "password"),
                new Student("annette@gmail.com", "annette allen", "password"),
                new Student("anthony@gmail.com", "anthony gallegos", "password"),
                new Student("ariadna@gmail.com", "ariadna ramirez", "password"),
                new Student("bolaji@gmail.com", "bolaji saibu", "password"),
                new Student("shirese@gmail.com", "shirese smith", "password")
        );

        //var that contains all students
        List<Student> actualResult = studentService.getAllStudents();

        //assert that both obj sizes are equals
        assertEquals(expectedResult.size(), actualResult.size());

        //assert that both objects have the same contents
        assertTrue(actualResult.containsAll(expectedResult));
    }


    //AssertJ framework
    @Test
    void removeStudent() {

        // var, creating an obj Student to remove, id'd by email, name
        Student studentToRemove = new Student("annette@gmail.com", "annette allen", "password");

        //var that contains the result of invoking (), true or false
        boolean result = studentService.removeStudent(studentToRemove);

        // Check if student was actually removed
        assertThat(result).isTrue();

        // check that the student is no longer in the list
        //first get all students, a List of student objs
        List<Student> allStudents = studentService.getAllStudents();

        //access prev var to check removed student is not in List
        assertThat(allStudents).doesNotContain(studentToRemove);
    }
}
