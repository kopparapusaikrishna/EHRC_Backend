package com.iiitb.tcp_backend.service;

import com.iiitb.tcp_backend.model.PatientDetails;
import com.iiitb.tcp_backend.repository.PatientDetailsRepository;
import com.twilio.rest.verify.v2.service.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import java.util.List;
import java.util.Random;

@Service
public class PatientLoginService {
    private TwilioRestClient twilioRestClient;
    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    public String generateOTP(String phoneNumber) {
        // Generate a random 6-digit OTP
        System.out.println(phoneNumber+"Service");
        String otp = String.format("%06d", new Random().nextInt(999999));

        System.out.println("Start");
        // Send the OTP to the user's phone via SMS
        final String ACCOUNT_SID = "ACbcec39040fe22ddea4512e79e24b6c90";
        final String AUTH_TOKEN = "82d9e8cdb894aecbfdfdae2e3536e44e";
        final String FROM_NUMBER = "+16317693117";

        System.out.println("before");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification = Verification.creator("VA7b7cae4a6cafb25ff2d150b9412ea804","+91"+phoneNumber,"sms").create();
        System.out.println(verification.getStatus());
        System.out.println("after");

        return verification.getStatus();
    }

    public String verifyOTP(String phoneNumber, String enteredOTP) {
        // Verify the OTP entered by the user
        final String ACCOUNT_SID = "ACbcec39040fe22ddea4512e79e24b6c90";
        final String AUTH_TOKEN = "82d9e8cdb894aecbfdfdae2e3536e44e";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        VerificationCheck verification;
        try {
            verification = VerificationCheck.creator("VA7b7cae4a6cafb25ff2d150b9412ea804").setTo("+91" + phoneNumber).setCode(enteredOTP).create();
        }catch (Exception e){
            return "decline";
        }
        return verification.getStatus();
    }
    public List<PatientDetails> userprofiles(String phone_number){
        return patientDetailsRepository.getuserprofiles(phone_number);
    }
    public PatientDetails userprofilepass(int patient_id){
        System.out.println("reached");
        PatientDetails u= patientDetailsRepository.getuserprofilepass(patient_id);
        System.out.println(u);
        return u;
    }


}


