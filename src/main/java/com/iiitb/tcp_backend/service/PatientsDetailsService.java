package com.iiitb.tcp_backend.service;

import com.iiitb.tcp_backend.model.PatientDetails;
import com.iiitb.tcp_backend.repository.PatientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class PatientsDetailsService {

        @Autowired
        private PatientDetailsRepository repo;
        public PatientDetails findById(int id)
        {
            return repo.findByPatientId(id);
        }

        public PatientDetails save(PatientDetails movement) {
            //System.out.println(movement.getDoctorId());
            repo.save(movement);
            //System.out.println(movement.getDoctorId());
            return movement;
        }

        public List<PatientDetails> getProfiles(String phone_number)
        {
            return repo.findByPhoneNumber(phone_number);
        }
}
