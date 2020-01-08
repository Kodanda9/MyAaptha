package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 6/5/2018.
 */

public class LabDetails_Model {
    String id;
    String  testName;
    String  priceAtLab;
    String  priceAtHome;
    String  discount;
    String  remarks;
    String  fullName;
    String  status;
    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "LabDetails_Model{" +
                "id='" + id + '\'' +
                ", testName='" + testName + '\'' +
                ", priceAtLab='" + priceAtLab + '\'' +
                ", priceAtHome='" + priceAtHome + '\'' +
                ", discount='" + discount + '\'' +
                ", remarks='" + remarks + '\'' +
                ", fullName='" + fullName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
