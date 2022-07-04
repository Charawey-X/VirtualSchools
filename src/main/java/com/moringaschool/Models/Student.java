package com.moringaschool.Models;

import java.util.List;
import java.util.Objects;

public class Student {
    private int studentId;
    private String studentName;
    private School school;
    private List<Subject> courses;

    public Student(String studentName, School school, List<Subject> courses) {
        this.studentName = studentName;
        this.school = school;
        this.courses = courses;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Subject> getCourses() {
        return courses;
    }

    public void setCourses(List<Subject> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", school=" + school +
                ", courses=" + courses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId == student.studentId && studentName.equals(student.studentName) && school.equals(student.school) && courses.equals(student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentName, school, courses);
    }
}
