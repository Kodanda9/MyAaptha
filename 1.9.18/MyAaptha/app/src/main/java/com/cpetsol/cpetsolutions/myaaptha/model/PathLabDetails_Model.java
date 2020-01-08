package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 6/6/2018.
 */

public class PathLabDetails_Model  {
   String id;
    String labRegNo;
    String labOwnerName;
    String approvalBy;
    String labRegYear;
    String labName;
    String qualificationYear;
    String pathLabId;
    String stateCode;
    String districtCode;
    String dateCreated;
    String viewStatus;
    String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabRegNo() {
        return labRegNo;
    }

    public void setLabRegNo(String labRegNo) {
        this.labRegNo = labRegNo;
    }

    public String getLabOwnerName() {
        return labOwnerName;
    }

    public void setLabOwnerName(String labOwnerName) {
        this.labOwnerName = labOwnerName;
    }

    public String getApprovalBy() {
        return approvalBy;
    }

    public void setApprovalBy(String approvalBy) {
        this.approvalBy = approvalBy;
    }

    public String getLabRegYear() {
        return labRegYear;
    }

    public void setLabRegYear(String labRegYear) {
        this.labRegYear = labRegYear;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getQualificationYear() {
        return qualificationYear;
    }

    public void setQualificationYear(String qualificationYear) {
        this.qualificationYear = qualificationYear;
    }

    public String getPathLabId() {
        return pathLabId;
    }

    public void setPathLabId(String pathLabId) {
        this.pathLabId = pathLabId;
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

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PathLabDetails_Model{" +
                "id='" + id + '\'' +
                ", labRegNo='" + labRegNo + '\'' +
                ", labOwnerName='" + labOwnerName + '\'' +
                ", approvalBy='" + approvalBy + '\'' +
                ", labRegYear='" + labRegYear + '\'' +
                ", labName='" + labName + '\'' +
                ", qualificationYear='" + qualificationYear + '\'' +
                ", pathLabId='" + pathLabId + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", districtCode='" + districtCode + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", viewStatus='" + viewStatus + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
