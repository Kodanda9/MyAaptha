package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 5/18/2018.
 */

public class MedTestModel {
    String drugName,drugType,dosage,qty,instruction;
    int id;
    public MedTestModel() {
    }

    public MedTestModel(int i,String drugName, String drugType, String dosage, String qty, String instruction) {
        this.id = i;
        this.drugName = drugName;
        this.drugType = drugType;
        this.dosage = dosage;
        this.qty = qty;
        this.instruction = instruction;
    }
    public MedTestModel(String drugName, String drugType, String dosage, String qty, String instruction) {
        this.drugName = drugName;
        this.drugType = drugType;
        this.dosage = dosage;
        this.qty = qty;
        this.instruction = instruction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
