package com.iiitb.tcp_backend.service;
import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.repository.DoctorDetailsRepository;
import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DoctorDetailsService {
    @Autowired
    private DoctorDetailsRepository repo;
    public DoctorDetails findById(int id)
    {
        return repo.findByDoctorId(id);
    }

    public DoctorDetails save(DoctorDetails movement) {
        //System.out.println(movement.getDoctorId());
        repo.save(movement);
        //System.out.println(movement.getDoctorId());--
        return movement;
    }
    public ResponseEntity<Void> sendsms(String phone_number){
        System.out.println("dgdgdgdd");
        final String ACCOUNT_SID = "ACbcec39040fe22ddea4512e79e24b6c90";
        final String AUTH_TOKEN = "2445d6bbd53302717d6b41df7d83e147";
        final String FROM_NUMBER = "+16317693117";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        //System.out.println(md.getMovie_name());
        final String to="+91"+phone_number;
        try {
            System.out.println("dgdgdgdd1");
            Message.creator(new PhoneNumber(to), new PhoneNumber(FROM_NUMBER), "Doctor has ended the consultations.Sorry!").create();
            System.out.println("dgdgdgdd2");
            return ResponseEntity.ok().build();
        }catch (TwilioException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public List<String> getDepartments()
    {
        return repo.getDepartments();
    }

    public List<DoctorDetails> getDoctors()
    {
    	return repo.getDoctors();
    }
    
    public int searchDepartment(String department)
    {
        return repo.countDepartment(department);
    }

}
