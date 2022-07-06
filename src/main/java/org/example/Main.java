package org.example;

import org.example.models.School;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.staticFileLocation;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");

        get("/", (request, response) -> {

            return new ModelAndView(new HashMap(), "dashboard.hbs");

        }, new HandlebarsTemplateEngine());

        get("/school-detail-form", (request, response) -> {

            return new ModelAndView(new HashMap(), "school-detail-form.hbs");

        }, new HandlebarsTemplateEngine());

        post("/school/new",(req, res) ->{
            Map<String, Object> model = new HashMap<>();
            String schoolName = req.queryParams("schoolName");
            String institution = req.queryParams("institution");
            String administrator = req.queryParams("administrator");
            String email = req.queryParams("email");
            String schoolEmail = req.queryParams("schoolEmail");
            String location = req.queryParams("location");
            School school = new School(schoolName,institution,administrator,email,schoolEmail,location);
            model.put("school",school);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

//display
        get("/school-detail", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<School> school = School.getAll();
            model.put("school", school);
            return new ModelAndView(model, "school-detail.hbs");
        }, new HandlebarsTemplateEngine());

        get("/teachers-detail-form", (request, response) -> {

            return new ModelAndView(new HashMap(), "teachers-detail-form.hbs");

        }, new HandlebarsTemplateEngine());

        get("/students-detail-form", (request, response) -> {

            return new ModelAndView(new HashMap(), "students-detail-form.hbs");

        }, new HandlebarsTemplateEngine());

        get("/classes-form", (request, response) -> {

            return new ModelAndView(new HashMap(), "classes-form.hbs");

        }, new HandlebarsTemplateEngine());


        get("/school", (request, response) -> {

            return new ModelAndView(new HashMap(), "school.hbs");

        }, new HandlebarsTemplateEngine());
    }
}
