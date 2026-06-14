package com.app.ecom.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.app.ecom.dto.UserLoginRequest;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.exception.NotFoundException;
import com.app.ecom.model.User;
import com.app.ecom.service.UserService;


@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private UserService userService;
    
    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> allUser() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("User not found "+id));
                // .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        UserResponse createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserRequest user) {
        return userService.updateUser(id, user);
    }

    // @PostMapping("/login")
    // public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
    //     // Implement your login logic here, such as validating the user's credentials
    //     // against the database and generating a JWT token if the credentials are valid.
    //     // For simplicity, this example just returns a success message.

    //     return ResponseEntity.ok("Login successful for user: " + userLoginRequest.getEmail());
    // }

    @GetMapping("/login")
    public ResponseEntity<String> loginUser() {
        return ResponseEntity.ok("Login successful for user");
    }
}
