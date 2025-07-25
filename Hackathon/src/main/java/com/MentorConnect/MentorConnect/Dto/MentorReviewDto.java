package com.MentorConnect.MentorConnect.Dto;

import lombok.Data;

@Data
public class MentorReviewDto {
    private Long taskId;
    private String feedback;
    private Integer rating;

}
