package com.example.filtercorrect.Security;

import com.example.filtercorrect.filter.CustomFilter;
import com.example.filtercorrect.service.UserDetailsServiceImpli;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
     @Autowired
    CustomFilter customfilter;
    @Autowired
    UserDetailsServiceImpli userDetailsService;





    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
      @Bean

    public PasswordEncoder passwordEncoder() throws  Exception
    {

        return  new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
        //authenticationManagerBuilder.inMemoryAuthentication()                                    .withUser("user").password("password").roles("ADMIN");
        return authenticationManagerBuilder.build();
    }







    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception
    {

        httpSecurity                              .csrf(AbstractHttpConfigurer::disable);
        httpSecurity                              .authorizeHttpRequests((req)->req
                                                   .requestMatchers("/signup","/login").permitAll()
                .                                    anyRequest().authenticated());
        httpSecurity                              .addFilterBefore(customfilter, BasicAuthenticationFilter.class);
        return httpSecurity.build();

    }

}
