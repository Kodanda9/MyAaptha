package com.cpetsol.cpetsolutions.myaaptha.model;

/**
 * Created by Admin on 7/26/2018.
 */

public class CommentsModel {
    String id;
    String answer;
    String referenceId;
    String dateCreated;
    String fullName;

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    @Override
    public String toString() {
        return "CommentsModel{" +
                "id='" + id + '\'' +
                ", answer='" + answer + '\'' +
                ", referenceId='" + referenceId + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
