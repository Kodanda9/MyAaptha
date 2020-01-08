package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 5/30/2018.
 */

public class Medicine_Hall_Model {

     String id ;
     String drugName;
     String details;
     String sideEffects;
     String status;
     String userDetails;
     String dateCreated;
     String viewStatus;

    public Medicine_Hall_Model( ) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }

    @Override
    public String toString() {
        return "Medicine_Hall_Model{" +
                "id='" + id + '\'' +
                ", drugName='" + drugName + '\'' +
                ", details='" + details + '\'' +
                ", sideEffects='" + sideEffects + '\'' +
                ", status='" + status + '\'' +
                ", userDetails='" + userDetails + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", viewStatus='" + viewStatus + '\'' +
                '}';
    }
}
