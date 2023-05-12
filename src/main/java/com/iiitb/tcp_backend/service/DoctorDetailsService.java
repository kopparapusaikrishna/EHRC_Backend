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
        //repo.save(movement);
        System.out.println(movement.toString());
        repo.adddoctor(movement.getDoctorName(), movement.getDoctorDob(), movement.getDoctorGender(), movement.getDepartmentName(), movement.getDoctorQualification(), movement.getDoctorClinicAddress(), movement.getDoctorPhoneNumber(),true, movement.getDoctorStartDate());
        //System.out.println(movement.getDoctorId());--
        int id=repo.lastdoctordetails();
        return repo.findByDoctorId(id);
    }
    public ResponseEntity<Void> sendReminderMessage(String phone_number){
        System.out.println("dgdgdgdd");
        final String ACCOUNT_SID = "AC57e93cf1adf49737c5d591a9aef97f98";
        final String AUTH_TOKEN = "566dadfee205b72be92c91777e076dff";
        final String FROM_NUMBER = "+12706322743";
        System.out.println(phone_number);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        //System.out.println(md.getMovie_name());
        final String to="+91"+phone_number;

            System.out.println("dgdgdgdd3");
            Message.creator(new PhoneNumber(to), new PhoneNumber(FROM_NUMBER), "You have a Follow Up appointment. Please visit your nearby center or Plan a meeting with doctor").create();
            System.out.println("dgdgdgdd4");
            return ResponseEntity.ok().build();

    }
    public ResponseEntity<Void> sendsms(String phone_number){
        System.out.println("dgdgdgdd");
        final String ACCOUNT_SID = "AC57e93cf1adf49737c5d591a9aef97f98";
        final String AUTH_TOKEN = "566dadfee205b72be92c91777e076dff";
        final String FROM_NUMBER = "+12706322743";
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
    
    public int searchDepartment(String department_name)
    {
        return repo.countDepartment(department_name);
    }

}
