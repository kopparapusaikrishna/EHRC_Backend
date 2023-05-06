package com.iiitb.tcp_backend;

import com.iiitb.tcp_backend.JwtUtil.JwtAuthenticationEntryPoint;
import com.iiitb.tcp_backend.JwtUtil.JwtFilter;
import com.iiitb.tcp_backend.JwtUtil.JwtUserDetailsService;
import com.iiitb.tcp_backend.JwtUtil.TokenManager;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;
import com.iiitb.tcp_backend.AuthenticationService;
import com.iiitb.tcp_backend.service.PatientLoginService;
@CrossOrigin(origins = "*")
@RestController


public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    private final PatientLoginService patientLoginService;
    private final TokenManager tokenManager;
    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }
    public AuthenticationController(AuthenticationService authenticationService,PatientLoginService patientLoginService)
    {
        this.authenticationService = authenticationService;
        tokenManager = new TokenManager();
        this.patientLoginService=patientLoginService;
    }

    @CrossOrigin
    @GetMapping("Patient/sendOTP")
    public int send_otp(@RequestParam("phone_number") String mobile_number)
    {
        System.out.println("Hello?");
        String result=this.patientLoginService.generateOTP(mobile_number);
        if (result.equals("pending"))
            return 1;
        return 0;
    }

    /*record verify_otp_body(String mobile_number,String otp){}
    @CrossOrigin
    @PostMapping("/verify_otp")
    public String verify_otp(@RequestBody verify_otp_body verify_otp_rec)
    {
        return authenticationService.verify_otp(verify_otp_rec.otp,verify_otp_rec.mobile_number);
    }*/
    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtFilter authenticationJwtTokenFilter() {
        return new JwtFilter(tokenManager, userDetailsService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/Patient/verifyOTP").permitAll()
                .antMatchers("/Doctor","/Patient/sendOTP","/Admin").permitAll()
                .anyRequest().authenticated();

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}