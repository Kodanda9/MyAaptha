package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 6/4/2018.
 */

public class Search_Medicime_Model {
            String name;
            String drugName;
            String dosage ;
            String drugType;
            String qty;
            String inTime;

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    @Override
    public String toString() {
        return "Search_Medicime_Model{" +
                "name='" + name + '\'' +
                ", drugName='" + drugName + '\'' +
                ", dosage='" + dosage + '\'' +
                ", drugType='" + drugType + '\'' +
                ", qty='" + qty + '\'' +
                ", inTime='" + inTime + '\'' +
                '}';
    }
}
