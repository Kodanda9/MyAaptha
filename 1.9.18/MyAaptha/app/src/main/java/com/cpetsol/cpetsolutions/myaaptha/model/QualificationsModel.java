package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 7/4/2018.
 */

public class QualificationsModel {
    String id;
    String qualification;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return "QualificationsModel{" +
                "id='" + id + '\'' +
                ", qualification='" + qualification + '\'' +
                '}';
    }
}
