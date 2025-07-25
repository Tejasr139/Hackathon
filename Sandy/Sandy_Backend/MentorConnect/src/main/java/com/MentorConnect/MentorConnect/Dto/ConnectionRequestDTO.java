package com.MentorConnect.MentorConnect.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionRequestDTO {
    private String menteeEmail;
    private String mentorEmail;
}
