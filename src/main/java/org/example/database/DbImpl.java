package org.example.database;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class DbImpl extends Db{
    public Connection connect() {
        try {
            String connect = "jdbc:postgresql://localhost:5432/news";
            Sql2o sql2o = new Sql2o(connect, "bill", "<cocoginger45");
            return sql2o.open();
        } catch (Sql2oException exc) {
            throw new Sql2oException("Error connecting to database", exc);
        }
    }


    public void disconnect(Connection connection) {
        connection.close();
    }
}
