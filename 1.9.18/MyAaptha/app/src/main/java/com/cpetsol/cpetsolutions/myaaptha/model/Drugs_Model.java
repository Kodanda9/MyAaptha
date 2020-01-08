package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 5/31/2018.
 */

public class Drugs_Model {
    Integer id;
    String quantity;
    String drugName;
    String dosage;
    String price;
    String thresholdQuantity;
    String discount;

    @Override
    public String toString() {
        return "Drugs_Model{" +
                "id=" + id +
                ", quantity='" + quantity + '\'' +
                ", drugName='" + drugName + '\'' +
                ", dosage='" + dosage + '\'' +
                ", price=" + price +
                ", thresholdQuantity=" + thresholdQuantity +
                ", discount=" + discount +
                '}';
    }



    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getThresholdQuantity() {
        return thresholdQuantity;
    }

    public void setThresholdQuantity(String thresholdQuantity) {
        this.thresholdQuantity = thresholdQuantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }



}
