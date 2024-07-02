package org.example.utils;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * HibernateUtil is a session factory helper class that builds a
 * secure connection to a database and permits CRUD operations on a table.
 * The session configuration derives from the hibernate.cfg.xml file in
 * the 'resources' folder.
 *
 *  <b style="color:red">WARNING! </b>
 *  <b>DO NOT MODIFY THIS CODE</b>
 */


//util class, creates and manages a Hibernate SessionFactory.
//SessionFactory is used to obtain sessions,
//sessions perform database operations (CRUD - Create, Read, Update, Delete).
public class HibernateUtil {

    //constructor
    private HibernateUtil() {
        // Utility classes should not have public constructors
        //'   ' not meant to be instantiated
        //which is the reason for the preventative private constructor
    }

    /**
     * @Getter builds a standard getter method for the object
     * sessionFactory.
     */
    @Getter

    //private var, not meant to be instantiated
    //variable holds 'single' application instance of hibernate SessionFactory
    private static SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Method builds a session factory from the 'hibernate.cfg.xml' file
     * in the 'resources' folder and returns a sessionFactory object.
     * @return
     */

    //create AND configure instance of Session Factory if none(null) created yet
    private static SessionFactory buildSessionFactory() {
        //error handling
        try
        {
            if (sessionFactory == null)
            {
                //configure and builds registry from config file
                StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml").build();

                //builds metadata about persistence classes/entities
                Metadata metaData = new MetadataSources(standardRegistry)
                        .getMetadataBuilder()
                        .build();

                //uses this configured metadata to build the SessionFactory
                sessionFactory = metaData.getSessionFactoryBuilder().build();
            }
            return sessionFactory;
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    /**
     * Closes the session factory.
     */
    //method to close session, release resources
    public static void shutdown() {
        //call close method
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

