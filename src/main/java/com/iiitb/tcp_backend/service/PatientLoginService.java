package com.iiitb.tcp_backend.service;

import com.twilio.rest.verify.v2.service.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
public class PatientLoginService {
    private TwilioRestClient twilioRestClient;

    public String generateOTP(String phoneNumber) {
        // Generate a random 6-digit OTP
        System.out.println(phoneNumber+"Service");
        String otp = String.format("%06d", new Random().nextInt(999999));

        System.out.println("Start");
        // Send the OTP to the user's phone via SMS
        final String ACCOUNT_SID = "ACe0ba10ac9a51ee9db2b213ff7cbfcdc9";
        final String AUTH_TOKEN = "ac417c7c2f7c89e5d84c32e7a56840e5";
        final String FROM_NUMBER = "+15855523181";

        System.out.println("before");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification = Verification.creator("VA35a5422136e65c0eec5b16776fb43025","+91"+phoneNumber,"sms").create();
        System.out.println(verification.getStatus());
        System.out.println("after");

        return verification.getStatus();
    }

    public String verifyOTP(String phoneNumber, String enteredOTP) {
        // Verify the OTP entered by the user
        final String ACCOUNT_SID = "ACe0ba10ac9a51ee9db2b213ff7cbfcdc9";
        final String AUTH_TOKEN = "ac417c7c2f7c89e5d84c32e7a56840e5";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        VerificationCheck verification;
        try {
            verification = VerificationCheck.creator("VA35a5422136e65c0eec5b16776fb43025").setTo("+91" + phoneNumber).setCode(enteredOTP).create();
        }catch (Exception e){
            return "decline";
        }
        return verification.getStatus();
    }


}


