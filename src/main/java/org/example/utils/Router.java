package org.example.utils;

import com.google.gson.Gson;
import org.example.dao.TeacherDao;
import org.example.dao.UserDao;
import org.example.interfaces.ITeacher;
import org.example.interfaces.IUser;
import org.example.models.Teacher;
import org.example.models.Users;
import org.sql2o.Connection;

import java.util.List;

import static spark.Spark.*;

public class Router {
    public static void run(Connection connection){
        IUser userDao = new UserDao(connection);
        ITeacher teacherDao = new TeacherDao(connection); //TODO: add teacher dao

        //TODO: add user routes
        post("/api/v1/users/login", (req, res) -> {
            Gson gson = new Gson();
            Users userData = gson.fromJson(req.body(), Users.class);
            Users user = userDao.login(userData.getEmail(), userData.getPassword());
            res.type("application/json");
            return gson.toJson(user);
        });

        post("/api/v1/users/register", (req, res) -> {
            Gson gson = new Gson();
            Users userData = gson.fromJson(req.body(), Users.class);
            Users user = userDao.register(userData);
            res.type("application/json");
            return gson.toJson(user);
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

        //TODO: add teacher routes
        post("/api/v1/teachers/register", (req, res) -> {
            Gson gson = new Gson();
            Teacher teacherData = gson.fromJson(req.body(), Teacher.class);
            Teacher teacher = teacherDao.createTeacher(teacherData);
            res.type("application/json");
            return gson.toJson(teacher);
        });

        get("/api/v1/teachers/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            Teacher teacher = teacherDao.getTeacher(id);
            res.type("application/json");
            return gson.toJson(teacher);
        });

        get("/api/v1/teachers", (req, res) -> {
            Gson gson = new Gson();
            List<Teacher> teachers = teacherDao.getAllTeachers();
            res.type("application/json");
            return gson.toJson(teachers);
        });

        patch("/api/v1/teachers/:id", (req, res) -> {
            Gson gson = new Gson();
            Teacher teacherData = gson.fromJson(req.body(), Teacher.class);
            teacherData.setId(Integer.parseInt(req.params(":id")));
            Teacher teacher = teacherDao.updateTeacher(teacherData);
            res.type("application/json");
            return gson.toJson(teacher);
        });

        delete("/api/v1/teachers/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            Teacher teacher = teacherDao.deleteTeacher(id);
            res.type("application/json");
            return gson.toJson(teacher);
        });

    }
}
