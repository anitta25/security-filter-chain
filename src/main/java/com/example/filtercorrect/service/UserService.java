package com.example.filtercorrect.service;

import com.example.filtercorrect.DTO.LoginRequestDTO;
import com.example.filtercorrect.DTO.SignupRequestDTO;
import com.example.filtercorrect.entity.User;
import com.example.filtercorrect.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired

    AuthenticationManager authenticationManager;
    @Autowired
    UserRepo repo;
    public ResponseEntity<String> signup(SignupRequestDTO signupRequestDTO) {
        String username=signupRequestDTO.getUsername();
        if(repo.findByUsername(username).isPresent())
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username laready exists");
        }
        else {
            User user = new User();
            user.setUsername(signupRequestDTO.getUsername());
            user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
            System.out.println("sample encoding" +passwordEncoder.encode("sample"));
            repo.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("account created");
        }
    }

    public ResponseEntity<String> login(LoginRequestDTO loginRequestDTO, HttpSession session) {
        try { log.info("inside login");
            String pass=loginRequestDTO.getPassword();
            System.out.println(pass);
            System.out.println("sample encoding"+ passwordEncoder.encode("sample"));

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), pass, null);

            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("setcontextholder",SecurityContextHolder.getContext());
            return ResponseEntity.status(HttpStatus.OK).body("login success");
        }
        catch (AuthenticationException exception)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("LOGIN FAIL");
        }
    }
}
