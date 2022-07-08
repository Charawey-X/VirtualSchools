package org.example.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
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

//        before((request, response) -> {
//            response.header("Access-Control-Allow-Origin", "*");
//            response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
//            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
//
//            // Required Authorization header for all requests
//            if (request.headers("Authorization") == null) {
//                halt(401, "You must send an Authorization header");
//            }
//
//        });

        /**
         * @api {Endpoints} /users/*
         */
        IUser userDao = new UserDao(connection);
        post("/api/v1/users/login", (req, res) -> {
            Gson gson = new Gson();
            Users userData = gson.fromJson(req.body(), Users.class);
            Users user = userDao.login(userData.getEmail());
            res.type("application/json");
            if (user != null && Hasher.verify(userData.getPassword(), user.getPassword())) {
                String token = Hasher.createJwt(user);
                Map<String, Object> map = new HashMap<>();
                map.put("token", token);
                map.put("user", user);
                return gson.toJson(map);
            } else {
                res.status(401);
                return gson.toJson("Invalid email or password");
            }
        });

        post("/api/v1/users/register", (req, res) -> {
            Gson gson = new Gson();
            Users userData = gson.fromJson(req.body(), Users.class);
            userData.setPassword(Hasher.hash(userData.getPassword()));
            res.type("application/json");
            if (userDao.register(userData)) {
                res.status(201);
                return gson.toJson(userData);
            }

            res.status(400);
            return gson.toJson("Bad Request");
        });

        patch("/api/v1/users/:id", (req, res) -> {
            // Get token from header
            String token = req.headers("Authorization");
            // Verify token
            DecodedJWT jwt = Hasher.decodeJwt(token);
            Gson gson = new Gson();
            Users userData = gson.fromJson(req.body(), Users.class);
            userData.setId(jwt.getClaim("id").asInt());
            Users user = userDao.updateUser(userData);
            res.type("application/json");
            if (user != null) {
                return gson.toJson(user);
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
         * @api {Endpoints} /resources/*
         */
        IResource resourceDao = new ResourceDao(connection);
        get("/api/v1/resources", (req, res) -> {
            Gson gson = new Gson();
            // Get accessLevel from bearer token
            String accessLevel = req.headers("Authorization").replace("Bearer ", "");
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
            String accessLevel = req.headers("Authorization").replace("Bearer ", "");
            res.type("application/json");
            int id = Integer.parseInt(req.params(":id"));
            Resources resource = resourceDao.getResource(
                    id
            );

            if (resource != null && resource.getAccess().equals(accessLevel)) {
                return gson.toJson(resource);
            }
            res.status(404);
            return gson.toJson("Not found");
        });

        post("/api/v1/resources", (req, res) -> {
            Gson gson = new Gson();
            res.type("application/json");
            Resources resource = gson.fromJson(req.body(), Resources.class);
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
