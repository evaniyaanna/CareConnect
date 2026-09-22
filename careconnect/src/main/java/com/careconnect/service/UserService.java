package com.careconnect.service;

import com.careconnect.entity.Role;
import com.careconnect.entity.User;
import com.careconnect.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository =
                userRepository;

        this.passwordEncoder =
                passwordEncoder;
    }


    // Get all patients

    public List<User> getAllPatients() {

        return userRepository.findByRole(
            Role.PATIENT
        );
    }


    // Get user by ID

    public User getUserById(Long id) {

        return userRepository
                .findById(id)
                .orElseThrow();
    }


    // Get total number of patients

    public long getPatientCount() {

        return userRepository.countByRole(
            Role.PATIENT
        );
    }


    // Change password

    public void changePassword(
            User user,
            String currentPassword,
            String newPassword) {

        // Check current password

        if (!passwordEncoder.matches(
                currentPassword,
                user.getPassword())) {

            throw new RuntimeException(
                    "Current password is incorrect."
            );
        }


        // Encode the new password

        user.setPassword(
            passwordEncoder.encode(
                newPassword
            )
        );


        // Save the updated user

        userRepository.save(user);
    }
}