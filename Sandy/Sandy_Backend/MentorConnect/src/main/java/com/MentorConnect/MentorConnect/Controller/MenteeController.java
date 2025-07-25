package com.MentorConnect.MentorConnect.Controller;

import com.MentorConnect.MentorConnect.Dto.ConnectionRequestDTO;
import com.MentorConnect.MentorConnect.Service.ConnectionReqService;
import com.MentorConnect.MentorConnect.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/mentee")
@PreAuthorize("hasAuthority('MENTEE')")
public class MenteeController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private ConnectionReqService connectionReqService;

     @PostMapping("/complete-task/{taskId}")
     public ResponseEntity<String> completeTask(@PathVariable Long taskId) {
             taskService.markTaskAsCompleted(taskId);
             return ResponseEntity.ok("Task marked as completed.");
     }

     @PostMapping("/send-request")
    public ResponseEntity<String> sendRequest(@RequestBody ConnectionRequestDTO dto){
         connectionReqService.sendConnectionRequest(dto.getMenteeEmail(), dto.getMentorEmail());
         return ResponseEntity.ok("Request Sent SuccessFully");
     }
}
