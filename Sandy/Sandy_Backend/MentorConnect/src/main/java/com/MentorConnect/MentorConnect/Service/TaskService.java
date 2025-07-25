package com.MentorConnect.MentorConnect.Service;

import com.MentorConnect.MentorConnect.Entity.Task;

import java.util.List;

public interface TaskService {
    void assignTaskToMentee(String menteeEmail, String taskDescription);
    List<Task> getTasksForMentee(String menteeEmail);
    void markTaskAsCompleted(Long taskId);
}
