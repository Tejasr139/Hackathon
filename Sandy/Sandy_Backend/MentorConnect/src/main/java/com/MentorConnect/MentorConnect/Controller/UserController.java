package com.MentorConnect.MentorConnect.Controller;

import com.MentorConnect.MentorConnect.Dto.LoginRequest;
import com.MentorConnect.MentorConnect.Dto.LoginResponse;
import com.MentorConnect.MentorConnect.Dto.RegisterRequest;
import com.MentorConnect.MentorConnect.Dto.RegisterResponse;
import com.MentorConnect.MentorConnect.Service.UserService;
import com.MentorConnect.MentorConnect.Entity.User;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request){
        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return userService.login(request);
    }

//    @GetMapping("/profile")
//    public String getProfile(@AuthenticationPrincipal Jwt jwt) {
//        return "Logged in as: " + jwt.getSubject();
//    }


    //sandy


    // READ - Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    // READ - Get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // UPDATE - Update user by ID
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    // DELETE - Delete user by ID
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }


}
