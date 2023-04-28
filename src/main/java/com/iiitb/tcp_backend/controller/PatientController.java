package com.iiitb.tcp_backend.controller;
import com.iiitb.tcp_backend.clientmodels.PatientPrevAppointments;
import com.iiitb.tcp_backend.clientmodels.Profile;
import com.iiitb.tcp_backend.model.Appointments;
import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.model.PatientDetails;
import com.iiitb.tcp_backend.service.AppointmentsService;
import com.iiitb.tcp_backend.service.DoctorDetailsService;
import com.iiitb.tcp_backend.service.PatientLoginService;

import com.iiitb.tcp_backend.service.PatientsDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class PatientController {

    @Autowired
    DoctorDetailsService doctor_service;
    @Autowired
    PatientLoginService patient_service;

    @Autowired
    PatientsDetailsService patient_details_service;

    @Autowired
    AppointmentsService appointmentsService;

    @Autowired
    DoctorDetailsService doctorDetailsService;

    @GetMapping("/PatientDepartment")
    public ResponseEntity<List<String>> change_status() {
        System.out.println("printing....");
        try {
            List<String> ans = doctor_service.getDepartments();
            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/profiles/{phone_number}")
    public ResponseEntity<List<PatientDetails>> get_profiles(@PathVariable("phone_number") String phone_number){
        try {
            System.out.println("inside");
            List<PatientDetails> lst;
            System.out.println("fdfdfdd");
            lst = patient_service.userprofiles(phone_number);

            List<PatientDetails> sending_lst = new ArrayList<>();

            for (int i = 0; i < lst.size(); i++) {
                PatientDetails patient = lst.get(i);

                if (patient.isActive() == true)
                    sending_lst.add(patient);
            }

            return new ResponseEntity<>(sending_lst, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @GetMapping("/GetProfiles/{phoneNumber}")
//    public ResponseEntity<List<Profile>> sendProfiles(@PathVariable("phoneNumber") String phoneNumber){
//        try{
//            System.out.println("inside");
//            List<PatientDetails> profiles = patient_details_service.getProfiles(phoneNumber);
//
//            List<Profile> profileList = new ArrayList<>();
//
//            for(int i=0 ; i<profiles.size(); i++)
//            {
//                PatientDetails patient = profiles.get(i);
//                Profile profile = new Profile(patient.getPatientId(),patient.getPatientName(),patient.getPatientDob(), patient.getGender(), patient.getPatientPhoneNumber(), patient.getPatientLocation(), patient.isActive(),"1234");
//
//                if(patient.isActive() == true)
//                    profileList.add(profile);
//            }
//
//            return new ResponseEntity<>(profileList,HttpStatus.OK);
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("profiles/pin")
    public ResponseEntity<PatientDetails> getuserprofilepass(@RequestParam int patient_id){
        System.out.println("dvdvdv");
        PatientDetails ud= patient_service.userprofilepass(patient_id);
        System.out.println(ud);
        if (ud!=null){
            System.out.println(ud);
            return new ResponseEntity<>(ud,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
  /*
    @CrossOrigin("http://localhost:4200")
    @GetMapping("/Patient/sendOTP")
    public int sendOTP(@RequestParam("phoneNumber") String phoneNumber) {
        System.out.println(phoneNumber+ "controller");
        String result=patientservice.generateOTP(phoneNumber);
        if (result.equals("pending"))
            return 1;
        return 0;
    }
    /*@CrossOrigin("http://localhost:4200/")
    @GetMapping("Patient/verifyOTP")
    public int verifyOTP(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("otp") String otp) {
        String result= patientservice.verifyOTP(phoneNumber, otp);
        if (result.equals("approved"))
            return 1;
        return 0;

    }*/

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

    @PostMapping("/PostProfileDetails")
    public ResponseEntity<String> addProfile(@RequestBody Profile profile){
        System.out.println("inside post");
        try{
            PatientDetails patientDetailsObj = new PatientDetails(profile.getName(),profile.getDob(), profile.getGender(), profile.getPhone_number(), profile.getLocation(), true, profile.getPin());
            System.out.println("torture");
            patientDetailsObj = patient_details_service.save(patientDetailsObj);
            return new ResponseEntity<>("Success",HttpStatus.OK);


        }catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/DeleteProfile/{patientId}")
    public ResponseEntity<String> deleteProfiles(@PathVariable("patientId") int patientId) {
        try
        {
            System.out.println("inside delete");
            PatientDetails patient = patient_details_service.findById(patientId);
            patient.setActive(false);

            patient_details_service.save(patient);

            return new ResponseEntity<>("Success", HttpStatus.OK);

        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/previousAppointmentsList")
    public ResponseEntity<List<PatientPrevAppointments>> getPrevAppointmentsLst(@RequestParam int patient_id) {
        try
        {
            System.out.println("inside previousAppointmentsList");
            List<Appointments> appointmentsLst =  appointmentsService.findByPatientId(patient_id);

            List<PatientPrevAppointments> patientPrevAppointments = new ArrayList<>();

            for(int i=0; i<appointmentsLst.size(); i++) {
                Appointments presentAppointment = appointmentsLst.get(i);
                DoctorDetails doctorDetails = doctorDetailsService.findById(presentAppointment.getDoctorId());
                PatientPrevAppointments patientPrevAppointment = new PatientPrevAppointments(presentAppointment.getAppointmentDate(), doctorDetails.getDepartmentName(), doctorDetails.getDoctorName(), presentAppointment.getAppointmentId());
                patientPrevAppointments.add(patientPrevAppointment);
            }

            return new ResponseEntity<>(patientPrevAppointments, HttpStatus.OK);

        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
