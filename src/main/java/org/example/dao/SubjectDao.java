package org.example.dao;

import org.example.interfaces.ISubject;
import org.example.models.Subject;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class SubjectDao implements ISubject {
    private Connection connection;

    public SubjectDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Subject subject) {
        String query = "INSERT INTO subjects (name, description, school, teacher) VALUES (:name, :description, :school, :teacher)";
        try{
            int id = (int) connection.createQuery(query, true)
                    .bind(subject)
                    .executeUpdate()
                    .getKey();
            subject.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error creating subject");
        }

    }

    @Override
    public Subject read(int id) {
        String query = "SELECT * FROM subjects WHERE id = :id";
        try {
            return connection.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Subject.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting subject");
        }
    }

    @Override
    public List<Subject> readAll() {
        String query = "SELECT * FROM subjects";
        try {
            return connection.createQuery(query)
                    .executeAndFetch(Subject.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting subjects");
        }
    }

    @Override
    public void update(Subject subject) {
        String query = "UPDATE subjects SET name = :name, description = :description, school_id = :school_id, teacher_id = :teacher_id, level_id = :level_id WHERE id = :id";
        try {
            connection.createQuery(query)
                    .bind(subject)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error updating subject");
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM subjects WHERE id = :id";
        try {
            connection.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error deleting subject");
        }
    }
}
