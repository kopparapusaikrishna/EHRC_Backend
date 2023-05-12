package com.iiitb.tcp_backend.controller;

import java.util.*;

import java.sql.Date;

//import com.iiitb.tcp_backend.service.PatientRecordService;
import com.iiitb.tcp_backend.clientmodels.DoctorMeetData;
import com.iiitb.tcp_backend.model.Appointments;
import com.iiitb.tcp_backend.repository.PatientDetailsRepository;
import com.iiitb.tcp_backend.service.AppointmentsService;
import com.iiitb.tcp_backend.service.DoctorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.iiitb.tcp_backend.model.PatientDetails;
import com.iiitb.tcp_backend.service.PatientsDetailsService;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

@CrossOrigin(origins = "*")
@RestController
public class MeetingController {

//    @Autowired
//    PatientRecordService patient_record_service;
   @Autowired
    PatientDetailsRepository patientDetailsRepository;
    @Autowired
    PatientsDetailsService patientsDetailsService;

    @Autowired
    AppointmentsService appointmentsService;

    @Autowired
    DoctorDetailsService doctorDetailsService;

    HashMap<String, Queue<Queue_item>> global_list;  // Dep_name: global_queue for that dep

    HashMap<Integer, Queue<Queue_item>> doctor_list;  // Doctor_id: local_queue for that doctor

    HashMap<Integer, String> patient_channel;

    HashMap<Integer, Integer> present_doctor_patient; // doctor_id: present_patient_id

//    HashMap<Integer, Integer> doctor_ratios; // doctor_id: ratio

    HashMap<Integer, Integer> from_local_or_global; // doctor_id: (local-0 or global-1)


