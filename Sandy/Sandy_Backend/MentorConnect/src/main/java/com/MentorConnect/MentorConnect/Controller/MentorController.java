package com.MentorConnect.MentorConnect.Controller;

import com.MentorConnect.MentorConnect.Dto.TaskRequest;
import com.MentorConnect.MentorConnect.Entity.Role;
import com.MentorConnect.MentorConnect.Entity.User;
import com.MentorConnect.MentorConnect.Service.TaskService;
import com.MentorConnect.MentorConnect.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/mentor")
@PreAuthorize("hasAuthority('MENTOR')")
public class MentorController {

    @Autowired
    private UserService userService; // For fetching mentees

    @Autowired
    private TaskService taskService; // For assigning tasks

    public MentorController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/mentees")
    public ResponseEntity<List<User>> getMentees() {
        List<User> mentees = userService.getUsersByRole("MENTEE");
        return ResponseEntity.ok(mentees);
    }

    @PostMapping("/assign-task")
    public ResponseEntity<String> assignTask(@RequestBody TaskRequest request) {
        taskService.assignTaskToMentee(request.getMenteeEmail(), request.getTask());
        return ResponseEntity.ok("Task assigned");
    }

//    @GetMapping("/mentees")
//    public List<User> getAllMentees(){
//        return userService.getAllMentees();
//    }

    @GetMapping("/mentors")
    public List<User> getAllMentors(){
        return userService.getUsersByRole("MENTOR");
    }
}
