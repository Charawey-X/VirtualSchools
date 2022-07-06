package org.example.interfaces;

import org.example.models.Subject;

import java.util.List;

public interface ISubject {
    // TODO: crud implementation
    //create implementation
    void create(Subject subject);

    //read implementation
    Subject read(int id);

    //read all implementation
    List<Subject> readAll();

    //update implementation
    void update(Subject subject);

    //delete implementation
    void delete(int id);
}
