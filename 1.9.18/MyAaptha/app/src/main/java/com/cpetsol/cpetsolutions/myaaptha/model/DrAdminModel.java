package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 7/13/2018.
 */

public class DrAdminModel {

    String doctorId;
    String name;
    String qulification;
    String experience;
    String location;
    String localities;
    String specilization;
    String medicalCouncilNo;
    String stateMedicalCouncil;
    String qualificationYear;
    String universityName;
    String docRegYear;
    String statu;
    String description;
    String dateCreated;
    String viewStatus;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQulification() {
        return qulification;
    }

    public void setQulification(String qulification) {
        this.qulification = qulification;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
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

    public String getSpecilization() {
        return specilization;
    }

    public void setSpecilization(String specilization) {
        this.specilization = specilization;
    }

    public String getMedicalCouncilNo() {
        return medicalCouncilNo;
    }

    public void setMedicalCouncilNo(String medicalCouncilNo) {
        this.medicalCouncilNo = medicalCouncilNo;
    }

    public String getStateMedicalCouncil() {
        return stateMedicalCouncil;
    }

    public void setStateMedicalCouncil(String stateMedicalCouncil) {
        this.stateMedicalCouncil = stateMedicalCouncil;
    }

    public String getQualificationYear() {
        return qualificationYear;
    }

    public void setQualificationYear(String qualificationYear) {
        this.qualificationYear = qualificationYear;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getDocRegYear() {
        return docRegYear;
    }

    public void setDocRegYear(String docRegYear) {
        this.docRegYear = docRegYear;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "DrAdminModel{" +
                "doctorId='" + doctorId + '\'' +
                ", name='" + name + '\'' +
                ", qulification='" + qulification + '\'' +
                ", experience='" + experience + '\'' +
                ", location='" + location + '\'' +
                ", localities='" + localities + '\'' +
                ", specilization='" + specilization + '\'' +
                ", medicalCouncilNo='" + medicalCouncilNo + '\'' +
                ", stateMedicalCouncil='" + stateMedicalCouncil + '\'' +
                ", qualificationYear='" + qualificationYear + '\'' +
                ", universityName='" + universityName + '\'' +
                ", docRegYear='" + docRegYear + '\'' +
                ", statu='" + statu + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", viewStatus='" + viewStatus + '\'' +
                '}';
    }
}