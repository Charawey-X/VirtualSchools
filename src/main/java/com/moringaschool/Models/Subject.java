package com.moringaschool.Models;

import java.util.Objects;

public class Subject {
    int id;
    School school;
    Level level;
    Teacher teacher;
    String name;

    public Subject(int id, School school, Level level, Teacher teacher, String name) {
        this.id = id;
        this.school = school;
        this.level = level;
        this.teacher = teacher;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return id == subject.id && school.equals(subject.school) && level.equals(subject.level) && teacher.equals(subject.teacher) && name.equals(subject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, school, level, teacher, name);
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

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", school=" + school +
                ", level=" + level +
                ", teacher=" + teacher +
                ", name='" + name + '\'' +
                '}';
    }
}
