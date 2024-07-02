package org.example.utils;

import org.example.models.Course;
import org.example.models.Student;
import org.example.services.CourseService;
import org.example.services.StudentService;

/**
 * CommandLine is a Utility class that runs each time the application
 * executes. It performs a common routine by creating and persisting
 * student objects to the 'student' table and course objects to the
 * 'course' table.
 */

// utility class to populate db with initial dummy data
// for students and courses whenever the application starts
public class CommandLine {

    private CommandLine(){
        // Utility classes should not have public constructors
        //'   ' not meant to be instantiated
        //which is the reason for the preventative private constructor
    }

    //constant used to hold default password for all student objs
    private static final String PASSWORD = "password";

    /*
      Creates and persist student object to the 'student' table and
      course objects to the 'course' table

      <b style="color:red">ATTENTION PLEASE READ</b>
      Uncomment the following code after creating both models and entities for ease of adding and dropping dummy data into the database, remember that
      in hibernate.cfg.xml <code>hibernate.hbm2ddl.auto = create-drop </code> will create and drop the tables every time the application
      re-runs.


     */



    //method populates the db with dummy data.
    public static void addData(){

        //create service class instances needed to interact w/db
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();

        //using 'studentService' service class to create and persist Student objects with email, full name, and a default password (PASSWORD).
        //calling the createStudent() on the instance of StudentService class, passing in args
        studentService.createStudent(new Student("reema@gmail.com", "reema brown", PASSWORD));
        studentService.createStudent(new Student("annette@gmail.com", "annette allen", PASSWORD));
        studentService.createStudent(new Student("anthony@gmail.com", "anthony gallegos", PASSWORD));
        studentService.createStudent(new Student("ariadna@gmail.com", "ariadna ramirez", PASSWORD));
        studentService.createStudent(new Student("bolaji@gmail.com", "bolaji saibu", PASSWORD));
        studentService.createStudent(new Student("shirese@gmail.com", "shirese smith", PASSWORD));

        //using 'courseService' service class to create and persist Course objects with course name and instructor name
        //calling the createCourse() on the instance of CourseService class, 'courseService' passing in args
        courseService.createCourse(new Course("Java", "Roger Boaitey"));
        courseService.createCourse(new Course("Frontend", "William Roales"));
        courseService.createCourse(new Course("JPA", "Jafer Alhaboubi"));
        courseService.createCourse(new Course("Spring Framework", "LaTonya Lewis"));
        courseService.createCourse(new Course("SQL", "Ezra Williams"));
        courseService.createCourse(new Course("GitHub", "Igor Adulyan"));
        courseService.createCourse(new Course("Web Services", "Raheem Abolfathzadeh"));
        courseService.createCourse(new Course("Microservices", "Eric Heilig"));

    }
}
