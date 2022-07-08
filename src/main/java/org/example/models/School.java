package org.example.models;

import org.example.enums.Level;

import java.util.List;
import java.util.Objects;

public class School {
    private int id;
    private String name;
    private String description;
    private Level level;
    private List<Subject> courses;
    private List<Student> students;
    private List<Teacher> teachers;


    public School(int id, String name, String description, Level level, List<Subject> courses, List<Teacher> teachers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.courses = courses;
        this.teachers = teachers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public List<Subject> getCourses() {
        return courses;
    }

    public void setCourses(List<Subject> courses) {
        this.courses = courses;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return id == school.id && name.equals(school.name) && description.equals(school.description) && level.equals(school.level) && courses.equals(school.courses) && students.equals(school.students) && teachers.equals(school.teachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, level, courses, students, teachers);
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", level=" + level +
                ", courses=" + courses +
                ", students=" + students +
                ", teachers=" + teachers +
                '}';
    }
}
