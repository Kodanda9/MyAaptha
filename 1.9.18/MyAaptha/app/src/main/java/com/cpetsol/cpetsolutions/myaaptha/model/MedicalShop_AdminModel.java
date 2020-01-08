package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 5/30/2018.
 */

public class MedicalShop_AdminModel {

    @Override
    public String toString() {
        return "MedicalShop_AdminModel{" +
                "id=" + id +
                ", medicalShopOwnerName='" + medicalShopOwnerName + '\'' +
                ", medicalShopRegNo='" + medicalShopRegNo + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", medicalShopRegYear='" + medicalShopRegYear + '\'' +
                ", medicalShopName='" + medicalShopName + '\'' +
                ", qualificationYear='" + qualificationYear + '\'' +
                ", medicalShopId='" + medicalShopId + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", districtCode='" + districtCode + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", viewStatus=" + viewStatus +
                '}';
    }

    Integer id;
    String medicalShopOwnerName;
    String medicalShopRegNo;
    String approvedBy;
    String medicalShopRegYear;
    String medicalShopName;
    String qualificationYear;
    String medicalShopId;
    String stateCode;
    String districtCode;
    String dateCreated;


    Integer viewStatus;

    public Integer getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(Integer viewStatus) {
        this.viewStatus = viewStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicalShopOwnerName() {
        return medicalShopOwnerName;
    }

    public void setMedicalShopOwnerName(String medicalShopOwnerName) {
        this.medicalShopOwnerName = medicalShopOwnerName;
    }

    public String getMedicalShopRegNo() {
        return medicalShopRegNo;
    }

    public void setMedicalShopRegNo(String medicalShopRegNo) {
        this.medicalShopRegNo = medicalShopRegNo;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getMedicalShopRegYear() {
        return medicalShopRegYear;
    }

    public void setMedicalShopRegYear(String medicalShopRegYear) {
        this.medicalShopRegYear = medicalShopRegYear;
    }

    public String getMedicalShopName() {
        return medicalShopName;
    }

    public void setMedicalShopName(String medicalShopName) {
        this.medicalShopName = medicalShopName;
    }

    public String getQualificationYear() {
        return qualificationYear;
    }

    public void setQualificationYear(String qualificationYear) {
        this.qualificationYear = qualificationYear;
    }

    public String getMedicalShopId() {
        return medicalShopId;
    }

    public void setMedicalShopId(String medicalShopId) {
        this.medicalShopId = medicalShopId;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }


}
