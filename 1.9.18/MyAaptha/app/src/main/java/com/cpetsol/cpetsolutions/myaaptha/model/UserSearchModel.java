package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 5/17/2018.
 */

public class UserSearchModel {
    String id;
    String fullName;
    String relation;

    public UserSearchModel(String id, String fullName, String relation) {
        this.id = id;
        this.fullName = fullName;
        this.relation = relation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
