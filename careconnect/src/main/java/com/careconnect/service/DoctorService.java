package com.careconnect.service;

import com.careconnect.dto.DoctorReport;
import com.careconnect.entity.Doctor;
import com.careconnect.repository.DoctorRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(
            DoctorRepository doctorRepository) {

        this.doctorRepository =
                doctorRepository;
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {

        return doctorRepository.findAll();
    }

    // Get doctor by ID
    public Optional<Doctor> getDoctorById(
            Long id) {

        return doctorRepository.findById(id);
    }

    // Save doctor
    public Doctor saveDoctor(
            Doctor doctor) {

        return doctorRepository.save(doctor);
    }

    // Delete doctor
    public void deleteDoctor(
            Long id) {

        doctorRepository.deleteById(id);
    }

    // Search doctors by name or specialization
    public List<Doctor> searchDoctors(
            String search) {

        return doctorRepository
                .findByNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(
                        search,
                        search
                );
    }

    // Get total number of doctors
    public long getDoctorCount() {

        return doctorRepository.count();
    }

    // Get popular doctors for reports
    public List<DoctorReport> getPopularDoctors() {

        return doctorRepository
                .getPopularDoctors();
    }

    // Get active doctors
    public List<Doctor> getActiveDoctors() {

        return doctorRepository
                .findByIsActiveTrue();
    }

    // Get active doctor by ID
    public Optional<Doctor> getActiveDoctorById(
            Long id) {

        return doctorRepository
                .findByIdAndIsActiveTrue(id);
    }
}