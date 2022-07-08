package org.example.models;

import java.util.List;
import java.util.Objects;

public class Student {
    private int studentId;
    private String studentName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    private String email;
    private int schoolId;
    private List<Subject> courses;

    public Student(String studentName, String email, int schoolId, List<Subject> courses) {
        this.studentName = studentName;
        this.schoolId = schoolId;
        this.courses = courses;
        this.email = email;
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

    public int getSchool() {
        return schoolId;
    }

    public void setSchool(int schoolId) {
        this.schoolId = schoolId;
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
                ", schoolId=" + schoolId +
                ", courses=" + courses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId == student.studentId && schoolId == student.schoolId && studentName.equals(student.studentName) && courses.equals(student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentName, schoolId, courses);
    }
}
