package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 6/21/2018.
 */

public class PrescriptionModel {
    String id;
    String drugName;
    String   dosage;
    String drugType;
    String instruction;
    String qty;

    String testName;
    String priceAtLab;
    String priceAtHome;
    String discount;
    String remarks;
    String fullName;
    String status;
    String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getPriceAtLab() {
        return priceAtLab;
    }

    public void setPriceAtLab(String priceAtLab) {
        this.priceAtLab = priceAtLab;
    }

    public String getPriceAtHome() {
        return priceAtHome;
    }

    public void setPriceAtHome(String priceAtHome) {
        this.priceAtHome = priceAtHome;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PrescriptionModel{" +
                "id='" + id + '\'' +
                ", drugName='" + drugName + '\'' +
                ", dosage='" + dosage + '\'' +
                ", drugType='" + drugType + '\'' +
                ", instruction='" + instruction + '\'' +
                ", qty='" + qty + '\'' +
                ", testName='" + testName + '\'' +
                ", priceAtLab='" + priceAtLab + '\'' +
                ", priceAtHome='" + priceAtHome + '\'' +
                ", discount='" + discount + '\'' +
                ", remarks='" + remarks + '\'' +
                ", fullName='" + fullName + '\'' +
                ", status='" + status + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
