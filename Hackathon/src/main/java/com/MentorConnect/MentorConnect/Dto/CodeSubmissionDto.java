package com.MentorConnect.MentorConnect.Dto;

import lombok.Data;

@Data
public class CodeSubmissionDto {
    private String menteeEmail;
//    private String taskTitle;
    private String taskDescription;
    private String language;
    private String code;
    private String output;
}
