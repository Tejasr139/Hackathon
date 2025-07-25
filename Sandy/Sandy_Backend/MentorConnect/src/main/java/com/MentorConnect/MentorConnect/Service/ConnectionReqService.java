package com.MentorConnect.MentorConnect.Service;


import com.MentorConnect.MentorConnect.Entity.ConnectionRequest;
import com.MentorConnect.MentorConnect.Entity.User;
import com.MentorConnect.MentorConnect.Repository.ConnectionReqRepository;
import com.MentorConnect.MentorConnect.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConnectionReqService {
    private final ConnectionReqRepository connectionRequestRepository;
    private final UserRepository userRepository;

//    public ConnectionRequest sendRequest(Long menteeId, Long mentorId) {
//        User mentee = userRepository.findById(menteeId)
//                .orElseThrow(() -> new RuntimeException("Mentee not found"));
//        User mentor = userRepository.findById(mentorId)
//                .orElseThrow(() -> new RuntimeException("Mentor not found"));
//
//        ConnectionRequest request = ConnectionRequest.builder()
//                .mentee(mentee)
//                .mentor(mentor)
//                .status(ConnectionRequest.Status.PENDING)
//                .build();
//
//        return connectionRequestRepository.save(request);
//    }

    public ConnectionRequest sendConnectionRequest(String menteeEmail, String mentorEmail) {
        User mentee = userRepository.findByEmail(menteeEmail)
                .orElseThrow(() -> new RuntimeException("Mentee not found"));
        User mentor = userRepository.findByEmail(mentorEmail)
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        ConnectionRequest connection = ConnectionRequest.builder()
            .mentee(mentee)
            .mentor(mentor)
            .status(ConnectionRequest.Status.PENDING)
            .build();

        return connectionRequestRepository.save(connection);
    }

    public void respondToRequest(Long requestId, String action) {
        ConnectionRequest request = connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        ConnectionRequest.Status status;
        if ("ACCEPTED".equalsIgnoreCase(action)) {
            status = ConnectionRequest.Status.ACCEPTED;
        } else if ("REJECTED".equalsIgnoreCase(action)) {
            status = ConnectionRequest.Status.REJECTED;
        } else {
            throw new IllegalArgumentException("Invalid action");
        }

        request.setStatus(status);
        connectionRequestRepository.save(request);
    }

    public List<User> getAcceptedMentees(String mentorEmail) {
        List<ConnectionRequest> acceptedConnections = connectionRequestRepository
         .findByMentorEmailAndStatus(mentorEmail, ConnectionRequest.Status.ACCEPTED);

     return acceptedConnections.stream()
                .map(ConnectionRequest::getMentee)
                .collect(Collectors.toList());
    }

    public List<User> getAcceptedMentors(String menteeEmail){
        return connectionRequestRepository.findAcceptedMentorsByMenteeEmail(menteeEmail);
    }


}
