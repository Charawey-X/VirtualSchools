package com.moringaschool.Models;

import java.util.Objects;

public class Teacher {
    int id;
    School school;
    String name;

    public Teacher(int id, School school, String name) {
        this.id = id;
        this.school = school;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id && school.equals(teacher.school) && name.equals(teacher.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, school, name);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", school=" + school +
                ", name='" + name + '\'' +
                '}';
    }
}
