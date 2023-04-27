package com.iiitb.tcp_backend.JwtUtil;
import com.iiitb.tcp_backend.service.PatientLoginService;
import com.twilio.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.iiitb.tcp_backend.JwtUtil.models.JwtRequestModel;
import com.iiitb.tcp_backend.JwtUtil.models.JwtResponseModel;
import com.iiitb.tcp_backend.AuthenticationService;
import org.springframework.security.core.Authentication;

import java.util.Objects;

@RestController
@RequestMapping(value = "/Patient")
public class JwtController {
    private  final PatientLoginService patientLoginService;
    private final AuthenticationService authenticationService;
    public JwtController(AuthenticationService authenticationService,PatientLoginService patientLoginService)
    {
        this.authenticationService = authenticationService;
        this.patientLoginService=patientLoginService;
    }
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenManager tokenManager;


    @CrossOrigin
    @GetMapping("/verifyOTP")
    public ResponseEntity createToken(@RequestParam String phone_number,@RequestParam String otp) {

        String auth = patientLoginService.verifyOTP(phone_number,otp);
        System.out.println("reached: "+ auth);

        if (Objects.equals(auth, "approved")) {
            System.out.println("entered");
            final UserDetails userDetails = userDetailsService.loadUserByUsername(phone_number);
            final String jwtToken = tokenManager.generateJwtToken(userDetails);
        System.out.println("hellso");
        //System.out.println(jwtToken);
        //System.out.println());
        return ResponseEntity.ok(new JwtResponseModel(jwtToken));

        }
        else
        {
            return ResponseEntity.ok(new JwtResponseModel("not"));
        }
        //return auth;
    }
}
