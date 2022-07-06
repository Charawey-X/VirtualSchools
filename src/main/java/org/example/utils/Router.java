package org.example.utils;

import com.google.gson.Gson;
import org.example.dao.*;
import org.example.interfaces.*;
import org.example.models.*;
import org.sql2o.Connection;

import java.util.List;

import static spark.Spark.*;

public class Router {
    public static void run(Connection connection){
        IUser userDao = new UserDao(connection);
        ITeacher teacherDao = new TeacherDao(connection); //TODO: add teacher dao
        IStudent studentDao = new StudentDao(connection); //TODO: add student dao
        ISchool schoolDao = new SchoolDao(connection); //TODO: add school dao
        ISubject subjectDao = new SubjectDao(connection); //TODO: add subject dao

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
            teacherDao.createTeacher(teacherData);
            res.type("application/json");
            return gson.toJson("Teacher created");
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
            teacherDao.updateTeacher(teacherData);
            res.type("application/json");
            return gson.toJson("Teacher updated");
        });

        delete("/api/v1/teachers/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            teacherDao.deleteTeacher(id);
            res.type("application/json");
            return gson.toJson("Teacher deleted");
        });

        //TODO: add student routes
        post("/api/v1/students/register", (req, res) -> {
            Gson gson = new Gson();
            Student studentData = gson.fromJson(req.body(), Student.class);
            studentDao.createStudent(studentData);
            res.type("application/json");
            return gson.toJson("Student created");
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
            studentData.setSchool(Integer.parseInt(req.params(":schoolId")));
            studentDao.updateStudent(studentData);
            res.type("application/json");
            return gson.toJson("Student updated");
        });

        delete("/api/v1/students/:id", (req, res) -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(req.params(":id"));
            studentDao.deleteStudent(id);
            res.type("application/json");
            return gson.toJson("Student deleted");
        });

        //TODO: add school routes
        post("/api/v1/schools/register", (req, res) -> {
            Gson gson = new Gson();
            School schoolData = gson.fromJson(req.body(), School.class);
            schoolDao.createSchool(schoolData);
            res.type("application/json");
            return gson.toJson("School created");
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

        //TODO: add course routes
        post("/api/v1/courses/register", (req, res) -> {
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

    }
}
