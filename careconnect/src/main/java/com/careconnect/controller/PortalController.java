package com.careconnect.controller;

import com.careconnect.service.AppointmentService;
import com.careconnect.service.DoctorService;
import com.careconnect.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class PortalController {

    private final DoctorService doctorService;

    private final AppointmentService appointmentService;

    private final UserService userService;


    public PortalController(
            DoctorService doctorService,
            AppointmentService appointmentService,
            UserService userService) {

        this.doctorService = doctorService;

        this.appointmentService =
                appointmentService;

        this.userService =
                userService;
    }


    @GetMapping("/")
    public String home(Model model) {

        // Total number of doctors

        model.addAttribute(
            "doctor_count",
            doctorService.getDoctorCount()
        );


        // Total number of patients

        model.addAttribute(
            "patient_count",
            userService.getPatientCount()
        );


        // Today's appointments count

        model.addAttribute(
            "todays_appointment_count",
            appointmentService
                .getTodaysAppointmentCount()
        );


        // Total number of bookings

        model.addAttribute(
            "appointment_count",
            appointmentService
                .getAppointmentCount()
        );


        // Today's appointment records

        model.addAttribute(
            "todaysAppointments",
            appointmentService.getAppointmentsByDate(
                LocalDate.now()
            )
        );


        // Active sidebar item

        model.addAttribute(
            "activePage",
            "dashboard"
        );


        return "portal/home";
    }


    @GetMapping("/login")
    public String login() {

        return "portal/login";
    }
}