package com.iiitb.tcp_backend.controller;



import com.iiitb.tcp_backend.clientmodels.AdminDetails;
import com.iiitb.tcp_backend.clientmodels.Doctor;
import com.iiitb.tcp_backend.model.Admin;
import com.iiitb.tcp_backend.model.AdminLogin;
import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.model.DoctorLogin;
import com.iiitb.tcp_backend.service.AdminDetailsService;
import com.iiitb.tcp_backend.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AdminController {
    @Autowired
    AdminDetailsService admin_service;
    @Autowired
    AdminLoginService admin_login_service;

    @PostMapping("/PostAdminDetails")
    public ResponseEntity<String> addDoctor(@RequestBody AdminDetails admindetails) {
        try {
            //System.out.println("asdfhj");
            String ans = "";
            AdminDetails adminDetails = admindetails;
            Admin admin = new Admin(adminDetails.getName(), adminDetails.getGender(),adminDetails.getDob(), adminDetails.getPhone_number());
            admin = admin_service.save(admin);

            AdminLogin adminLogin = new AdminLogin(adminDetails.getEmail_id(),adminDetails.getPassword(),admin.getAdminId(), true);
            adminLogin = admin_login_service.save(adminLogin);

            ans = "Success";
            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/GetAdminsList")
    public ResponseEntity<List<AdminDetails>> sendAdminList(){

        try{
            List<AdminDetails> adminDetails = new ArrayList<AdminDetails>();
            List<Admin> admins = admin_service.getAdminList();

            //System.out.println(doctorDetails.toString());

            for (int i=0 ; i<admins.size() ; i++)
            {
                Admin singleAdmin = admins.get(i);

                String name = singleAdmin.getAdminName();
                Date dob = singleAdmin.getAdminDob();
                String gender = singleAdmin.getAdminGender();
                String email_id = "nobody@gmail.com";
                String phone_number = singleAdmin.getAdminPhoneNumber();
                String password = "qwertyjhgsa";

                AdminDetails admin = new AdminDetails(name, dob, gender, email_id, password, phone_number);

                adminDetails.add(admin);

            }

            for(int i=0 ; i<adminDetails.size() ; i++)
            {
                System.out.println(adminDetails.get(i).toString());
            }

            return new ResponseEntity<>(adminDetails, HttpStatus.OK);

        }

        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
