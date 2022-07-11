package org.example.models;

public class Attendance {
    private int id;
    private int userid;
    private String date;
    private String activity;
    private String resource;

    public Attendance(int userid, String date, String activity, String resource) {
        this.userid = userid;
        this.date = date;
        this.activity = activity;
        this.resource = resource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
