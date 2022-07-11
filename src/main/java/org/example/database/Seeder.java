package org.example.database;

import org.jetbrains.annotations.NotNull;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.sql.SQLException;

public class Seeder {
    public static void seed(@NotNull Connection connection) {
        try {
            connection.createQuery("CREATE TABLE  IF NOT EXISTS " +
                            "users (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL," +
                            "email VARCHAR(255) UNIQUE NOT NULL, role VARCHAR(255) NOT NULL," +
                            "password VARCHAR(255) NOT NULL, createdat TIMESTAMP DEFAULT " +
                            "CURRENT_TIMESTAMP, " +
                            "updatedat TIMESTAMP DEFAULT CURRENT_TIMESTAMP)")
                    .executeUpdate();

            connection.createQuery("CREATE TABLE  IF NOT EXISTS " +
                            "resources (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, " +
                            "description VARCHAR(255), url VARCHAR(255) not null, " +
                            "type TEXT, userid Text," +
                            "access VARCHAR(255) NOT NULL, createdat TIMESTAMP DEFAULT " +
                            "CURRENT_TIMESTAMP, " +
                            "updatedat TIMESTAMP DEFAULT CURRENT_TIMESTAMP, subjectid INTEGER NOT NULL)")
                    .executeUpdate();

            connection.createQuery("CREATE TABLE  IF NOT EXISTS " +
                            "schools (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL," +
                            "description VARCHAR(255) NOT NULL)")
                    .executeUpdate();
            connection.createQuery("CREATE TABLE  IF NOT EXISTS " +
                            "subjects (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL," +
                            "description VARCHAR(255) NOT NULL, school TEXT NOT NULL," +
                            "teacher TEXT)")
                    .executeUpdate();
            connection.createQuery("CREATE TABLE  IF NOT EXISTS " +
                            "attendance (id SERIAL PRIMARY KEY, userid INTEGER NOT NULL," +
                            "date VARCHAR(255) NOT NULL, activity TEXT NOT NULL," +
                            "resource TEXT)")
                    .executeUpdate();

        } catch (Exception e) {
            throw new Sql2oException("Error creating tables", e);
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
