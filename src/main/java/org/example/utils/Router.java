package org.example.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import org.example.Main;
import org.example.dao.*;
import org.example.enums.Level;
import org.example.interfaces.*;
import org.example.models.*;
import org.sql2o.Connection;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.example.dao.ResourceDao;
import org.example.dao.UserDao;
import org.example.interfaces.IResource;
import org.example.interfaces.IUser;
import org.example.models.Resources;
import org.example.models.Users;
import org.sql2o.Connection;

import java.util.List;

import static spark.Spark.*;

public class Router {

    private static final HashMap<String, String> corsHeaders = new HashMap<String, String>();
    
    static {
        corsHeaders.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        corsHeaders.put("Access-Control-Allow-Credentials", "true");
    }

    public final static void apply() {
        Filter filter = new Filter() {
            @Override
            public void handle(Request request, Response response) throws Exception {
                corsHeaders.forEach((key, value) -> {
                    response.header(key, value);
                });
            }
        };
        Spark.after(filter);
    }

    public static void markAttendance(Connection connection, int userId, String resource, String activity) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String formattedDate = formatter.format(new Date());
        Attendance attendance = new Attendance(userId, formattedDate, activity, resource);
        new AttendanceDao(connection).addAttendance(attendance);
    }

    public static void run(Connection connection) {
        Router.apply();
        port(5000);
      
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
                markAttendance(connection, user.getId(), "Authorization", "User Login");
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
            markAttendance(connection, jwt.getClaim("id").asInt(), "User Resource", "Update User details");
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
            markAttendance(connection, user.getId(), "User Resource", "Get User Data");
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

        /**
         * Attendance
         * @api {Endpoints} /attendance/*
         */

        IAttendance attendanceDao = new AttendanceDao(connection);

        get("/api/v1/attendance/:date/all", (req, res) -> {
            Gson gson = new Gson();
            // Get accessLevel from bearer token
            String token = req.headers("Authorization").split(" ")[1];
            DecodedJWT jwt = Hasher.decodeJwt(token);
            String accessLevel = jwt.getClaim("accessLevel").asString();
            res.type("application/json");
            if (accessLevel.equals("ADMIN") || accessLevel.equals("TEACHER")) {
                String date = req.params(":date");
                List<Attendance> attendance = attendanceDao.getAllAttendanceByDate(
                        date
                );
                if (attendance != null || !attendance.isEmpty()) {
                    return gson.toJson(attendance);
                }
                res.status(404);
                return gson.toJson("Not found");
            } else {
                res.status(401);
                return gson.toJson("You do not have access to this resource");
            }
        });

        get("/api/v1/attendance/:date/:id", (req, res) -> {
            Gson gson = new Gson();
            // Get accessLevel from bearer token
            String token = req.headers("Authorization").split(" ")[1];
            DecodedJWT jwt = Hasher.decodeJwt(token);
            String accessLevel = jwt.getClaim("accessLevel").asString();
            int userID = Integer.parseInt(req.params(":id"));
            res.type("application/json");
            if (accessLevel.equals("ADMIN") || accessLevel.equals("TEACHER")) {
                String date = req.params(":date");
                Attendance attendance = attendanceDao.getAttendance(
                        userID,
                        date
                );
                if (attendance != null) {
                    return gson.toJson(attendance);
                }
                res.status(404);
                return gson.toJson("Not found");
            } else {
                res.status(401);
                return gson.toJson("You do not have access to this resource");
            }
        });

        post("/api/v1/attendance", (req, res) -> {
            Gson gson = new Gson();
            // Get Token from header
            String token = req.headers("Authorization").split(" ")[1];
            // Verify token
            DecodedJWT jwt = Hasher.decodeJwt(token);
            res.type("application/json");
            Attendance attendance = gson.fromJson(req.body(), Attendance.class);
            attendance.setUserid(jwt.getClaim("id").asInt());
            if (attendanceDao.addAttendance(
                    attendance
            )) {
                res.status(201);
                return gson.toJson(attendance);
            }
            res.status(400);
            return gson.toJson("Bad Request");
        });

    }
}
