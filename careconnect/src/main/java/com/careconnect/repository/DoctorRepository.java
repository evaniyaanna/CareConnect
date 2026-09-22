package com.careconnect.repository;

import com.careconnect.dto.DoctorReport;
import com.careconnect.entity.Doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository
        extends JpaRepository<Doctor, Long> {

    List<Doctor> findByIsActiveTrue();

    Optional<Doctor> findByIdAndIsActiveTrue(
            Long id
    );

    // Check if a doctor email already exists
    boolean existsByEmail(String email);

    // Check if another doctor already uses this email
    boolean existsByEmailAndIdNot(
            String email,
            Long id
    );

    // Search doctors by name or specialization
    List<Doctor>
            findByNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(
                    String name,
                    String specialization
            );

    @Query("""
        SELECT new com.careconnect.dto.DoctorReport(
            d,
            COUNT(a)
        )
        FROM Doctor d
        LEFT JOIN Appointment a
            ON a.doctor.id = d.id
        GROUP BY d
        ORDER BY COUNT(a) DESC
    """)
    List<DoctorReport> getPopularDoctors();
}