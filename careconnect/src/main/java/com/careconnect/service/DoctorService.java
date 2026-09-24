package com.careconnect.service;

import com.careconnect.dto.DoctorReport;
import com.careconnect.entity.Doctor;
import com.careconnect.repository.AppointmentRepository;
import com.careconnect.repository.DoctorRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    private final AppointmentRepository appointmentRepository;


    public DoctorService(
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository) {

        this.doctorRepository =
                doctorRepository;

        this.appointmentRepository =
                appointmentRepository;
    }


    // =========================================================
    // GET ALL DOCTORS
    // =========================================================

    public List<Doctor> getAllDoctors() {

        return doctorRepository.findAll();
    }


    // =========================================================
    // GET DOCTOR BY ID
    // =========================================================

    public Optional<Doctor> getDoctorById(
            Long id) {

        return doctorRepository.findById(id);
    }


    // =========================================================
    // SAVE DOCTOR
    // =========================================================

    public Doctor saveDoctor(
            Doctor doctor) {

        return doctorRepository.save(doctor);
    }


    // =========================================================
    // CHECK EMAIL FOR NEW DOCTOR
    // =========================================================

    public boolean doctorEmailExists(
            String email) {

        return doctorRepository.existsByEmail(
                email
        );
    }


    // =========================================================
    // CHECK EMAIL FOR EDIT DOCTOR
    // =========================================================

    public boolean doctorEmailExistsForAnotherDoctor(
            String email,
            Long id) {

        return doctorRepository
                .existsByEmailAndIdNot(
                        email,
                        id
                );
    }


    // =========================================================
    // DELETE DOCTOR
    // =========================================================

    public void deleteDoctor(
            Long id) {

        boolean hasAppointments =
                appointmentRepository
                        .existsByDoctorId(id);


        if (hasAppointments) {

            throw new IllegalStateException(
                    "Doctor has appointments and cannot be deleted."
            );
        }


        doctorRepository.deleteById(id);
    }


    // =========================================================
    // SEARCH DOCTORS
    // =========================================================

    public List<Doctor> searchDoctors(
            String search) {

        return doctorRepository
                .findByNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(
                        search,
                        search
                );
    }


    // =========================================================
    // GET TOTAL NUMBER OF DOCTORS
    // =========================================================

    public long getDoctorCount() {

        return doctorRepository.count();
    }


    // =========================================================
    // GET POPULAR DOCTORS FOR REPORTS
    // =========================================================

    public List<DoctorReport> getPopularDoctors() {

        return doctorRepository
                .getPopularDoctors();
    }


    // =========================================================
    // GET ACTIVE DOCTORS
    // =========================================================

    public List<Doctor> getActiveDoctors() {

        return doctorRepository
                .findByIsActiveTrue();
    }


    // =========================================================
    // GET ACTIVE DOCTOR BY ID
    // =========================================================

    public Optional<Doctor> getActiveDoctorById(
            Long id) {

        return doctorRepository
                .findByIdAndIsActiveTrue(id);
    }
}