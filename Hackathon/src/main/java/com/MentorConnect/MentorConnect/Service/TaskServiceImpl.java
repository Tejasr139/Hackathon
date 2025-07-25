package com.MentorConnect.MentorConnect.Service;

import com.MentorConnect.MentorConnect.Entity.Task;
import com.MentorConnect.MentorConnect.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void assignTaskToMentee(String menteeEmail, String taskDescription) {
        Task task = new Task();
        task.setMenteeEmail(menteeEmail);
        task.setDescription(taskDescription);
        task.setCompleted(false);
        taskRepository.save(task);
    }

    @Override
    public List<Task> getTasksForMentee(String menteeEmail) {
        return taskRepository.findByMenteeEmail(menteeEmail);
    }

    @Override
    public void markTaskAsCompleted(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
        task.setCompleted(true);
        taskRepository.save(task);
    }

    @Override
    public void sendTaskForReview(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
        task.setSentForReview(true);
        taskRepository.save(task);
    }

    @Override
    public void reviewTask(Long taskId, String feedback, Integer rating) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
        task.setMentorFeedback(feedback);
        task.setRating(rating);
        task.setReviewed(true);
        task.setCompleted(true); // Mark as completed after review
        taskRepository.save(task);
    }

    @Override
    public List<Task> getTasksForReview() {
        return taskRepository.findAll()
                .stream()
                .filter(task -> task.isSentForReview() && !task.isReviewed())
                .toList();
    }

}
