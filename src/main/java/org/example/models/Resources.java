package org.example.models;

public class Resources {
    private int id;
    private String name;
    private String description;
    private String createdat;
    private String updatedat;
    private String url;
    private String type;
    private String userid;
    private String access;

    public Resources(String name, String description, String created_at, String updated_at, String url, String type, String userid, String access) {
        this.name = name;
        this.description = description;
        this.createdat = created_at;
        this.updatedat = updated_at;
        this.url = url;
        this.type = type;
        this.userid = userid;
        this.access = access;
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

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(String updatedat) {
        this.updatedat = updatedat;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
