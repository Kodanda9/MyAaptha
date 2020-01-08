package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 7/13/2018.
 */

public class HospitalOwnerModel {

    String pid;
    String name;
    String employmentType;
    String hospitalName;
    String hospitalId;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Override
    public String toString() {
        return "HospitalOwnerModel{" +
                "pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                '}';
    }
}
