package org.example.interfaces;

import org.example.models.School;

import java.util.List;

public interface ISchool {
    //crud
    //create operation
    public void createSchool(School school);

    //read operation
    public School getSchool(int id);

    //read all operation
    public List<School> getAllSchools();

    //update operation
    public void updateSchool(School school);

    //delete operation
    public void deleteSchool(int id);
}

