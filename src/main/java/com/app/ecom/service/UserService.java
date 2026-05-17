package com.app.ecom.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.ecom.dto.AddressDto;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse createUser(User user) {
        // userRepository.save(user);
        User createdUser = userRepository.save(user);
        return mapToUserResponse(createdUser);
    }

    public Optional<UserResponse> getUserById(Long id) {
        // return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userRepository.findById(id).map(this::mapToUserResponse);
    }

    public UserResponse updateUser(Long id, UserRequest updatedUserRequest) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        updateUserFormRequest(existingUser, updatedUserRequest);
        // return userRepository.save(existingUser).map(this::mapToUserResponse).orElseThrow(() -> new RuntimeException("User not found after update"));
        return mapToUserResponse(userRepository.save(existingUser));
    }

    public UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId().toString());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());


        if (user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setZipCode(user.getAddress().getZipCode());
            userResponse.setAddress(addressDto);
        }

        return userResponse;
    }   

    public void updateUserFormRequest(User existingUser, UserRequest updatedUser) {
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        // existingUser.setRole(updatedUser.getRole());

        Address existingAddress = existingUser.getAddress();
        existingAddress.setStreet(updatedUser.getAddress().getStreet());
        existingAddress.setCity(updatedUser.getAddress().getCity());
        existingAddress.setState(updatedUser.getAddress().getState());
        existingAddress.setCountry(updatedUser.getAddress().getCountry());
        existingAddress.setZipCode(updatedUser.getAddress().getZipCode());
    }
    
}
