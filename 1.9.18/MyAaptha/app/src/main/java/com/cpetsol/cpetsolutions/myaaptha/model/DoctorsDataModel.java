package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 10/14/2017.
 */

public class DoctorsDataModel {
    String doctorId;
    String name;
    String qulification;
    String experience;
    String localities;
    String specilization;
    String weekday;
    String hospitalName;
    String clinicName;
    String doctorAvailability;
    String price;
    String availableDate;
    String gender;
    String sunday;
    String monday;
    String tuesday;
    String wednesday;
    String thursday;
    String friday;
    String saturday;
   String hospitalId;

    public DoctorsDataModel() {
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public DoctorsDataModel(String doctorId, String name, String qulification, String experience, String localities, String specilization, String hospitalName, String clinicName, String price, String gender, String sunday,
                            String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String hospitalId) {
        this.doctorId = doctorId;
        this.name = name;
        this.qulification = qulification;
        this.experience = experience;
        this.localities = localities;
        this.specilization = specilization;

        this.hospitalName = hospitalName;
        this.clinicName = clinicName;

        this.price = price;

        this.gender = gender;
        this.sunday=sunday;
        this.monday=monday;
        this.tuesday=tuesday;
        this.wednesday=wednesday;
        this.thursday=thursday;
        this.friday=friday;
        this.saturday=saturday;
        this.hospitalId=hospitalId;
    }

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

    public String getHospitalName() {
        return hospitalName;
    }
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    public String getClinicName() {
        return clinicName;
    }
    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
}
