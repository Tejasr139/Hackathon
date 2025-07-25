package com.MentorConnect.MentorConnect.Repository;

import com.MentorConnect.MentorConnect.Entity.ConnectionRequest;
import com.MentorConnect.MentorConnect.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConnectionReqRepository extends JpaRepository<ConnectionRequest, Long> {
    List<ConnectionRequest> findByMenteeEmail(String menteeEmail);
    List<ConnectionRequest> findByMentorEmailAndStatus(String email, ConnectionRequest.Status status);

    @Query("SELECT c.mentor FROM ConnectionRequest c WHERE c.mentee.email = :menteeEmail AND c.status = 'ACCEPTED'")
    List<User> findAcceptedMentorsByMenteeEmail(String menteeEmail);
}
