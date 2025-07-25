package com.MentorConnect.MentorConnect.Repository;

import com.MentorConnect.MentorConnect.Entity.CodeSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeSubmissionRepository extends JpaRepository<CodeSubmission, Long> {
    List<CodeSubmission> findByMenteeEmail(String menteeEmail);
}
