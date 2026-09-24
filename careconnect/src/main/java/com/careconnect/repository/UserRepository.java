package com.careconnect.repository;

import com.careconnect.entity.Role;
import com.careconnect.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

    // Count users by role
    long countByRole(Role role);


    // =========================================================
    // SEARCH PATIENTS
    // =========================================================

    @Query("""
        SELECT u
        FROM User u
        WHERE u.role = :role
        AND (
            LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))
            OR u.phoneNumber LIKE CONCAT('%', :search, '%')
            OR LOWER(
                CONCAT(
                    u.firstName,
                    ' ',
                    u.lastName
                )
            ) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    List<User> searchPatients(
            @Param("role") Role role,
            @Param("search") String search
    );
}