package org.example;

import lombok.extern.java.Log;
import org.example.models.Course;
import org.example.models.Student;
import org.example.services.CourseService;
import org.example.services.StudentService;
import org.example.utils.CommandLine;


import java.util.List;
import java.util.Scanner;

//students can register for courses, and view the course assigned to them.
//Main initializes dummy data: {@link CommandLine#addData()}



//USER INTERACTIONS:

@Log
public class Main {
    static final StudentService studentService = new StudentService();
    static final CourseService courseService = new CourseService();

    public static void main(String[] args) {

        //load fake data
        CommandLine.addData();

        //PROMPT FOR USER INPUT
        Scanner input = new Scanner(System.in);
        int userInput;

        do {
            System.out.printf("Select # from menu:%n1.Student%n2.Quit%n");
            userInput = input.nextInt();

            //
            switch (userInput) {
                case 1:
                    handleStudentMenu(input);
                    break;
                case 2:
                    System.out.println("Exiting application...");
                    break;
                default:
                    System.out.println("Invalid input. Please select a valid option.");
                    break;
            }
        } while (userInput != 2);

        input.close();
    }

    private static void handleStudentMenu(Scanner input) {
        System.out.print("Enter student email: ");
        String email = input.next();
        System.out.printf("Enter %s's password: ", email.substring(0, email.indexOf("@")));
        String password = input.next();
        if (studentService.validateStudent(email, password)) {
            printStudentCourses(email);
            int userInput;
            do {
                System.out.printf("Select # from menu: %n1.Register %s to class: %n2.Logout%n", studentService.getStudentByEmail(email).getName());
                userInput = input.nextInt();
                switch (userInput) {
                    case 1:
                        handleCourseRegistration(email, input);
                        break;
                    case 2:
                        System.out.println("Logging out.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                        break;
                }
            } while (userInput != 2);
        } else {
            System.out.println("Incorrect username or password");
        }
    }



    private static void handleCourseRegistration(String email, Scanner input) {
        List<Course> courseList = courseService.getAllCourses();

        if (courseList.isEmpty()) {
            System.out.println("No courses available to register.");
            return;
        }

        System.out.println("Available courses:");
        for (Course course : courseList) {
            System.out.printf("%d. %s (Instructor: %s)%n", course.getId(), course.getName(), course.getInstructor());
        }

        System.out.print("Enter course ID to register: ");
        int courseId = getIntInput(input);

        // Validate courseId
        if (courseId >= 1 && courseId <= courseList.size()) {
            Course selectedCourse = courseList.get(courseId - 1);

            if (studentService.registerStudentToCourse(email, courseId)) {
                System.out.println("Successfully registered for the course.");
                printStudentCourses(email);
            } else {
                System.out.println("Failed to register for the course. Please try again.");
            }
        } else {
            System.out.println("Invalid course ID. Please select a valid course.");
        }
    }


    private static int getIntInput(Scanner input){
        while (!input.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            input.next(); // consume invalid input
        }
        return input.nextInt();
    }

    private static void printStudentCourses(String email){
        System.out.printf("%s's courses:%n", email);
        List<Course> userCourses = studentService.getStudentCourses(email);

        if (userCourses.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            System.out.printf("%-2s | %-20s | %s%n", "ID", "Course", "Instructor");
            for (Course course : userCourses) {
                System.out.printf("%-2d | %-20s | %s%n", course.getId(), course.getName(), course.getInstructor());
            }
        }
    }
}






