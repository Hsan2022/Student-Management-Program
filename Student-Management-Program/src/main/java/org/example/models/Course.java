package org.example.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Course is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A Course object contains fields that represent course
 * information and a mapping of 'courses' that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */
//Lombok
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

//using JPA annotations to map a class, now entity to a db table
@Table(name = "course")
@Entity

//declare a public access class
public class Course {

    //pk(email), attributes of course entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(length = 50, name = "name")
    private String name;

    @NonNull
    @Column(length = 50, name="instructor")
    private String instructor;

    //relationship - Courses --> 'courses' attribute in Student class
    @ManyToMany(mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},fetch = FetchType.EAGER)

    //instance of unique set/coll Student objs, order in insertions
    private Set<Student> students = new LinkedHashSet<>();

    //CHECKING COURSE OBJECTS FOR EQUALITY
    //overrides equals() from Object class
    @Override
    //public, custom class, passing in Object 'o', returns boolean
    public boolean equals(Object o) {
        //if "this" obj is = to 'o' obj - return true(same obj on memory)
        if (this == o) return true;
        //if 'o' = null OR NOT an instance of Student class return false(Type check)
        if (o == null || getClass() != o.getClass()) return false;
        //cast 'o'  to Course object after equal confirmation
        Course course = (Course) o;
        //compare obj attributes, int, String types with obj.equals()
        return id == course.id && Objects.equals(name, course.name) && Objects.equals(instructor, course.instructor);
    }

    //annotation to override default hashCode method, same attributes as above
    @Override
    public int hashCode() {
        //using method on Object superclass, passing in entity props, same as equals()
        //returns integer hash code in hashmap format, key-value pairs
        return Objects.hash(id, name, instructor);
    }
}

