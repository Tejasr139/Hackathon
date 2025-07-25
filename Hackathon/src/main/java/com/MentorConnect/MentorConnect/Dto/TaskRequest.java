package com.MentorConnect.MentorConnect.Dto;

import lombok.Data;

@Data
public class TaskRequest {
    private String menteeEmail;
    private String task;

    public String getMenteeEmail() {
        return menteeEmail;
    }

    public String getTask() {
        return task;
    }

    public void setMenteeEmail(String menteeEmail) {
        this.menteeEmail = menteeEmail;
    }

    public void setTask(String task) {
        this.task = task;
    }


}
