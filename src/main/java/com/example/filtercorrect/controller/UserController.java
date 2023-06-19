package com.example.filtercorrect.controller;

import com.example.filtercorrect.DTO.LoginRequestDTO;
import com.example.filtercorrect.DTO.SignupRequestDTO;
import com.example.filtercorrect.entity.User;
import com.example.filtercorrect.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
   private  UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDTO signupRequestDTO)
    { return userService.signup(signupRequestDTO);

    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session)

    {  return  userService.login(loginRequestDTO,session);

    }
    @GetMapping("privatepage")
    public String privatepage()
    {
        return "privatepage";
    }
}
