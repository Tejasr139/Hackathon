package com.MentorConnect.MentorConnect.Controller;


import com.MentorConnect.MentorConnect.Dto.MentorReviewDto;
import com.MentorConnect.MentorConnect.Dto.TaskRequest;
import com.MentorConnect.MentorConnect.Entity.Task;
import com.MentorConnect.MentorConnect.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

//    // Mentor assigns a task to a mentee
//    @PostMapping("/assign")
//    public String assignTask(@RequestBody TaskRequest taskRequest) {
//        taskService.assignTaskToMentee(taskRequest.getMenteeEmail(), taskRequest.getTask());
//        return "Task assigned successfully";
//    }
//
//    // Mentee views all tasks assigned to them
//    @GetMapping("/mentee/{menteeEmail}")
//    public List<Task> getTasksForMentee(@PathVariable String menteeEmail) {
//        return taskService.getTasksForMentee(menteeEmail);
//    }
//
//    // Mentee marks a task as completed
//    @PutMapping("/complete/{taskId}")
//    public String completeTask(@PathVariable Long taskId) {
//        taskService.markTaskAsCompleted(taskId);
//        return "Task marked as completed";
//    }

    @PostMapping("/assign")
    public String assignTask(@RequestBody TaskRequest taskRequest) {
        taskService.assignTaskToMentee(taskRequest.getMenteeEmail(), taskRequest.getTask());
        return "Task assigned successfully";
    }

    @GetMapping("/mentee/{menteeEmail}")
    public List<Task> getTasksForMentee(@PathVariable String menteeEmail) {
        return taskService.getTasksForMentee(menteeEmail);
    }

    @PutMapping("/complete/{taskId}")
    public String completeTask(@PathVariable Long taskId) {
        taskService.markTaskAsCompleted(taskId);
        return "Task marked as completed";
    }

    @PutMapping("/send-review/{taskId}")
    public String sendForReview(@PathVariable Long taskId) {
        taskService.sendTaskForReview(taskId);
        return "Task sent for mentor review";
    }

    @PostMapping("/review")
    public String reviewTask(@RequestBody MentorReviewDto reviewDto) {
        taskService.reviewTask(reviewDto.getTaskId(), reviewDto.getFeedback(), reviewDto.getRating());
        return "Review submitted successfully";
    }

    @GetMapping("/mentor/pending")
    public List<Task> getPendingReviews() {
        return taskService.getTasksForReview();
    }
}
