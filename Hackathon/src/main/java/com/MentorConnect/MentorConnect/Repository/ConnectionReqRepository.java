package com.MentorConnect.MentorConnect.Repository;

import com.MentorConnect.MentorConnect.Entity.ConnectionRequest;
import com.MentorConnect.MentorConnect.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConnectionReqRepository extends JpaRepository<ConnectionRequest, Long> {
    List<ConnectionRequest> findByMenteeEmail(String menteeEmail);
    @Query("SELECT c FROM ConnectionRequest c WHERE c.mentor.email = :mentorEmail AND c.status = :status")
    List<ConnectionRequest> findByMentorEmailAndStatus(@Param("mentorEmail") String mentorEmail,
                                                       @Param("status") ConnectionRequest.Status status);


    @Query("SELECT cr.mentor FROM ConnectionRequest cr WHERE cr.mentee.email = :menteeEmail AND cr.status = 'ACCEPTED'")
    List<User> findAcceptedMentorsByMenteeEmail(@Param("menteeEmail") String menteeEmail);


    List<ConnectionRequest> findByMentor_EmailAndStatus(String mentorEmail, ConnectionRequest.Status status);


//    List<ConnectionRequest> findByMentorEmailAndStatus(String email, ConnectionRequest.Status status);
//    List<ConnectionRequest> findByMentor_EmailAndStatus(String email, ConnectionRequest.Status status);


//    @Query("SELECT c.mentor FROM ConnectionRequest c WHERE c.mentee.email = :menteeEmail AND c.status = 'ACCEPTED'")
//    List<User> findAcceptedMentorsByMenteeEmail(String menteeEmail);



//    @Query("SELECT cr.mentor FROM ConnectionRequest cr WHERE cr.mentee.email = :email AND cr.status = 'ACCEPTED'")
//    List<User> findAcceptedMentorsByMenteeEmail(@org.springframework.data.repository.query.Param("email") String email);


}
