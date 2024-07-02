package org.example.services;

import org.example.models.Course;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.example.dao.CourseI;
import org.example.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */


public class CourseService implements CourseI {

    private static final Logger logger = Logger.getLogger(CourseService.class.getName());

    //private attributes for db connection at class level
    private final SessionFactory factory;

    //constructor
    public CourseService() {
        //initialize factory session from hibernate util
        this.factory = HibernateUtil.getSessionFactory();
    }

    @Override
    //public, does not return, takes in an object 'Course course'
    public void createCourse(Course course) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(course);
            transaction.commit();
            logger.info("Course created: " + course.getName());
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "There's been an error in creating course: " + course.getName(), e);
            throw new RuntimeException("There's been an error in creating course: " + course.getName(), e);
        }
    }

    @Override
    //READ-ONLY public method, returns a Course obj, takes in an int param
    public Course getCourseById(int courseId) {

        //Course obj 'course' is set to null, will store course data retrieved from db
        Course course = null;

        //error handling
        try (Session session = factory.openSession()) {
            //query to fetch from Customer obj, these matching attributes
            String hql = "FROM Course WHERE id = :courseId";

            //this is a hibernate query obj, result is mapped to Course class
            Query<Course> query = session.createQuery(hql, Course.class);

            //binding params
            query.setParameter("courseId", courseId);
            //reassigning the value of query to the result of invoking

            //uniqueResult() on query
            course = query.uniqueResult();

        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "There's been an error in finding course with ID: " + courseId, e);
            throw new RuntimeException("There's been an error in finding course with ID: " + courseId, e);
        }
        //return fetched Course course obj, otherwise return 'null'
        return course;
    }

    @Override
    //public custom class, returns a List of Course objs
    public List<Course> getAllCourses() {

        //initialize a List of Course obj labeled 'courses'
        List<Course> courses = new ArrayList<>();

        //error handling
        try (Session session = factory.openSession()) {
            // hql is a query that fetches all Course entities from db
            String hql = "FROM Course";

            //create query object 'query', expects results type Course
            Query<Course> query = session.createQuery(hql, Course.class);

            //reassign 'course value to result of executing query result List
            courses = query.getResultList();

        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Error fetching all courses", e);
            throw new RuntimeException("Error fetching all courses", e);
        }
        //return the List of Course objs
        return courses;
    }

}

