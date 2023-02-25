package com.iiitb.tcp_backend.controller;

import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.service.DoctorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class DoctorController {
    @Autowired
    DoctorDetailsService doctor_service;
    @PutMapping("/DoctorAvailability")
    public ResponseEntity<String> change_status(@RequestBody DoctorDetails doctor) {
        try {

            String ans = "";
            DoctorDetails doc = doctor_service.findById(doctor.getDoctorId());
            doc.setDoctorAvailability(doctor.isDoctorAvailability());
            ans = "Success";
            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