    @PostConstruct
    public void initialize() {
        global_list = new HashMap<>();
        doctor_list = new HashMap<>();
        patient_channel = new HashMap<>();
        present_doctor_patient = new HashMap<>();
        from_local_or_global = new HashMap<>();
    }
    @DeleteMapping("/deletepatients")
    public ResponseEntity<Void> deletepatients(@RequestParam int doctor_id){

                Queue<Queue_item> qi= doctor_list.get(doctor_id);
                while(!qi.isEmpty()){
                    Queue_item q=qi.poll();
                    int patient_id=q.getPatient_id();
                    PatientDetails patientDetails=patientDetailsRepository.findByPatientId(patient_id);
                    doctorDetailsService.sendsms(patientDetails.getPatientPhoneNumber());

                }



        if (doctor_list.get(doctor_id)!=null){
            doctor_list.put(doctor_id,new ArrayDeque<Queue_item>());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/patientChannelGlobal")
    public ResponseEntity<String> addPatientToGlobal(@RequestParam String patient_id, @RequestParam String dept_name) {
        try {
            System.out.println("Inside Add Patient to a Global Queue");
            int p_id = Integer.parseInt(patient_id);
            String channel_name = Integer.toString(p_id);
            patient_channel.put(p_id, channel_name);
            if (global_list.containsKey(dept_name) == false) {
                global_list.put(dept_name, new ArrayDeque<Queue_item>());
            }
            Queue<Queue_item> dept_queue = global_list.get(dept_name);
            Queue_item queueItem = new Queue_item(p_id, -1);
            dept_queue.add(queueItem);
            global_list.put(dept_name, dept_queue);

            System.out.println(global_list.toString());

            return new ResponseEntity<>(channel_name, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patientChannelLocal/Same")
    public ResponseEntity<String> addPatientToLocalSame(@RequestParam int appointment_id) {
        try {
            System.out.println("Inside Add Patient to Local Queue Same");

            Appointments appointment = appointmentsService.findByAppointmentId(appointment_id);

             int doctor_id = appointment.getDoctorId();
             int patient_id = appointment.getPatientId();

            String channel_name = Integer.toString(patient_id);

            if (doctor_list.containsKey(doctor_id) == false) {
                doctor_list.put(doctor_id, new ArrayDeque<Queue_item>());
            }

            patient_channel.put(patient_id, channel_name);
            Queue<Queue_item> doctor_queue = doctor_list.get(doctor_id);
            Queue_item queueItem = new Queue_item(patient_id, appointment_id);
            doctor_queue.add(queueItem);
            doctor_list.put(doctor_id, doctor_queue);

            System.out.println(doctor_list.toString());

            return new ResponseEntity<>(channel_name, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patientChannelLocal/Different")
    public ResponseEntity<String> addPatientToLocalDifferent(@RequestParam int appointment_id, @RequestParam String dept_name) {
        try {
            System.out.println("Inside Add Patient to Local Queue Different");

            Appointments appointment = appointmentsService.findByAppointmentId(appointment_id);

            int patient_id = appointment.getPatientId();

            String channel_name = Integer.toString(patient_id);

            patient_channel.put(patient_id, channel_name);

            if (global_list.containsKey(dept_name) == false) {
                global_list.put(dept_name, new ArrayDeque<Queue_item>());
            }

            Queue<Queue_item> dept_queue = global_list.get(dept_name);
            Queue_item queueItem = new Queue_item(patient_id, appointment_id);
            dept_queue.add(queueItem);
            global_list.put(dept_name, dept_queue);

            System.out.println(global_list.toString());

            return new ResponseEntity<>(channel_name, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    local-0 or global-1
    @GetMapping("/doctorChannel")
    public ResponseEntity<DoctorMeetData> getDoctorChannel(@RequestParam int doctor_id, @RequestParam String dept_name, @RequestParam Boolean availability) {
        try {

            String channel_name;
            System.out.println("Inside Get ChannelId For Doctor");
            if (global_list.containsKey(dept_name) == false) {
                global_list.put(dept_name, new ArrayDeque<>());
            }
            Queue<Queue_item> dept_queue = global_list.get(dept_name);
            if (doctor_list.containsKey(doctor_id) == false) {
                System.out.println("Hello local");
                doctor_list.put(doctor_id, new ArrayDeque<Queue_item>());
            }
            Queue<Queue_item> doctor_queue = doctor_list.get(doctor_id);

            if (from_local_or_global.containsKey(doctor_id) == false) {
                from_local_or_global.put(doctor_id, 0);
            }

            System.out.println("Hello");

            Queue_item queueItem;
            System.out.println(dept_queue.size());
            System.out.println("Hello-1112");
            System.out.println(doctor_queue.size());
            System.out.println("Hello-111");


            if(availability == false) {
                System.out.println("In Only local");
                queueItem = doctor_queue.poll();
                int patient_id = queueItem.getPatient_id();
                doctor_list.put(doctor_id, doctor_queue);
                present_doctor_patient.put(doctor_id, patient_id);
                channel_name = patient_channel.get(patient_id);

                DoctorMeetData doctorMeetData = new DoctorMeetData(channel_name, queueItem.getPrev_appointment_id());

                return new ResponseEntity<>(doctorMeetData, HttpStatus.OK);
            }

            else {
                if (dept_queue.size() != 0 && doctor_queue.size() == 0) {
                    System.out.println("In global");
                    queueItem = dept_queue.poll();
                    int patient_id = queueItem.getPatient_id();
                    //                    System.out.println("1");
                    global_list.put(dept_name, dept_queue);
                    present_doctor_patient.put(doctor_id, patient_id);
                    //                    System.out.println("2");
                    channel_name = patient_channel.get(patient_id);
                    //                    System.out.println("3");
                    from_local_or_global.put(doctor_id, 0);
                    System.out.println("Out Global");
                } else if (dept_queue.size() == 0 && doctor_queue.size() != 0) {
                    System.out.println("In local");
                    queueItem = doctor_queue.poll();
                    int patient_id = queueItem.getPatient_id();
                    doctor_list.put(doctor_id, doctor_queue);
                    present_doctor_patient.put(doctor_id, patient_id);
                    channel_name = patient_channel.get(patient_id);
                    from_local_or_global.put(doctor_id, 1);
                } else {
                    if (from_local_or_global.get(doctor_id) == 1) {
                        queueItem = dept_queue.poll();
                        int patient_id = queueItem.getPatient_id();
                        global_list.put(dept_name, dept_queue);
                        present_doctor_patient.put(doctor_id, patient_id);
                        channel_name = patient_channel.get(patient_id);
                        from_local_or_global.put(doctor_id, 0);
                    } else {
                        queueItem = doctor_queue.poll();
                        int patient_id = queueItem.getPatient_id();
                        doctor_list.put(doctor_id, doctor_queue);
                        present_doctor_patient.put(doctor_id, patient_id);
                        channel_name = patient_channel.get(patient_id);
                        from_local_or_global.put(doctor_id, 1);
                    }
                }

                DoctorMeetData doctorMeetData = new DoctorMeetData(channel_name, queueItem.getPrev_appointment_id());

                return new ResponseEntity<>(doctorMeetData, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    private String scheduling(Queue<Integer> dept_queue, Queue<Integer> doctor_queue, int doctor_id, String dept_name) {
//
//        int total = sum(dept_queue) +sum(doctor_queue);
//
//        int doctors_in_department = dept_queue.size() + doctor_queue.size();
//
//        int no_of_active_doctors = doctorDetailsService.searchDepartment(dept_name);
//
//        int k = total/no_of_active_doctors;
//
//        return "Global Local";
//    }
//
//    private int sum(Queue<Integer> q) {
//        int sum = 0;
//        while (!q.isEmpty()) {
//            int n = q.poll();
//            sum += n;
//        }
//        return sum;
//    }


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

   @Transactional
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

    @GetMapping("/doctorStats")
    public ResponseEntity<List<Integer>> doctorStats(@RequestParam int doctor_id, @RequestParam String dept_name) {
        try {
            List<Integer> ans = new ArrayList<Integer>();
            int globval = -1;
            int locval = -1;
            System.out.println("Inside Doctor Stats");
            if (!global_list.containsKey(dept_name)) {
                global_list.put(dept_name, new ArrayDeque<Queue_item>());
            }
            Queue<Queue_item> dept_queue = global_list.get(dept_name);
            globval = dept_queue.size();

            if(!doctor_list.containsKey(doctor_id)){
                doctor_list.put(doctor_id,new ArrayDeque<Queue_item>());
            }
            locval = doctor_list.get(doctor_id).size();
            ans.add(globval);
            ans.add(locval);

            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/remindPatient")
    public ResponseEntity<String> sendRemainderMessage(@RequestParam int appointment_id) {
        try {
            System.out.println("Inside Remind Patient Queue Different");

            Appointments appointment = appointmentsService.findByAppointmentId(appointment_id);
            int patient_id = appointment.getPatientId();
            PatientDetails patientDetails = patientDetailsRepository.findByPatientId(patient_id);
            doctorDetailsService.sendReminderMessage(patientDetails.getPatientPhoneNumber());

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

}