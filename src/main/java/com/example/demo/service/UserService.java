package com.example.demo.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;


@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public String deleteAccount(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            return "User not found";
        }

        userRepository.deleteById(userId);
        return "Account deleted successfully";
    }

}
