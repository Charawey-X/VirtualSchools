package org.example.utils;

import com.google.gson.Gson;
import org.example.dao.SchoolDao;
import org.example.dao.StudentDao;
import org.example.dao.TeacherDao;
import org.example.dao.UserDao;
import org.example.interfaces.ISchool;
import org.example.interfaces.IStudent;
import org.example.interfaces.ITeacher;
import org.example.interfaces.IUser;
import org.example.models.School;
import org.example.models.Student;
import org.example.models.Teacher;
import org.example.models.Users;
import org.sql2o.Connection;

import java.util.List;

import static spark.Spark.*;

public class Router {
    public static void run(Connection connection){
        IUser userDao = new UserDao(connection);
        ITeacher teacherDao = new TeacherDao(connection); //TODO: add teacher dao
        IStudent studentDao = new StudentDao(connection); //TODO: add student dao
        ISchool schoolDao = new SchoolDao(connection); //TODO: add school dao

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

        //TODO: add student routes
        post("/api/v1/students/register", (req, res) -> {
            Gson gson = new Gson();
            Student studentData = gson.fromJson(req.body(), Student.class);
            Student student = studentDao.createStudent(studentData);
            res.type("application/json");
            return gson.toJson(student);
        });

        get("/api/v1/students/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            Student student = studentDao.getStudent(id);
            res.type("application/json");
            return gson.toJson(student);
        });

        get("/api/v1/students", (req, res) -> {
            Gson gson = new Gson();
            List<Student> students = studentDao.getAllStudents();
            res.type("application/json");
            return gson.toJson(students);
        });

        patch("/api/v1/students/:id", (req, res) -> {
            Gson gson = new Gson();
            Student studentData = gson.fromJson(req.body(), Student.class);
            studentData.setStudentId(Integer.parseInt(req.params(":id")));
            studentData.setStudentName(req.params(":studentName"));
            studentData.setSchool(req.params(":school"));
            Student student = studentDao.updateStudent(studentData);
            res.type("application/json");
            return gson.toJson(student);
        });

        delete("/api/v1/students/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            Student student = studentDao.deleteStudent(id);
            res.type("application/json");
            return gson.toJson(student);
        });

        //TODO: add school routes
        post("/api/v1/schools/register", (req, res) -> {
            Gson gson = new Gson();
            School schoolData = gson.fromJson(req.body(), School.class);
            School school = schoolDao.createSchool(schoolData);
            res.type("application/json");
            return gson.toJson(school);
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
            schoolData.setId(Integer.parseInt(req.params(":id")));
            School school = schoolDao.updateSchool(schoolData);
            res.type("application/json");
            return gson.toJson(school);
        });

        delete("/api/v1/schools/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            School school = schoolDao.deleteSchool(id);
            res.type("application/json");
            return gson.toJson(school);
        });


    }
}
