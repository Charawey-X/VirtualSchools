package org.example.models;

import java.util.ArrayList;

public class School {
    private String schoolName;
    private String institution;
    private String administrator;
    private String email;
    private String schoolEmail;
    private String location;
    private int id;
    private static ArrayList<School> instances = new ArrayList<>();

    public School(String schoolName, String institution, String administrator, String email, String schoolEmail, String location) {
        this.schoolName = schoolName;
        this.institution = institution;
        this.administrator = administrator;
        this.email = email;
        this.schoolEmail = schoolEmail;
        this.location = location;
        instances.add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }


    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static ArrayList<School> getAll() {
        return instances;
    }

    public static void setInstances(ArrayList<School> instances) {
        School.instances = instances;
    }
}
