package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 8/14/2018.
 */

public class DosageDrugTypeModel {
  String id;
    String dosage;
    String drugType;
    String drugName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    @Override
    public String toString() {
        return "DosageDrugTypeModel{" +
                "id='" + id + '\'' +
                ", dosage='" + dosage + '\'' +
                ", drugType='" + drugType + '\'' +
                ", drugName='" + drugName + '\'' +
                '}';
    }
}
