package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 5/30/2018.
 */

public class MedicineStockModel {
    String drugName;
    String drugType;
    String dosage;
    String quantity;
    String threshold;
    String agencyName;
    String remarks;
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @Override
    public String toString() {
        return "MedicineStockModel{" +
                "drugName='" + drugName + '\'' +
                ", drugType='" + drugType + '\'' +
                ", dosage='" + dosage + '\'' +
                ", quantity='" + quantity + '\'' +
                ", threshold='" + threshold + '\'' +
                ", agencyName='" + agencyName + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }


}
