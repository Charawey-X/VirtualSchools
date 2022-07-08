package org.example.models;

import java.util.List;
import java.util.Objects;

public class Teacher {
    private int id;
    private School school;
    private String name;
    private List<Subject> subjects;

    public Teacher(School school, String name, List<Subject> subjects) {
        this.school = school;
        this.name = name;
        this.subjects = subjects;
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

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id && school.equals(teacher.school) && name.equals(teacher.name) && subjects.equals(teacher.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, school, name, subjects);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", school=" + school +
                ", name='" + name + '\'' +
                ", subjects=" + subjects +
                '}';
    }
}
