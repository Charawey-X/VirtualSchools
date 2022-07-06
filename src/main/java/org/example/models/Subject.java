package org.example.models;

import java.util.Objects;

public class Subject {
    private int id;
    private School school;
    private Level level;
    private Teacher teacher;
    private String name;

    public Subject(School school, Level level, Teacher teacher, String name) {
        this.school = school;
        this.level = level;
        this.teacher = teacher;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
