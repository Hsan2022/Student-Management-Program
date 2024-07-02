package org.example.services;

import org.example.models.Course;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.example.dao.StudentI;
import org.example.models.Student;
import org.example.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StudentService implements StudentI {
    public static final Logger logger = Logger.getLogger(StudentService.class.getName());

    private final org.example.services.CourseService courseService = new org.example.services.CourseService();

    @Override
    public List<Student> getAllStudents() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction trans = null;
        List<Student> studentList = new ArrayList<>();
        try {
            trans = s.beginTransaction();
            Query<Student> q = s.createQuery("from Student", Student.class);
            studentList = q.getResultList();
            trans.commit();
        } catch (HibernateException exception) {
            if (trans != null) trans.rollback();
            logger.log(Level.SEVERE, "There's an error getting all students in db", exception);
        } finally {
            s.close();
        }
        return studentList;
    }

    @Override
    public void createStudent(Student student) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.persist(student);
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            logger.log(Level.SEVERE, "There's an error in creating a student obj", exception);
            throw new RuntimeException("There's an error in creating a student obj", exception);
        } finally {
            s.close();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Student student = null;
        try {
            tx = s.beginTransaction();
            Query<Student> q = s.createQuery("from Student where email = :email", Student.class);
            q.setParameter("email", email);
            student = q.uniqueResult();
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            logger.log(Level.SEVERE, "Theres's an error in querying student by email: " + email, exception);
            throw new RuntimeException("Theres's an error in querying student by email: " + email, exception);
        } finally {
            s.close();
        }
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Student s = getStudentByEmail(email);
        return s != null && s.getPassword().equals(password);
    }

    //add functionality to check if student is already enrolled in course

    @Override
    public boolean registerStudentToCourse(String email, int courseId) {

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction trans = null;
        boolean success = false;

        try {
            trans = s.beginTransaction();

            Student student = getStudentByEmail(email);
            Course course = courseService.getCourseById(courseId);


            //added check
            if (student.getCourses().contains(course)) {
                // Student is already registered for this course
                String message = "Student with email " + email + " is already registered for this course " + course.getName();
                logger.warning(message);
            } else {
                // Register student for the course
                student.addCourse(course);
                s.merge(student);
                trans.commit();
                success = true;
                String message = "Student with email " + email + " registered successfully for course " + course.getName();
                logger.info(message);
            }
        } catch (RuntimeException exception) {
            if (trans != null) trans.rollback();
            logger.log(Level.SEVERE, "There's an error in registering student with email " + email + " for course ID " + courseId, exception);
            throw new RuntimeException("There's an error in registering student with email " + email + " for course ID " + courseId, exception);
        } finally {
            s.close();
        }
        return false;
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Course> courseList = new ArrayList<>();

        try {
            tx = s.beginTransaction();
            String nativeGetStudentCourses = "select c.id, c.name, c.instructor from course as c join student_courses as sc on c.id = sc.courses_id join student as s on s.email = sc.student_email where s.email = :email";
            NativeQuery<Course> studentCourses = s.createNativeQuery(nativeGetStudentCourses, Course.class);
            studentCourses.setParameter("email", email);
            courseList = studentCourses.getResultList();
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            logger.log(Level.SEVERE, "There's an error in getting courses for student with email " + email, exception);
            throw new RuntimeException("There's an error in getting courses for student with email " + email, exception);
        } finally {
            s.close();
        }
        return courseList;
    }

    public boolean removeStudent(Student studentToRemove) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Student student = s.get(Student.class, studentToRemove.getEmail()); // Assuming getId() returns the student's ID
            if (student != null) {
                s.delete(student);
                tx.commit();
                return true; // Student found and removed
            }
            return false; // Student not found
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            logger.log(Level.SEVERE, "There's an error in removing student with email " + studentToRemove.getEmail(), exception);
            throw new RuntimeException("There's an error in removing student with email " + studentToRemove.getEmail(), exception);
        } finally {
            s.close();
        }
    }
}
