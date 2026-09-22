package com.careconnect.service;

import com.careconnect.dto.AppointmentRequest;
import com.careconnect.dto.AppointmentResponse;
import com.careconnect.entity.Appointment;
import com.careconnect.entity.AppointmentStatus;
import com.careconnect.entity.Doctor;
import com.careconnect.entity.User;
import com.careconnect.repository.AppointmentRepository;
import com.careconnect.repository.DoctorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository
            appointmentRepository;

    private final DoctorRepository
            doctorRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository) {

        this.appointmentRepository =
                appointmentRepository;

        this.doctorRepository =
                doctorRepository;
    }

    // =========================================================
    // MANAGEMENT PORTAL METHODS
    // =========================================================

    // Get appointments for a specific date
    public List<Appointment> getAppointmentsByDate(
            LocalDate date) {

        return appointmentRepository
                .findByAppointmentDate(date);
    }

    // Get a single appointment by ID
    public Appointment getAppointmentById(
            Long id) {

        return appointmentRepository
                .findById(id)
                .orElseThrow();
    }

    // Get all appointments for a specific patient
    public List<Appointment> getAppointmentsByPatient(
            Long patientId) {

        return appointmentRepository
                .findByPatient_Id(patientId);
    }

    // Get total number of appointments
    public long getAppointmentCount() {

        return appointmentRepository.count();
    }

    // Get number of appointments for today
    public long getTodaysAppointmentCount() {

        return appointmentRepository
                .countByAppointmentDate(
                        LocalDate.now()
                );
    }

    // =========================================================
    // PATIENT REST API METHODS
    // =========================================================

    // Get appointments belonging to the logged-in patient
    public List<AppointmentResponse>
            getPatientAppointments(User patient) {

        return appointmentRepository
                .findByPatientOrderByAppointmentDateAscAppointmentTimeAsc(
                        patient
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Create a new appointment
    @Transactional
    public AppointmentResponse createAppointment(
            AppointmentRequest request,
            User patient) {

        Doctor doctor =
                doctorRepository
                        .findByIdAndIsActiveTrue(
                                request.getDoctorId()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Doctor not found."
                                )
                        );

        boolean alreadyBooked =
                appointmentRepository
                        .existsByDoctorAndAppointmentDateAndAppointmentTimeAndStatus(
                                doctor,
                                request.getAppointmentDate(),
                                request.getAppointmentTime(),
                                AppointmentStatus.BOOKED
                        );

        if (alreadyBooked) {

            throw new IllegalArgumentException(
                    "The selected time slot is not available."
            );
        }

        Appointment appointment =
                new Appointment();

        appointment.setPatient(patient);

        appointment.setDoctor(doctor);

        appointment.setAppointmentDate(
                request.getAppointmentDate()
        );

        appointment.setAppointmentTime(
                request.getAppointmentTime()
        );

        appointment.setStatus(
                AppointmentStatus.BOOKED
        );

        Appointment saved =
                appointmentRepository.save(
                        appointment
                );

        return toResponse(saved);
    }

    // Cancel an appointment belonging to the logged-in patient
    @Transactional
    public void cancelAppointment(
            Long id,
            User patient) {

        Appointment appointment =
                appointmentRepository
                        .findByIdAndPatient(
                                id,
                                patient
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Appointment not found."
                                )
                        );

        if (!"Booked".equalsIgnoreCase(
                appointment.getStatus().name())) {

            throw new IllegalArgumentException(
                    "This appointment cannot be cancelled."
            );
        }

        appointment.setStatus(
                AppointmentStatus.CANCELLED
        );

        appointmentRepository.save(
                appointment
        );
    }

    // =========================================================
    // CONVERT APPOINTMENT ENTITY TO REST API RESPONSE
    // =========================================================

    private AppointmentResponse toResponse(
            Appointment appointment) {

        return new AppointmentResponse(
                appointment.getId(),
                appointment.getDoctor().getId(),
                appointment.getDoctor().getName(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getStatus() != null
                        ? appointment.getStatus().name()
                        : null
        );
    }
}