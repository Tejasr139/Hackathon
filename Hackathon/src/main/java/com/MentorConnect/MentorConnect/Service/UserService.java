package com.MentorConnect.MentorConnect.Service;

import com.MentorConnect.MentorConnect.Dto.LoginRequest;
import com.MentorConnect.MentorConnect.Dto.LoginResponse;
import com.MentorConnect.MentorConnect.Dto.RegisterRequest;
import com.MentorConnect.MentorConnect.Dto.RegisterResponse;
import com.MentorConnect.MentorConnect.Entity.Role;
import com.MentorConnect.MentorConnect.Entity.User;
import com.MentorConnect.MentorConnect.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;   // allows us to interact with DB(for USer Entity)

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public RegisterResponse register(RegisterRequest request){      //Accepts a RegisterRequest DTO containing user details and returns RegisterResponse DTO with a message and email.
        if(userRepository.findByEmail(request.getEmail()).isPresent()){   //Checks if the user with sam eemail exists in DB
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()     //Creates new USer entity
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);       //save the user
        return new RegisterResponse("User Registered Successfully",user.getEmail());
    }


    //For Login Purpose

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(token, user.getEmail(), user.getRole().name());
    }


    public List<User> getUsersByRole(String roleName){
        Role role = Role.valueOf(roleName.toUpperCase());
        return userRepository.findByRole(role);
    }

    public List<User> getAllMentees(){
        return userRepository.findByRole(Role.MENTEE);
    }

}
