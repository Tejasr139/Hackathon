package com.MentorConnect.MentorConnect.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String menteeEmail;
    private String description;
    private boolean completed = false;

    private boolean sentForReview = false;
    private boolean reviewed = false;
    private String mentorFeedback;
    private Integer rating;




    public void setMenteeEmail(String menteeEmail) {
        this.menteeEmail = menteeEmail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public String getMenteeEmail() {
        return menteeEmail;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSentForReview() {
        return sentForReview;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public String getMentorFeedback() {
        return mentorFeedback;
    }

    public Integer getRating() {
        return rating;
    }

    public void setSentForReview(boolean sentForReview) {
        this.sentForReview = sentForReview;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public void setMentorFeedback(String mentorFeedback) {
        this.mentorFeedback = mentorFeedback;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
