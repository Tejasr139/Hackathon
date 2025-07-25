package com.MentorConnect.MentorConnect.Controller;

import com.MentorConnect.MentorConnect.Dto.RequestResponseDTO;
import com.MentorConnect.MentorConnect.Entity.ConnectionRequest;
import com.MentorConnect.MentorConnect.Entity.User;
import com.MentorConnect.MentorConnect.Repository.ConnectionReqRepository;
import com.MentorConnect.MentorConnect.Service.ConnectionReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/connection")
@RequiredArgsConstructor
public class ConnectionReqController {
    private final ConnectionReqService connectionRequestService;

    @Autowired
    private ConnectionReqRepository connectionReqRepository;

    @PostMapping("/send-request")
    public ConnectionRequest sendConnectionRequest(@RequestParam String menteeEmail, @RequestParam String mentorEmail) {
        return connectionRequestService.sendConnectionRequest(menteeEmail, mentorEmail);
    }

    @GetMapping("/sent-requests")
    public List<ConnectionRequest> getSentRequests(@RequestParam String menteeEmail){
        return connectionReqRepository.findByMenteeEmail(menteeEmail);
    }

    @GetMapping("/requests")
    public List<ConnectionRequest> getRequests(@RequestParam String mentorEmail){
        return connectionReqRepository.findByMentorEmailAndStatus(mentorEmail, ConnectionRequest.Status.PENDING);
    }

    @PostMapping("/respond-request")
    public ResponseEntity<String> respondToRequest(@RequestBody RequestResponseDTO dto) {
        connectionRequestService.respondToRequest(dto.getRequestId(), dto.getAction());
        return ResponseEntity.ok("Request updated");
    }


    @GetMapping("/accepted-mentees")
    public List<User> getAcceptedMentees(@RequestParam String mentorEmail) {
        return connectionRequestService.getAcceptedMentees(mentorEmail);
    }


    @GetMapping("/accepted-mentors")
    public ResponseEntity<List<User>> getAcceptedMentors(@RequestParam String menteeEmail){
        List<User> mentors = connectionRequestService.getAcceptedMentors(menteeEmail);
        return ResponseEntity.ok(mentors);
    }



}
