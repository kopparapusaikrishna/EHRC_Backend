package com.iiitb.tcp_backend.controller;

import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import com.iiitb.tcp_backend.clientmodels.Doctor;
import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.model.PatientRecord;
//import com.iiitb.tcp_backend.service.PatientRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
public class MeetingController {

//    @Autowired
//    PatientRecordService patient_record_service;

    HashMap<String, Queue<Integer>> global_list;  // Dep_name: global_queue for that dep

    HashMap<Integer, Queue<Integer>> doctor_list;  // Doctor_id: local_queue for that doctor

    HashMap<Integer, String> patient_channel;

    HashMap<Integer, Integer> present_doctor_patient; // doctor_id: present_patient_id

    @PostConstruct
    public void initialize() {
        global_list = new HashMap<>();
        doctor_list = new HashMap<>();
        patient_channel = new HashMap<>();
        present_doctor_patient = new HashMap<>();
    }

    @PostMapping ("/patientChannelGlobal")
    public ResponseEntity<String> addPatientToGlobal(@RequestParam String dept_name, @RequestParam int patient_id) {
        try {
            System.out.println("Inside Add Patient to a Department");
            String channel_name = Integer.toString(patient_id);
            patient_channel.put(patient_id, channel_name);

            Queue<Integer> dept_queue = global_list.get(dept_name);
            dept_queue.add(patient_id);
            global_list.put(dept_name, dept_queue);

            System.out.println(global_list.toString());

            return new ResponseEntity<>(channel_name, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping ("/AddToLocal")
    public ResponseEntity<String> addPatientToLocal(@RequestParam int patient_id, @RequestParam int doctor_id) {
        try {
            System.out.println("Inside Add Patient to a Doctor");
            String ans = "";

            Queue<Integer> doctor_queue = doctor_list.get(doctor_id);
            doctor_queue.add(patient_id);
            doctor_list.put(doctor_id, doctor_queue);

            System.out.println(doctor_list.toString());
            ans = "Success";
            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping ("/doctorChannel")
    public ResponseEntity<String> getDoctorChannel(@PathVariable int doctor_id, @PathVariable String dept_name) {
        try {
            System.out.println("Inside Get ChannelId For Doctor");

            Queue<Integer> dept_queue = global_list.get(dept_name);
            int patient_id = dept_queue.poll();
            global_list.put(dept_name, dept_queue);

            present_doctor_patient.put(doctor_id, patient_id);

            String channel_name = patient_channel.get(patient_id);

            return new ResponseEntity<>(channel_name, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PostMapping ("/patientRecords")
//    public ResponseEntity<Integer> postPatientDetails(@RequestBody HashMap<String, String> patient_records) {
//        try {
//            System.out.println("Inside Post Patient Records");
//
//            int weight = Integer.parseInt(patient_records.get("weight"));
//            String bp = patient_records.get("bp");
//            int temp = Integer.parseInt(patient_records.get("temperature"));
//            int patient_id = Integer.parseInt((patient_records.get("patient_id")));
//
//            PatientRecord patientRecord = new PatientRecord(patient_id, weight, temp, bp);
//
//            patientRecord = patient_record_service.save(patientRecord);
//
//            int ans = patientRecord.getPatientId();
//
//            return new ResponseEntity<>(ans, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



//    @PostMapping ("/doctorPrescription")
//    public ResponseEntity<String> postPatientDetails(ArrayList<HashMap<String, String>>) {
//        try {
//            System.out.println("Inside Get ChannelId For Doctor");
//
//            Queue<Integer> dept_queue = global_list.get(dept_name);
//            int patient_id = dept_queue.poll();
//            global_list.put(dept_name, dept_queue);
//
//            present_doctor_patient.put(doctor_id, patient_id);
//
//            String channel_name = patient_channel.get(patient_id);
//
//            return new ResponseEntity<>(channel_name, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



    public void updateLists(int doctor_id, String dept_name) {
        System.out.println("Inside update lists");
        doctor_list.put(doctor_id, new ArrayDeque<>());

        present_doctor_patient.put(doctor_id, null);
        if(global_list.containsKey(dept_name) == false) {
            global_list.put(dept_name, new ArrayDeque<>());
        }
    }


//    @GetMapping("/Testing")
//    public ResponseEntity<String> StartMeeting(HttpServletRequest request) {
//        try {
//            String ans = "sad";
//            String ipAddress = request.getHeader("X-Forwarded-For"); // For obtaining IP address through reverse proxy server
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("Proxy-Client-IP");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("WL-Proxy-Client-IP");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("HTTP_X_FORWARDED");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("HTTP_CLIENT_IP");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("HTTP_FORWARDED_FOR");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("HTTP_FORWARDED");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("REMOTE_ADDR");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getRemoteAddr();
//            }
//            // use the ipAddress variable as needed
//            System.out.println(ipAddress);
//            return new ResponseEntity<>(ans, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @RequestMapping(value = "/Test", method = RequestMethod.GET)
//    public String myEndpoint(HttpServletRequest request) {
//        String ipAddress = request.getHeader("X-FORWARDED-FOR");
//        if (ipAddress == null) {
//            ipAddress = request.getRemoteAddr();
//        }
//        System.out.println(ipAddress);
//        return "Client IP Address: " + ipAddress;
//    }

}