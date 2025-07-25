package com.MentorConnect.MentorConnect.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.MentorConnect.MentorConnect.Service.OpenAIService;


@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/suggest")
    public ResponseEntity<String> getSuggestions(@RequestBody Map<String, String> request) {
        String taskDescription = request.get("task");
        try {
            String response = openAIService.getSuggestions(taskDescription);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/review")
    public ResponseEntity<String> reviewTask(@RequestBody Map<String, String> request) {
        String task = request.get("task");
        String submission = request.get("submission");
        try {
            String response = openAIService.reviewTask(task, submission);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}

