package org.example.interfaces;

import org.example.models.Student;

import java.util.List;

public interface IStudent {
    //crud

    //create operation
    public void createStudent(Student student);

    //read operation
    public Student getStudent(int id);

    //read all operation
    public List<Student> getAllStudents();

    //update operation
    public void updateStudent(Student student);

    //delete operation
    public void deleteStudent(int id);
}
