package org.example.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Student is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object contains fields that represent student
 * login credentials and a join table containing a registered student's email and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Setter@Getter@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

@Table(name = "student")
@Entity

public class Student {

    @NonNull
    @Id @Column(length = 50,name = "email")
    String email;

    @NonNull
    @Column(length = 50, nullable = false, name = "name")
    String name;

    @NonNull
    @Column(length = 50,name = "password")
    String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "courses_id"))

    //initialized HashSet/unique of Course objects, order in insertions
    Set<org.example.models.Course> courses = new LinkedHashSet<>();


    //CHECKING OBJECTS FOR EQUALITY
    //annotation to override default equals method
    @Override
    //public, custom class, passing in Object 'o', returns boolean
    public boolean equals(Object o) {
        //if "this" obj is = to 'o' obj - return true(same obj on memory)
        if (this == o) return true;
        //if 'o' = null OR NOT an instance of Student class return false(Type check)
        if (o == null || getClass() != o.getClass()) return false;
        //cast 'o'  to Course object after equal confirmation
        Student student = (Student) o;
        //compare obj attributes, String, String and String types with prop.equals() passing in obj props
        return email.equals(student.email) && name.equals(student.name) && password.equals(student.password);
    }

    //annotation to override default hashCode method, same attributes as above
    @Override

    //public int class, no params passed in, custom class
    public int hashCode() {
        //using method on Object superclass, passing in entity props, same as equals()
        //returns integer hash code in hashmap format, key-value pairs
        return Objects.hash(email, name, password);
    }


    public void addCourse(org.example.models.Course course) {
        courses.add(course);
    }
}
