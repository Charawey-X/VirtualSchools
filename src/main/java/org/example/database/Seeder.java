package org.example.database;

import org.jetbrains.annotations.NotNull;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

public class Seeder {
    public static void seed(@NotNull Connection connection) {
        try {
            connection.createQuery("CREATE TABLE  IF NOT EXISTS " +
                            "users (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL," +
                            "email VARCHAR(255) NOT NULL, role VARCHAR(255) NOT NULL," +
                            "password VARCHAR(255) NOT NULL, createdat TIMESTAMP DEFAULT " +
                            "CURRENT_TIMESTAMP, " +
                            "updatedat TIMESTAMP DEFAULT CURRENT_TIMESTAMP)")
                    .executeUpdate();
            connection.createQuery("CREATE TABLE  IF NOT EXISTS " +
                            "teachers (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL," +
                            "subjects VARCHAR(255) NOT NULL)")
                    .executeUpdate();
            connection.createQuery("CREATE TABLE  IF NOT EXISTS " +
                            "students (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL," +
                            "school_id INTEGER NOT NULL, courses VARCHAR(255) NOT NULL)")
                    .executeUpdate();
            connection.createQuery("CREATE TABLE  IF NOT EXISTS " +
                            "schools (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL," +
                            "description VARCHAR(255) NOT NULL, level VARCHAR(255) NOT NULL," +
                            "courses VARCHAR(255) NOT NULL, teachers VARCHAR(255) NOT NULL)")
                    .executeUpdate();
            connection.createQuery("CREATE TABLE  IF NOT EXISTS " +
                            "subjects (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL," +
                            "description VARCHAR(255) NOT NULL, school_id INTEGER NOT NULL," +
                            "teacher_id INTEGER NOT NULL, level_id INTEGER NOT NULL)")
                    .executeUpdate();
        } catch (Sql2oException e) {
            System.out.println("Error connecting to database");
        }
    }

    public static void drop(@NotNull Connection connection) {
        try {
            connection.createQuery("DROP TABLE IF EXISTS users")
                    .executeUpdate();
            connection.createQuery("DROP TABLE IF EXISTS teachers")
                    .executeUpdate();
            connection.createQuery("DROP TABLE IF EXISTS students")
                    .executeUpdate();
            connection.createQuery("DROP TABLE IF EXISTS schools")
                    .executeUpdate();
            connection.createQuery("DROP TABLE IF EXISTS subjects")
                    .executeUpdate();
        } catch (Sql2oException e) {
            System.out.println("Error connecting to database");
        }
    }
}
