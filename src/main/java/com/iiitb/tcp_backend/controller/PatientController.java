package com.iiitb.tcp_backend.controller;
import com.iiitb.tcp_backend.clientmodels.DoctorAvailable;
import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.service.DoctorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class PatientController {

    @Autowired
    DoctorDetailsService doctor_service;
    @GetMapping("/PatientDepartment")
    public ResponseEntity<List<String>> change_status() {
        try {
            List<String> ans = doctor_service.getDepartments();
            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patient")
    public ResponseEntity<String> check_department(@RequestParam String department_name) {
        try {
            System.out.println(department_name);
            String ans = "";
            int count = doctor_service.searchDepartment(department_name);
            if(count > 0)
            {
                ans = "Success";
            }
            else
            {
                ans = "Failure";
            }
            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
