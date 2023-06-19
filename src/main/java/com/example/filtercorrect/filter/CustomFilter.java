package com.example.filtercorrect.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@Component
public class CustomFilter  extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       HttpSession session= request.getSession();
        SecurityContext context= (SecurityContext) session.getAttribute("setcontextholder");
        if (context!=null)
        {
            Authentication authentication=context.getAuthentication();
            if(authentication!=null && authentication.isAuthenticated())
            {
                log.info("success");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } filterChain.doFilter(request,response);
    }
}
