package com.careconnect.controller;

import com.careconnect.entity.Doctor;
import com.careconnect.service.DoctorService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(
            DoctorService doctorService) {

        this.doctorService = doctorService;
    }

    // =========================================================
    // LIST DOCTORS / SEARCH DOCTORS
    // =========================================================

    @GetMapping
    public String listDoctors(
            @RequestParam(required = false)
            String search,
            Model model) {

        List<Doctor> doctors;

        if (search == null || search.isBlank()) {

            doctors = doctorService.getAllDoctors();

        } else {

            doctors = doctorService.searchDoctors(search);
        }

        model.addAttribute(
                "doctors",
                doctors
        );

        model.addAttribute(
                "search",
                search
        );

        model.addAttribute(
                "activePage",
                "doctors"
        );

        return "portal/doctors/list";
    }

    // =========================================================
    // SHOW ADD DOCTOR FORM
    // =========================================================

    @GetMapping("/add")
    public String addDoctorForm(
            Model model) {

        model.addAttribute(
                "doctor",
                new Doctor()
        );

        return "portal/doctors/form";
    }

    // =========================================================
    // SAVE NEW DOCTOR
    // =========================================================

    @PostMapping("/add")
    public String addDoctor(
            @Valid @ModelAttribute("doctor")
            Doctor doctor,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {

            return "portal/doctors/form";
        }

        doctorService.saveDoctor(doctor);

        redirectAttributes.addFlashAttribute(
                "message",
                "Doctor added successfully."
        );

        return "redirect:/doctors";
    }

    // =========================================================
    // SHOW EDIT DOCTOR FORM
    // =========================================================

    @GetMapping("/edit/{id}")
    public String editDoctorForm(
            @PathVariable Long id,
            Model model) {

        Doctor doctor = doctorService
                .getDoctorById(id)
                .orElseThrow();

        model.addAttribute(
                "doctor",
                doctor
        );

        return "portal/doctors/form";
    }

    // =========================================================
    // UPDATE DOCTOR
    // =========================================================

    @PostMapping("/edit/{id}")
    public String editDoctor(
            @PathVariable Long id,
            @Valid @ModelAttribute("doctor")
            Doctor doctor,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {

            return "portal/doctors/form";
        }

        Doctor existingDoctor = doctorService
                .getDoctorById(id)
                .orElseThrow();

        existingDoctor.setName(
                doctor.getName()
        );

        existingDoctor.setSpecialization(
                doctor.getSpecialization()
        );

        existingDoctor.setQualification(
                doctor.getQualification()
        );

        existingDoctor.setExperience(
                doctor.getExperience()
        );

        existingDoctor.setPhoneNumber(
                doctor.getPhoneNumber()
        );

        existingDoctor.setEmail(
                doctor.getEmail()
        );

        existingDoctor.setConsultationFee(
                doctor.getConsultationFee()
        );

        existingDoctor.setIsActive(
                doctor.getIsActive()
        );

        doctorService.saveDoctor(
                existingDoctor
        );

        redirectAttributes.addFlashAttribute(
                "message",
                "Doctor updated successfully."
        );

        return "redirect:/doctors";
    }

    // =========================================================
    // DELETE DOCTOR
    // =========================================================

    @PostMapping("/delete/{id}")
    public String deleteDoctor(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        doctorService.deleteDoctor(id);

        redirectAttributes.addFlashAttribute(
                "message",
                "Doctor deleted successfully."
        );

        return "redirect:/doctors";
    }
}