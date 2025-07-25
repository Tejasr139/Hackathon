package com.MentorConnect.MentorConnect.Service;

import com.MentorConnect.MentorConnect.Dto.CodeSubmissionDto;
import com.MentorConnect.MentorConnect.Entity.CodeSubmission;
import com.MentorConnect.MentorConnect.Repository.CodeSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeSubmissionService {
 @Autowired
 private final CodeSubmissionRepository codeSubmissionRepository;

 public CodeSubmission saveSubmission(CodeSubmissionDto dto) {
  // 🔍 Log the incoming DTO
  System.out.println("📥 Received DTO:");
  System.out.println("Email: " + dto.getMenteeEmail());
  System.out.println("Task: " + dto.getTaskDescription());
  System.out.println("Language: " + dto.getLanguage());
  System.out.println("Code: " + dto.getCode());
  System.out.println("Output: " + dto.getOutput());

  CodeSubmission submission = new CodeSubmission();
  submission.setMenteeEmail(dto.getMenteeEmail());
  submission.setTaskDescription(dto.getTaskDescription());
  submission.setLanguage(dto.getLanguage());
  submission.setCode(dto.getCode());
  submission.setOutput(dto.getOutput());
  submission.setSubmittedAt(LocalDateTime.now());

  // 🔍 Log the mapped entity before saving
  System.out.println("🗃 Saving entity: " + submission);

  return codeSubmissionRepository.save(submission);
 }

 public List<CodeSubmission> getSubmissions(String menteeEmail) {
  return codeSubmissionRepository.findByMenteeEmail(menteeEmail);
 }
 public CodeSubmission saveCodeSubmission(CodeSubmission submission) {
  submission.setSubmittedAt(LocalDateTime.now()); // set current timestamp
  return codeSubmissionRepository.save(submission);
 }
}
