package com.example.virtualschools.models;

public class UpdateStudent {

    private String name;

    public UpdateStudent(String name) {
        this.name = name;
    }

    public UpdateStudent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
