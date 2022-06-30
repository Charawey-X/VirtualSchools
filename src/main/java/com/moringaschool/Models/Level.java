package com.moringaschool.Models;

import java.util.Objects;

public class Level {
    int id;
    School school;
    String name;

    public Level(int id, School school, String name) {
        this.id = id;
        this.school = school;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Level)) return false;
        Level level = (Level) o;
        return getId() == level.getId() && getSchool().equals(level.getSchool()) && getName().equals(level.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSchool(), getName());
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
        return "Level{" +
                "id=" + id +
                ", school=" + school +
                ", name='" + name + '\'' +
                '}';
    }
}
