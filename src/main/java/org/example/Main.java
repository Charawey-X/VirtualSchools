package org.example;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;

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

        get("/teachers-detail-form", (request, response) -> {

            return new ModelAndView(new HashMap(), "teachers-detail-form.hbs");

        }, new HandlebarsTemplateEngine());

        get("/students-detail-form", (request, response) -> {

            return new ModelAndView(new HashMap(), "students-detail-form.hbs");

        }, new HandlebarsTemplateEngine());
    }
}
