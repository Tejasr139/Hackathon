package com.MentorConnect.MentorConnect.Controller;

import com.MentorConnect.MentorConnect.Dto.CodeSubmissionDto;
import com.MentorConnect.MentorConnect.Entity.CodeSubmission;
import com.MentorConnect.MentorConnect.Repository.CodeSubmissionRepository;
import com.MentorConnect.MentorConnect.Service.CodeSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CodeSubmissionController {
@Autowired
    private final CodeSubmissionService codeSubmissionService;
@Autowired
    private CodeSubmissionRepository codeSubmissionRepository;
//
//    @PostMapping("/submit")
//     public ResponseEntity<String> submitCode(@RequestBody CodeSubmission code) {
//            codeSubmissionRepository.save(code);
//            return ResponseEntity.ok("Code saved successfully");
//    }
@PostMapping("/submit")
public ResponseEntity<String> submitCode(@RequestBody CodeSubmission code) {
    try {
        codeSubmissionService.saveCodeSubmission(code);
        return ResponseEntity.ok("Code saved successfully");
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error saving code: " + e.getMessage());
    }
}

    @GetMapping("/user/{email}")
    public List<CodeSubmission> getCodesByUser(@PathVariable String email) {
        return codeSubmissionRepository.findByMenteeEmail(email);
     }

//     @PostMapping("/submit")
//     public ResponseEntity<?> submitCode(@RequestBody CodeSubmissionDto dto) {
//         System.out.println("Received code :"+dto.getCode());
//         CodeSubmission saved = codeSubmissionService.saveSubmission(dto);
//         return ResponseEntity.ok(saved);
//     }
//
//     @GetMapping("/submissions")
//     public ResponseEntity<?> getByEmail(@RequestParam String email) {
//         return ResponseEntity.ok(codeSubmissionService.getSubmissions(email));
//     }
}
