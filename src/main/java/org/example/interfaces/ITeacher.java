package org.example.interfaces;

import org.example.models.Teacher;

import java.util.List;

public interface ITeacher {
    //crud
    //create
    void createTeacher(Teacher teacher);

    //read
    Teacher getTeacher(int id);
    List<Teacher> getAllTeachers();

    //update
    void updateTeacher(Teacher teacher);

    //delete
    void deleteTeacher(int id);

}
