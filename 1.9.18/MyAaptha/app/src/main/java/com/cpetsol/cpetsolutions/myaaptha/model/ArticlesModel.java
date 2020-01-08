package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by AILOGIC3 on 3/24/2018.
 */

public class ArticlesModel {
    String files;
    String id;
    String articleName;
    String overview;
    String tags;
    String publishDate;
    String articleDescription;
    String authors;
    String categories;
    String subCategories;
    String status;
    String views;
    String fileName;
    String dno;
    String userDetails;
    String dateCreated;
    String viewStatus;

    public ArticlesModel() {
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getDno() {
        return dno;
    }

    public void setDno(String dno) {
        this.dno = dno;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "ArticlesModel{" +
                "files='" + files + '\'' +
                ", id='" + id + '\'' +
                ", articleName='" + articleName + '\'' +
                ", overview='" + overview + '\'' +
                ", tags='" + tags + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", articleDescription='" + articleDescription + '\'' +
                ", authors='" + authors + '\'' +
                ", categories='" + categories + '\'' +
                ", subCategories='" + subCategories + '\'' +
                ", status='" + status + '\'' +
                ", views='" + views + '\'' +
                ", dno='" + dno + '\'' +
                ", userDetails='" + userDetails + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", viewStatus='" + viewStatus + '\'' +
                '}';
    }
}
