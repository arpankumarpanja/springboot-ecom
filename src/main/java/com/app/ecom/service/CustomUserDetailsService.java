package com.app.ecom.service;

import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.app.ecom.config.CustomUserDetails;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        // return org.springframework.security.core.userdetails.User.builder()
        //         .username(user.getEmail())
        //         .password(user.getPassword())
        //         .roles(user.getRole().toString())
        //         .build();
        return new CustomUserDetails(user);
    }

}
