package org.example.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import org.example.Main;
import org.example.dao.*;
import org.example.enums.Level;
import org.example.interfaces.*;
import org.example.models.*;
import org.sql2o.Connection;

import java.util.List;

import org.example.dao.ResourceDao;
import org.example.dao.UserDao;
import org.example.interfaces.IResource;
import org.example.interfaces.IUser;
import org.example.models.Resources;
import org.example.models.Users;
import org.sql2o.Connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Router {
    public static void run(Connection connection) {

        port(8989);

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");

        });

        /**
         * Users {Teachers, Students, Admins}
         * @api {Endpoints} /users/*
         */
        IUser userDao = new UserDao(connection);

        ISubject subjectDao = new SubjectDao(connection);

        post("/api/v1/users/login", (req, res) -> {
            Gson gson = new Gson();
            Users userData = gson.fromJson(req.body(), Users.class);
            Users user = userDao.login(userData.getEmail());
            System.out.println(userData.getEmail());
            res.type("application/json");
            if (user != null && Hasher.verify(userData.getPassword(), user.getPassword())) {
                String token = Hasher.createJwt(user);
                Map<String, Object> map = new HashMap<>();
                map.put("token", token);
                map.put("user", user);
                return gson.toJson(map);
            } else {
                System.out.println("User not found");
                res.status(401);
                return gson.toJson("Invalid email or password");
            }
        });

        post("/api/v1/users/register", (req, res) -> {
            Gson gson = new Gson();
            Users userData = gson.fromJson(req.body(), Users.class);
            String temp = "";
            if (userData.getPassword() == null) {
                temp = Hasher.generatePassword();
                userData.setPassword(Hasher.hash(temp));

            } else {
                userData.setPassword(Hasher.hash(userData.getPassword()));
            }

            res.type("application/json");
            if (userDao.register(userData)) {
                res.status(201);
                //If user.role =="teacher" or "student"
                if (userData.getRole().equals("TEACHER") || userData.getRole().equals("STUDENT")) {
                    Mailer.sendMail(userData.getEmail(), "Welcome to Virtual School", "Your temporary password is: " + temp);
                }
                return gson.toJson(userData);
            }

            res.status(400);
            return gson.toJson("Bad Request");
        });

        patch("/api/v1/users", (req, res) -> {
            // Get token from header
            String token = req.headers("Authorization").split(" ")[1];
            // Verify token
            DecodedJWT jwt = Hasher.decodeJwt(token);
            Gson gson = new Gson();
            Users userData = gson.fromJson(req.body(), Users.class);
            userData.setId(jwt.getClaim("id").asInt());

            res.type("application/json");
            Map<String, Object> map = new HashMap<>();
            if (userDao.updateUser(userData)) {
                map.put("user", userData);
                map.put("message", "User updated successfully");
                return gson.toJson(map);
            }
            res.status(400);
            return gson.toJson("Bad Request");
        });

        get("/api/v1/users", (req, res) -> {
            // Get token from header
            String token = req.headers("Authorization").split(" ")[1];
            // Verify token
            DecodedJWT jwt = Hasher.decodeJwt(token);
            Gson gson = new Gson();
            int id = jwt
                    .getClaim("id")
                    .asInt();
            Users user = userDao.getUser(id);
            res.type("application/json");
            if (user != null) {
                return gson.toJson(user);
            }
            res.status(400);
            return gson.toJson("Bad Request");
        });

        get("/api/v1/users/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            Users user = userDao.getUser(id);
            res.type("application/json");
            if (user != null) {
                return gson.toJson(user);
            }
            res.status(400);
            return gson.toJson("Bad Request");
        });

        delete("/api/v1/users", (req, res) -> {
            // Get token from header
            String token = req.headers("Authorization");
            // Verify token
            DecodedJWT jwt = Hasher.decodeJwt(token);
            Gson gson = new Gson();
            int id = Integer.parseInt(jwt.getClaim("id").asString());
            Users user = userDao.deleteUser(id);
            res.type("application/json");
            if (user != null) {
                return gson.toJson(user);
            }
            res.status(400);
            return gson.toJson("Bad Request");
        });

        /**
         * Schools
         * @api {Endpoints} /schools/*
         */

        ISchool schoolDao = new SchoolDao(connection);
        post("/api/v1/schools", (req, res) -> {
            Gson gson = new Gson();
            String token = req.headers("Authorization").split(" ")[1];
            DecodedJWT jwt = Hasher.decodeJwt(token);
            String accessLevel = jwt.getClaim("accessLevel").asString();
            if (accessLevel.equals("ADMIN")) {
                School schoolData = gson.fromJson(req.body(), School.class);
                schoolDao.createSchool(schoolData);
                res.type("application/json");
                return gson.toJson("School created");
            }
            res.status(401);
            return gson.toJson("Unauthorized");
        });

        get("/api/v1/schools/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            School school = schoolDao.getSchool(id);
            res.type("application/json");
            return gson.toJson(school);
        });

        get("/api/v1/schools", (req, res) -> {
            Gson gson = new Gson();
            List<School> schools = schoolDao.getAllSchools();
            res.type("application/json");
            return gson.toJson(schools);
        });

        patch("/api/v1/schools/:id", (req, res) -> {
            Gson gson = new Gson();
            School schoolData = gson.fromJson(req.body(), School.class);
            schoolDao.updateSchool(schoolData);
            res.type("application/json");
            return gson.toJson("School updated");
        });

        delete("/api/v1/schools/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            schoolDao.deleteSchool(id);
            res.type("application/json");
            return gson.toJson("School deleted");
        });


        /**
         * Courses
         * @api {Endpoints} /courses/*
         */
        post("/api/v1/courses", (req, res) -> {
            Gson gson = new Gson();
            Subject subjectData = gson.fromJson(req.body(), Subject.class);
            subjectDao.create(subjectData);
            res.type("application/json");
            return gson.toJson("Course created");
        });

        get("/api/v1/courses/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            Subject course = subjectDao.read(id);
            res.type("application/json");
            return gson.toJson(course);
        });

        get("/api/v1/courses", (req, res) -> {
            Gson gson = new Gson();
            List<Subject> courses = subjectDao.readAll();
            res.type("application/json");
            return gson.toJson(courses);
        });

        patch("/api/v1/courses/:id", (req, res) -> {
            Gson gson = new Gson();
            Subject subjectData = gson.fromJson(req.body(), Subject.class);
            subjectData.setId(Integer.parseInt(req.params(":id")));
            subjectDao.update(subjectData);
            res.type("application/json");
            return gson.toJson("Course updated");
        });

        delete("/api/v1/courses/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            subjectDao.delete(id);
            res.type("application/json");
            return gson.toJson("Course deleted");
        });


        /**
         * @api {Endpoints} /resources/*
         */
        IResource resourceDao = new ResourceDao(connection);
        get("/api/v1/resources", (req, res) -> {
            Gson gson = new Gson();
            // Get accessLevel from bearer token
            String token = req.headers("Authorization").split(" ")[1];
            DecodedJWT jwt = Hasher.decodeJwt(token);
            String accessLevel = jwt.getClaim("accessLevel").asString();

            res.type("application/json");
            List<Resources> resources = resourceDao.getAllResources(
                    accessLevel
            );
            if (resources != null) {
                return gson.toJson(resources);
            }
            res.status(404);
            return gson.toJson("Not found");
        });

        get("/api/v1/resources/:id", (req, res) -> {
            Gson gson = new Gson();
            // Get accessLevel from bearer token
            String token = req.headers("Authorization").split(" ")[1];
            DecodedJWT jwt = Hasher.decodeJwt(token);
            String accessLevel = jwt.getClaim("accessLevel").asString();
            res.type("application/json");
            int id = Integer.parseInt(req.params(":id"));
            Resources resource = resourceDao.getResource(
                    id
            );
            if (resource != null && resource.getAccess().equals(accessLevel)) {
                return gson.toJson(resource);
            }
            res.status(401);
            return gson.toJson("Requested resource not found or you do not have access to it");
        });

        post("/api/v1/resources", (req, res) -> {
            Gson gson = new Gson();
            // Get Token from header
            String token = req.headers("Authorization").split(" ")[1];

            // Verify token
            DecodedJWT jwt = Hasher.decodeJwt(token);
            res.type("application/json");
            Resources resource = gson.fromJson(req.body(), Resources.class);
            resource.setUserid(String.valueOf(jwt.getClaim("id").asInt()));
            if (resourceDao.createResource(
                    resource
            )) {
                res.status(201);
                return gson.toJson(resource);
            }
            res.status(400);
            return gson.toJson("Bad Request");
        });

        patch("/api/v1/resources/:id", (req, res) -> {
            Gson gson = new Gson();
            res.type("application/json");
            int id = Integer.parseInt(req.params(":id"));
            Resources resource = gson.fromJson(req.body(), Resources.class);
            resource.setId(id);
            if (resourceDao.updateResource(
                    resource
            )) {
                res.status(200);
                return gson.toJson(resource);
            }
            res.status(400);
            return gson.toJson("Bad Request");
        });

        delete("/api/v1/resources/:id", (req, res) -> {
            Gson gson = new Gson();
            res.type("application/json");
            int id = Integer.parseInt(req.params(":id"));
            boolean response = resourceDao.deleteResource(
                    id
            );
            if (response) {
                return gson.toJson("Resource deleted");
            }
            res.status(400);
            return gson.toJson("Bad Request");
        });

    }
}
