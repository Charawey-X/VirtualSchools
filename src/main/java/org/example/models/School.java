package org.example.models;

import org.example.enums.Level;

import java.util.List;
import java.util.Objects;

public class School {
    private int id;
    private String name;
    private String description;

    public School(int id, String name, String description, Level level, List<String> courses, List<String> teachers) {
        this.id = id;
        this.name = name;
        this.description = description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return id == school.id && name.equals(school.name) && description.equals(school.description) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }


}
