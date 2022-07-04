package org.example.models;

public class Users {
    private int id;
    private String name;
    private String email;
    private String password;
    private String createdat;
    private String updatedat;
    private String role;

    public Users(String name, String email, String password, String created_at, String updated_at, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdat = created_at;
        this.updatedat = updated_at;
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated_at() {
        return createdat;
    }

    public void setCreated_at(String created_at) {
            this.createdat = created_at;
    }

    public String getUpdated_at() {
        return updatedat;
    }

    public void setUpdated_at(String updated_at) {
        this.updatedat = updated_at;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
