package org.example.utils;

import com.google.gson.Gson;
import org.example.dao.UserDao;
import org.example.interfaces.IUser;
import org.example.models.Users;
import org.sql2o.Connection;

import static spark.Spark.*;

public class Router {
    public static void run(Connection connection){
        IUser userDao = new UserDao(connection);
        post("/api/v1/users/login", (req, res) -> {
            Gson gson = new Gson();
            Users userData = gson.fromJson(req.body(), Users.class);
            Users user = userDao.login(userData.getEmail(), userData.getPassword());
            res.type("application/json");
            if(user != null){
                return gson.toJson(user);
            } else {
                res.status(401);
                return gson.toJson("Invalid email or password");
            }
        });

        post("/api/v1/users/register", (req, res) -> {
            Gson gson = new Gson();
            Users userData = gson.fromJson(req.body(), Users.class);
            if(userDao.register(userData)){
                res.type("application/json");
                res.status(201);
                return gson.toJson(userData);
            }
            res.type("application/json");
            res.status(400);
            return gson.toJson("Bad Request");
        });

        patch("/api/v1/users/:id", (req, res) -> {
            Gson gson = new Gson();
            Users userData = gson.fromJson(req.body(), Users.class);
            userData.setId(Integer.parseInt(req.params(":id")));
            Users user = userDao.updateUser(userData);
            res.type("application/json");
            return gson.toJson(user);
        });

        get("/api/v1/users/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            Users user = userDao.getUser(id);
            res.type("application/json");
            return gson.toJson(user);
        });

        delete("/api/v1/users/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            Users user = userDao.deleteUser(id);
            res.type("application/json");
            return gson.toJson(user);
        });

    }
}
