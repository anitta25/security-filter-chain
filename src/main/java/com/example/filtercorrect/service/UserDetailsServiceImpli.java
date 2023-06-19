package com.example.filtercorrect.service;

import com.example.filtercorrect.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
@Slf4j
public class UserDetailsServiceImpli  implements UserDetailsService {
    @Autowired
    UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside loaduserbyname");
        com.example.filtercorrect.entity.User user= repo.findByUsername(username).get();
        return new User(user.getUsername(),user.getPassword(),new ArrayList<>());
    }
}
