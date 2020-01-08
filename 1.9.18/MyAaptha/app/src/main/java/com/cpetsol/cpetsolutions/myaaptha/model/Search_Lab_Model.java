package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 6/6/2018.
 */

public class Search_Lab_Model {

    String labTestNames;
    String doctorName;
    String hospitalName;
    String patientName;
    String presciptionDate;
    String age;
    String gender;
    String profileId;
    String permanentAddress;
    String emailId;
    String mobileNo;
    String labTestName;

    public String getLabTestNames() {
        return labTestNames;
    }

    public void setLabTestNames(String labTestNames) {
        this.labTestNames = labTestNames;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPresciptionDate() {
        return presciptionDate;
    }

    public void setPresciptionDate(String presciptionDate) {
        this.presciptionDate = presciptionDate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getLabTestName() {
        return labTestName;
    }

    public void setLabTestName(String labTestName) {
        this.labTestName = labTestName;
    }

    @Override
    public String toString() {
        return "Search_Lab_Model{" +
                "labTestNames='" + labTestNames + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", patientName='" + patientName + '\'' +
                ", presciptionDate='" + presciptionDate + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", profileId='" + profileId + '\'' +
                ", permanentAddress='" + permanentAddress + '\'' +
                ", emailId='" + emailId + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", labTestName='" + labTestName + '\'' +
                '}';
    }
}
