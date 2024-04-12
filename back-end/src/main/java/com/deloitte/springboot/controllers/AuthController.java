package com.deloitte.springboot.controllers;

import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.springboot.entities.Role;
import com.deloitte.springboot.entities.User;
import com.deloitte.springboot.exceptions.UserConflictException;
import com.deloitte.springboot.exceptions.UserUnauthorizedException;
import com.deloitte.springboot.repositories.RoleRepository;
import com.deloitte.springboot.repositories.UserRepository;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    
    @PostMapping("/auth/login")
    public User login(@RequestBody User user) {
        if (! userRepository.existsByEmail(user.getEmail())) {
            throw new NoSuchElementException();
        }
        User queriedUser = userRepository.findByEmail(user.getEmail()).get();

        if (passwordEncoder.matches(user.getPassword(), queriedUser.getPassword())) {
            return queriedUser;
        }

        throw new UserUnauthorizedException("Wrong Credentials");
    }

    /**
     * Checks if the user already exists searching by email.
     * If it exists then it will inform it.
     * 
     * @param user The user to create.
     * @return The created User.
     */
    @PostMapping("/auth/register")
    public User register(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserConflictException("User Already Exists");
        }
        Role userRole = roleRepository.findByName("USER").get();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(userRole));
        return userRepository.save(user);
    }
}
