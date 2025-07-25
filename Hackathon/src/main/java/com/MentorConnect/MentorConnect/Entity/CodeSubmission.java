package com.MentorConnect.MentorConnect.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "code_submissions")
public class CodeSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String menteeEmail;
//     private String taskTitle;
     private String taskDescription;
     private String language;

     @Column(length = 10000)
     private String code;

     @Column(length = 5000)
     private String output;

     private LocalDateTime submittedAt;


    public Long getId() {
        return id;
    }

    public String getMenteeEmail() {
        return menteeEmail;
    }

//    public String getTaskTitle() {
//        return taskTitle;
//    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }

    public String getOutput() {
        return output;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setMenteeEmail(String menteeEmail) {
        this.menteeEmail = menteeEmail;
    }

//    public void setTaskTitle(String taskTitle) {
//        this.taskTitle = taskTitle;
//    }

    public void setTaskDescription(String taskDescription) {

        this.taskDescription = taskDescription;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
