package org.example;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");
        port(7071);

        get("/",(request, response) -> {
            return new ModelAndView(new HashMap(),"landingpage.hbs");
        },new HandlebarsTemplateEngine());


        //form admin
        get( "/admin/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "login.hbs");
        }, new HandlebarsTemplateEngine());

        //form teacher
        get("/login/new/teacher",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"login.hbs");
        },new HandlebarsTemplateEngine());

        //form register
        get("/register/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"register.hbs");
        },new HandlebarsTemplateEngine());

        //form login
        get("/login/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"login.hbs");
        },new HandlebarsTemplateEngine());

        //form resource
        get("/resources/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"resources.hbs");
        },new HandlebarsTemplateEngine());

        //form resource
        get("/attendance/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"attendance.hbs");
        },new HandlebarsTemplateEngine());
        //form resource
        get("/grades/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"grades.hbs");
        },new HandlebarsTemplateEngine());
        get("/logout",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"login.hbs");
        },new HandlebarsTemplateEngine());


    }
}