package com.careconnect.repository;

import com.careconnect.entity.Appointment;
import com.careconnect.entity.AppointmentStatus;
import com.careconnect.entity.Doctor;
import com.careconnect.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    // =========================================================
    // MANAGEMENT PORTAL METHODS
    // =========================================================

    List<Appointment> findByAppointmentDate(
            LocalDate appointmentDate
    );

    List<Appointment> findByPatient_Id(
            Long patientId
    );

    long countByAppointmentDate(
            LocalDate date
    );

    // =========================================================
    // PATIENT REST API METHODS
    // =========================================================

    List<Appointment>
            findByPatientOrderByAppointmentDateAscAppointmentTimeAsc(
                    User patient
            );

    Optional<Appointment> findByIdAndPatient(
            Long id,
            User patient
            );

    boolean existsByDoctorAndAppointmentDateAndAppointmentTimeAndStatus(
            Doctor doctor,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            AppointmentStatus status
    );

    // =========================================================
    // CHECK WHETHER DOCTOR HAS APPOINTMENTS
    // =========================================================

    boolean existsByDoctorId(
            Long doctorId
    );
}