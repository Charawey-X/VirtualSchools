package org.example.dao;

import org.example.interfaces.IUser;
import org.example.models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class UserDao implements IUser {

    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Users login(String email) {
        try {
            return connection.createQuery("SELECT * FROM users WHERE email = :email")
                    .addParameter("email", email)
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

            return  false;
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

    /**
     * @param role 
     * @return
     */
    @Override
    public List<Users> getUsersByRole(String role) {
        try {
            return connection.createQuery("SELECT * FROM users where role = :role")
                    .addParameter("role", role)
                    .executeAndFetch(Users.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting all users");
        }
    }

    @Override
    public List<Users> getAllUsers() {
        try {
            return connection.createQuery("SELECT * FROM users")
                    .executeAndFetch(Users.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting all users");
        }
    }

    @Override
    public boolean updateUser(Users user) {
        try {
            return connection.createQuery("UPDATE users SET name = :name WHERE id = :id")
                    .bind(user)
                    .executeUpdate().getResult() > 0;
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
