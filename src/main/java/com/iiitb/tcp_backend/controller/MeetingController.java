package com.iiitb.tcp_backend.controller;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

import java.sql.Date;

//import com.iiitb.tcp_backend.service.PatientRecordService;
import com.iiitb.tcp_backend.model.Appointments;
import com.iiitb.tcp_backend.service.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.iiitb.tcp_backend.model.PatientDetails;
import com.iiitb.tcp_backend.service.PatientsDetailsService;

import javax.annotation.PostConstruct;

@CrossOrigin(origins = "*")
@RestController
public class MeetingController {

//    @Autowired
//    PatientRecordService patient_record_service;

    @Autowired
    PatientsDetailsService patientsDetailsService;

    @Autowired
    AppointmentsService appointmentsService;

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

    @GetMapping("/patientChannelGlobal")
    public ResponseEntity<String> addPatientToGlobal(@RequestParam String patient_id, @RequestParam String dept_name) {
        try {
            System.out.println("Inside Add Patient to a Department");
            int p_id = Integer.parseInt(patient_id);
            String channel_name = Integer.toString(p_id);
            patient_channel.put(p_id, channel_name);
            if (global_list.containsKey(dept_name) == false) {
                global_list.put(dept_name, new ArrayDeque<Integer>());
            }
            Queue<Integer> dept_queue = global_list.get(dept_name);
            dept_queue.add(p_id);
            global_list.put(dept_name, dept_queue);

            System.out.println(global_list.toString());

            return new ResponseEntity<>(channel_name, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/AddToLocal")
    public ResponseEntity<String> addPatientToLocal(@RequestParam int patient_id, @RequestParam int doctor_id) {
        try {
            System.out.println("Inside Add Patient to a Doctor");
            String ans = "";
            if (doctor_list.containsKey(doctor_id) == false) {
                doctor_list.put(doctor_id, new ArrayDeque<Integer>());
            }
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


    @GetMapping("/doctorChannel")
    public ResponseEntity<String> getDoctorChannel(@RequestParam String doctor_id, @RequestParam String dept_name) {
        try {
            System.out.println("Inside Get ChannelId For Doctor");
            int d_id = Integer.parseInt(doctor_id);
            Queue<Integer> dept_queue = global_list.get(dept_name);
            int patient_id = dept_queue.poll();
            global_list.put(dept_name, dept_queue);

            present_doctor_patient.put(d_id, patient_id);

            String channel_name = patient_channel.get(patient_id);

            return new ResponseEntity<>(channel_name, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patientDetails")
    public ResponseEntity<PatientDetails> getPatientCurrentlyBeingVisited(@RequestParam String doctor_id) {
        try {

            int d_id = Integer.parseInt(doctor_id);
            System.out.println("Inside Get Patient Details Meeting");

            int patient_id = present_doctor_patient.get(d_id);

            PatientDetails patientDetails = patientsDetailsService.findById(patient_id);

            return new ResponseEntity<>(patientDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @PostMapping("/addAppointmentDetails")
    public ResponseEntity<String> postAppointmentDetails(@RequestBody HashMap<String, String> map) {
        try {
            System.out.println("Inside add appointment details");

            int weight = Integer.parseInt(map.get("weight"));

            int doctor_id = Integer.parseInt(map.get("doctor_id"));

            int patient_id = present_doctor_patient.get(doctor_id);

            String bp = map.get("bp");

            int temp = Integer.parseInt(map.get("temperature"));

            String prescripion = map.get("prescription");

            boolean follow_up;

            if (map.get("follow_up").equals("Yes")) {
                follow_up = true;
            } else {
                follow_up = false;
            }

            Date followup_date = Date.valueOf(map.get("followup_date"));

            Date date = new java.sql.Date(System.currentTimeMillis());

            Appointments appointments = new Appointments(doctor_id, patient_id, date, follow_up, followup_date, prescripion, weight, temp, bp);

            appointments = appointmentsService.save(appointments);

            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    public void updateLists(int doctor_id, String dept_name) {
//        System.out.println("Inside update lists");
//        doctor_list.put(doctor_id, new ArrayDeque<>());
//
//        present_doctor_patient.put(doctor_id, null);
//        if(global_list.containsKey(dept_name) == false) {
//            global_list.put(dept_name, new ArrayDeque<>());
//        }
//
//        System.out.println(doctor_list.toString());
//
//    }


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