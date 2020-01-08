package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 7/20/2018.
 */

public class MsgSearchModel {
    String id;
    String fullName;
    String relation;

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

    @Override
    public String toString() {
        return "MsgSearchModel{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", relation='" + relation + '\'' +
                '}';
    }
}
