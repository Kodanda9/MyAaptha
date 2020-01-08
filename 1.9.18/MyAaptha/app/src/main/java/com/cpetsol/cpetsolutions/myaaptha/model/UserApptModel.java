package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 5/26/2018.
 */

public class UserApptModel {

String id;
String fullName;
String relation;
String status;
String message;
String specialization;
String timeSlot;
String bookedDate;
String hospitalName;
String appId;
String inTime;
String doctorId;
String points;
String price;
String familyNames;
String localities;
String qualification;
String company;
String fileName;
String preId;
String reportId;

    public UserApptModel( ) {
    }

    public UserApptModel(String id, String fullName, String relation, String status, String message, String specialization, String timeSlot, String bookedDate, String hospitalName, String appId, String inTime, String doctorId, String points, String price, String familyNames, String localities, String qualification, String company, String fileName, String preId, String reportId) {
        this.id = id;
        this.fullName = fullName;
        this.relation = relation;
        this.status = status;
        this.message = message;
        this.specialization = specialization;
        this.timeSlot = timeSlot;
        this.bookedDate = bookedDate;
        this.hospitalName = hospitalName;
        this.appId = appId;
        this.inTime = inTime;
        this.doctorId = doctorId;
        this.points = points;
        this.price = price;
        this.familyNames = familyNames;
        this.localities = localities;
        this.qualification = qualification;
        this.company = company;
        this.fileName = fileName;
        this.preId = preId;
        this.reportId = reportId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFamilyNames() {
        return familyNames;
    }

    public void setFamilyNames(String familyNames) {
        this.familyNames = familyNames;
    }

    public String getLocalities() {
        return localities;
    }

    public void setLocalities(String localities) {
        this.localities = localities;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPreId() {
        return preId;
    }

    public void setPreId(String preId) {
        this.preId = preId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}
