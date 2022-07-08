package org.example.dao;

import org.example.interfaces.ITeacher;
import org.example.models.Teacher;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class TeacherDao implements ITeacher {
    private Connection connection;

    public TeacherDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTeacher(Teacher teacher) {
        String query = "INSERT INTO teachers (school_id, name, subjects) VALUES (:school_id, :name, :subjects)";
        try {
            int id = (int) connection.createQuery(query,true)
                    .bind(teacher)
                    .executeUpdate()
                    .getKey();
            teacher.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error creating teacher");
        }

    }

    @Override
    public Teacher getTeacher(int id) {
        String query = "SELECT * FROM teachers WHERE id = :id";
        try {
            return connection.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Teacher.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting teacher");
        }
    }

    @Override
    public List<Teacher> getAllTeachers() {
        String query = "SELECT * FROM teachers";
        try {
            return connection.createQuery(query)
                    .executeAndFetch(Teacher.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting teachers");
        }
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        String query = "UPDATE teachers SET school_id = :school_id, name = :name, subjects = :subjects WHERE id = :id";
        try {
            connection.createQuery(query)
                    .bind(teacher)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error updating teacher");
        }
    }

    @Override
    public void deleteTeacher(int id) {
        String query = "DELETE FROM teachers WHERE id = :id";
        try {
            connection.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error deleting teacher");
        }

    }
}
