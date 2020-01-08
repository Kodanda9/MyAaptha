package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by AILOGIC3 on 3/24/2018.
 */

public class ArticalAnswersModel {
    String id;
    String answer;
    String referenceId;
    String forum;
    String userDetails;
    String dateCreated;
    String viewStatus;
    String likes;
    String dislikes;

    public ArticalAnswersModel(String id, String answer, String referenceId, String forum, String userDetails,
                               String dateCreated, String viewStatus, String likes, String dislikes) {
        this.id=id;
        this.answer=answer;
        this.referenceId=referenceId;
        this.forum=forum;
        this.userDetails=userDetails;
        this.dateCreated=dateCreated;
        this.viewStatus=viewStatus;
        this.likes=likes;
        this.dislikes=dislikes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getForum() {
        return forum;
    }

    public void setForum(String forum) {
        this.forum = forum;
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

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }
}
