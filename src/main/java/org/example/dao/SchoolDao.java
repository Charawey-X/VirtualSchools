package org.example.dao;

import org.example.interfaces.ISchool;
import org.example.models.School;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class SchoolDao implements ISchool {
    private Connection connection;

    public SchoolDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createSchool(School school) {
        String query = "INSERT INTO schools (name,description) VALUES (:name,:description)";
        try {
            int id = (int) connection.createQuery(query,true)
                    .bind(school)
                    .executeUpdate()
                    .getKey();
            school.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error creating school");
        }

    }

    @Override
    public School getSchool(int id) {
        String query = "SELECT * FROM schools WHERE id = :id";
        try {
            return connection.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(School.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting school");
        }
    }

    @Override
    public List<School> getAllSchools() {
        String query = "SELECT * FROM schools";
        try {
            return connection.createQuery(query)
                    .executeAndFetch(School.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting schools");
        }
    }

    @Override
    public void updateSchool(School school) {
        String query = "UPDATE schools SET name = :name, description = :description, level = :level, courses = :courses, teachers = :teachers WHERE id = :id";
        try {
            connection.createQuery(query)
                    .bind(school)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error updating school");
        }
    }

    @Override
    public void deleteSchool(int id) {
        String query = "DELETE FROM schools WHERE id = :id";
        try {
            connection.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error deleting school");
        }
    }
}
