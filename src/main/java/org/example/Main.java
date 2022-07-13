package org.example;

import org.example.database.DbImpl;
import org.example.database.Seeder;
import org.example.utils.Router;
import org.sql2o.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = new DbImpl().connect();
        Seeder.seed(connection);
        new Router().run(connection);
    }


}
