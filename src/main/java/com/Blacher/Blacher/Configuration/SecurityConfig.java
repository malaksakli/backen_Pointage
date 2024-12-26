package com.Blacher.Blacher.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.Blacher.Blacher.controller.CustomUserDetailsService;

@Configuration

public class SecurityConfig {

   private final CustomUserDetailsService userDetailsService;

   public SecurityConfig(CustomUserDetailsService userDetailsService) {
       this.userDetailsService = userDetailsService;
   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
           .csrf().disable() // Disable CSRF protection
           .authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll()
               .anyRequest().authenticated()
           .and()
            .httpBasic();

       return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
   @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
               .userDetailsService(userDetailsService)
               .passwordEncoder(passwordEncoder())
               .and()
                .build();
    }
}

