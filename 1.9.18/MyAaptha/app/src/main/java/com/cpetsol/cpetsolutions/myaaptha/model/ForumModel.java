package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 10/13/2017.
 */

public class ForumModel {
    String id;
    String question;
    String categories;
    String subCategories;
    String viewedBy;
    String repliedBy;
    String dateCreated;
    String viewStatus;
    String likes;

    public ForumModel() {
    }

    public ForumModel(String id, String question, String categories, String subCategories, String viewedBy, String repliedBy, String dateCreated, String viewStatus, String likes) {
        this.id = id;
        this.question = question;
        this.categories = categories;
        this.subCategories = subCategories;
        this.viewedBy = viewedBy;
        this.repliedBy = repliedBy;
        this.dateCreated = dateCreated;
        this.viewStatus = viewStatus;
        this.likes = likes;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }
    public String getSubCategories() {
        return subCategories;
    }
    public void setSubCategories(String subCategories) {
        this.subCategories = subCategories;
    }
    public String getViewedBy() {
        return viewedBy;
    }
    public void setViewedBy(String viewedBy) {
        this.viewedBy = viewedBy;
    }
    public String getRepliedBy() {
        return repliedBy;
    }
    public void setRepliedBy(String repliedBy) {
        this.repliedBy = repliedBy;
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
    public String getLikes() { return likes;    }
    public void setLikes(String likes) {   this.likes = likes;    }
}
