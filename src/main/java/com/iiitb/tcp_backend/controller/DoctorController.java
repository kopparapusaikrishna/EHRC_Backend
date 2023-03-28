package com.iiitb.tcp_backend.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iiitb.tcp_backend.clientmodels.Doctor;
import com.iiitb.tcp_backend.clientmodels.DoctorAvailable;
import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.service.DoctorDetailsService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<String> change_status(@RequestBody DoctorAvailable doctor) {
        try {
            String ans = "";
            DoctorDetails doc = doctor_service.findById(doctor.getDoctorId());
            doc.setDoctorAvailability(doctor.isStatus());
            doctor_service.save(doc);
            ans = "Success";
            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/GetDoctorList")
    public ResponseEntity<List<Doctor>> sendDoctorList(@RequestBody Doctor doctor){
    	
    	try {
    		System.out.println("HI");
    		List<Doctor> doctors = new ArrayList<Doctor>(); 
    		List<DoctorDetails> doctorDetails = doctor_service.getDoctors();
    		
    		System.out.println(doctorDetails.toString());
    		
    		for (int i=0 ; i<doctorDetails.size() ; i++)
    		{
    			DoctorDetails singleDoctorDetails = new DoctorDetails();
    	
    			String name = singleDoctorDetails.getDoctorName();
    		    Date dob = singleDoctorDetails.getDoctorDob();
    		    String gender = singleDoctorDetails.getDoctorGender();
    		    String email_id = "nobody@gmail.com";   
    		    Date doctor_start_date = singleDoctorDetails.getDoctorStartDate();
    		    String qualification = singleDoctorDetails.getDoctorQualification();
    			String department_name = singleDoctorDetails.getDepartmentName();
    		    String phone_number = singleDoctorDetails.getDoctorPhoneNumber();
    		    String clinic_address = singleDoctorDetails.getDoctorClinicAddress();
    		    String password = "qwertyjhgsaADGNN";
    			
    			Doctor doctor1 = new Doctor(name,dob,gender,email_id,doctor_start_date,
    					qualification,department_name, phone_number, clinic_address, password);
    			
    			doctors.add(doctor1);
    			
    		}
    		
    		for(int i=0 ; i<doctors.size() ; i++)
    		{
    			System.out.println(doctors.get(i).toString());
    		}
    		
    		return new ResponseEntity<>(doctors, HttpStatus.OK);
    		
    	}
    	
    	catch (Exception e)
    	{
    		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
		 	
    	
    }
}
