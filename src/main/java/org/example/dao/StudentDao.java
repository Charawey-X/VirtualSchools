package org.example.dao;

import org.example.interfaces.IStudent;
import org.example.models.Student;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class StudentDao implements IStudent {
    private Connection connection;

    public StudentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createStudent(Student student) {
        String query = "INSERT INTO students (school_id, name, courses) VALUES (:school_id, :name, :courses)";
        try {
            int id = (int) connection.createQuery(query,true)
                    .bind(student)
                    .executeUpdate()
                    .getKey();
            student.setStudentId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error creating student");
        }

    }

    @Override
    public Student getStudent(int id) {
        String query = "SELECT * FROM students WHERE id = :id";
        try {
            return connection.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Student.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting student");
        }
    }

    @Override
    public List<Student> getAllStudents() {
        String query = "SELECT * FROM students";
        try {
            return connection.createQuery(query)
                    .executeAndFetch(Student.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting students");
        }
    }

    @Override
    public void updateStudent(Student student) {
        String query = "UPDATE students SET school_id = :school_id, name = :name, courses = :courses WHERE id = :id";
        try {
            connection.createQuery(query)
                    .addParameter("school_id", student.getSchool())
                    .addParameter("name", student.getStudentName())
                    .addParameter("courses", student.getCourses())
                    .addParameter("id", student.getStudentId())
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error updating student");
        }
    }

    @Override
    public void deleteStudent(int id) {
        String query = "DELETE FROM students WHERE id = :id";
        try {
            connection.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error deleting student");
        }

    }
}
