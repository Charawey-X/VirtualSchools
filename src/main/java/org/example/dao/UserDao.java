package org.example.dao;

import org.example.interfaces.IUser;
import org.example.models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

public class UserDao implements IUser {

    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Users login(String email, String password) {
        try {
            return connection.createQuery("SELECT * FROM users WHERE email = :email AND password = :password")
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .executeAndFetchFirst(Users.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error logging in");
        }
    }

    @Override
    public boolean register(Users user) {
        try {
            return connection.createQuery("INSERT INTO users (name, email, password, role) VALUES (:name, :email, :password, :role)")
                    .bind(user)
                    .executeUpdate().getResult()>0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error registering");
        }
    }

    @Override
    public Users getUser(int id) {
        try {
            return connection.createQuery("SELECT * FROM users WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Users.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting user");
        }
    }

    @Override
    public Users updateUser(Users user) {
        try {
            return connection.createQuery("UPDATE users SET name = :name, email = :email, password = :password, created_at = :created_at, updated_at = :updated_at, role = :role WHERE id = :id")
                    .bind(user)
                    .executeAndFetchFirst(Users.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error updating user");
        }
    }

    @Override
    public Users deleteUser(int id) {
        try {
            return connection.createQuery("DELETE FROM users WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Users.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error deleting user");
        }
    }
}
