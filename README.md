
Student Management Program

The Student Management Program is a Java-based application for managing students and courses. It provides functionalities for student authentication, course registration, and viewing enrolled courses through a command-line interface (CLI).

Table of Contents

Introduction
Features
Architecture
Setup
Prerequisites
Installation
Database Setup
Usage
Running the Application
Example Scenarios
Tests
Dependencies
Contributing
License
Introduction

The Student Management Program allows students to log in, view available courses, and register for courses of interest. It is built using Java, MySQL for data storage, and Hibernate ORM for database interactions. The project also integrates with Lombok for reducing boilerplate code and uses JUnit and AssertJ for unit testing.

Features

User Authentication: Students can authenticate using their email and password.
Course Management: Students can view available courses and register for them.
Data Persistence: Utilizes MySQL database for storing student and course information.
ORM Integration: Uses Hibernate for object-relational mapping, simplifying database operations.
Unit Testing: Includes comprehensive unit tests to ensure code reliability.
Architecture

The project follows a layered architecture pattern:

Presentation Layer: CLI interface (Main.java) for user interaction.
Service Layer: Business logic encapsulated in StudentService.java and CourseService.java.
Data Access Layer: Uses Hibernate for interacting with the MySQL database (Student.java, Course.java).
Setup

Prerequisites
Ensure you have the following installed:

Java Development Kit (JDK) 16 or higher
Apache Maven
MySQL database server
Installation
Clone the repository:

bash
Copy code
git clone https://github.com/yourusername/Student-Management-Program.git
cd Student-Management-Program
Build the project using Maven:

bash
Copy code
mvn clean install
Database Setup
Create a MySQL database named student_management.

Configure the database connection settings in src/main/resources/application.properties:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/student_management
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
Adjust spring.datasource.username and spring.datasource.password with your MySQL credentials.

Usage

Running the Application
Run the application:
bash
Copy code
mvn exec:java -Dexec.mainClass="org.example.Main"
Follow the prompts to interact with the application:
Enter student email and password to log in.
View available courses and register for a course.
Example Scenarios
Logging in: Enter a valid student email and password.
Viewing Courses: After logging in, choose option 1 to view available courses.
Registering for a Course: Select a course by its ID to register.
Tests

The project includes unit tests to ensure the functionality of core components. Run tests using Maven:

bash
Copy code
mvn test
Dependencies

MySQL Connector Java 8.0.32
Hibernate Core 5.5.2.Final
Lombok 1.18.32
JUnit Jupiter 5.8.2
AssertJ Core 3.22.0
Contributing

License

This project is licensed under the MIT License. See the LICENSE file for details.
