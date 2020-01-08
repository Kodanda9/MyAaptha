package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 8/21/2018.
 */

public class WebPagesModel {
 String id;
    String name;
    String url;
    String level;
    String orderNumber;
    String viewStatus;
    String dateCreated;
    String refId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    @Override
    public String toString() {
        return "WebPagesModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", level='" + level + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", viewStatus='" + viewStatus + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", refId='" + refId + '\'' +
                '}';
    }
}
