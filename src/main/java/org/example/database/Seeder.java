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

        } catch (Sql2oException e) {
            System.out.println("Error connecting to database");
        }
    }

    public static void drop(@NotNull Connection connection) {
        try {
            connection.createQuery("DROP TABLE IF EXISTS users")
                    .executeUpdate();

        } catch (Sql2oException e) {
            System.out.println("Error connecting to database");
        }
    }
}
