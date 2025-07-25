package com.MentorConnect.MentorConnect.Service;

import com.MentorConnect.MentorConnect.Entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    void assignTaskToMentee(String menteeEmail, String taskDescription);
    List<Task> getTasksForMentee(String menteeEmail);
    void markTaskAsCompleted(Long taskId);
    void sendTaskForReview(Long taskId);
    void reviewTask(Long taskId, String feedback, Integer rating);
    List<Task> getTasksForReview();
}
