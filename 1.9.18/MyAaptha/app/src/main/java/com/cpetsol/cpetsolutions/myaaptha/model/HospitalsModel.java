package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 7/7/2018.
 */

public class HospitalsModel {
             String hospitalId;
             String hospitalName;
             String location;
             String localities;
             String address;
             String status;
             String longitude;
             String registrationNo;
             String roc;
             String registrationYear;
             String telephoneNo;
             String mobileNo;
             String details;
             String websiteUrl;
             String dateCreated;
             String viewStatus;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocalities() {
        return localities;
    }

    public void setLocalities(String localities) {
        this.localities = localities;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getRoc() {
        return roc;
    }

    public void setRoc(String roc) {
        this.roc = roc;
    }

    public String getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(String registrationYear) {
        this.registrationYear = registrationYear;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
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
        return "HospitalsModel{" +
                "hospitalId='" + hospitalId + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", location='" + location + '\'' +
                ", localities='" + localities + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", longitude='" + longitude + '\'' +
                ", registrationNo='" + registrationNo + '\'' +
                ", roc='" + roc + '\'' +
                ", registrationYear='" + registrationYear + '\'' +
                ", telephoneNo='" + telephoneNo + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", details='" + details + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", viewStatus='" + viewStatus + '\'' +
                '}';
    }
}
