package com.careconnect.service;

import com.careconnect.entity.Role;
import com.careconnect.entity.User;
import com.careconnect.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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


    // =========================================================
    // GET ALL PATIENTS
    // =========================================================

    public List<User> getAllPatients() {

        return userRepository.findByRole(
            Role.PATIENT
        );
    }


    // =========================================================
    // SEARCH PATIENTS
    // =========================================================

    public List<User> searchPatients(
            String search) {

        List<User> patients =
                getAllPatients();


        if (search == null || search.isBlank()) {

            return patients;
        }


        String searchText =
                search.trim().toLowerCase();


        return patients.stream()
                .filter(user -> {

                    String firstName =
                            user.getFirstName() == null
                                    ? ""
                                    : user.getFirstName()
                                            .toLowerCase();

                    String lastName =
                            user.getLastName() == null
                                    ? ""
                                    : user.getLastName()
                                            .toLowerCase();

                    String email =
                            user.getEmail() == null
                                    ? ""
                                    : user.getEmail()
                                            .toLowerCase();

                    String phone =
                            user.getPhoneNumber() == null
                                    ? ""
                                    : String.valueOf(
                                            user.getPhoneNumber()
                                      );


                    String fullName =
                            firstName
                                    + " "
                                    + lastName;


                    return firstName.contains(searchText)
                            || lastName.contains(searchText)
                            || fullName.contains(searchText)
                            || email.contains(searchText)
                            || phone.contains(searchText);
                })
                .collect(Collectors.toList());
    }


    // =========================================================
    // GET USER BY ID
    // =========================================================

    public User getUserById(Long id) {

        return userRepository
                .findById(id)
                .orElseThrow();
    }


    // =========================================================
    // GET TOTAL NUMBER OF PATIENTS
    // =========================================================

    public long getPatientCount() {

        return userRepository.countByRole(
            Role.PATIENT
        );
    }


    // =========================================================
    // CHANGE PASSWORD
    // =========================================================

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