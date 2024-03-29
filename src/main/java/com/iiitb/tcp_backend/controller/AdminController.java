package com.iiitb.tcp_backend.controller;



import com.iiitb.tcp_backend.JwtUtil.JwtUserDetailsService;
import com.iiitb.tcp_backend.JwtUtil.TokenManager;
import com.iiitb.tcp_backend.JwtUtil.models.JwtResponseModel;
import com.iiitb.tcp_backend.clientmodels.AdminDetails;
import com.iiitb.tcp_backend.clientmodels.Doctor;
import com.iiitb.tcp_backend.model.Admin;
import com.iiitb.tcp_backend.model.AdminLogin;
import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.model.DoctorLogin;
import com.iiitb.tcp_backend.repository.AdminDetailsRepository;
import com.iiitb.tcp_backend.repository.AdminLoginRepository;
import com.iiitb.tcp_backend.repository.DoctorDetailsRepository;
import com.iiitb.tcp_backend.service.AdminDetailsService;
import com.iiitb.tcp_backend.service.AdminLoginService;

import com.iiitb.tcp_backend.service.DoctorLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.iiitb.tcp_backend.JwtUtil.models.JwtResponseModel;

import javax.transaction.Transactional;

@CrossOrigin(origins = "*")
@RestController
public class AdminController {
    @Autowired
    AdminDetailsService admin_service;
    @Autowired
    AdminDetailsRepository adminDetailsRepository;
    @Autowired
    AdminLoginService admin_login_service;
    @Autowired
    AdminLoginRepository adminLoginRepository;

    @Autowired
    JwtUserDetailsService userDetailsService;
    @Autowired
    TokenManager tokenManager;

    @GetMapping("/Admin")
    public ResponseEntity Adminlogin(@RequestParam String username,@RequestParam String password){
        System.out.println(username);
        AdminLogin s = admin_login_service.findByemail(username);
        System.out.println("admin login");
        if(s == null){
            return ResponseEntity.ok(new JwtResponseModel("not"));
        }
        if (!s.getIsAdminActive())
            return ResponseEntity.ok(new JwtResponseModel("not"));
        System.out.println(s.getAdminPassword());
        if(s!=null && s.getAdminPassword().equals(password)) {
            System.out.println("entered");
            final UserDetails userDetails = userDetailsService.loadAdminByUsername(username,password);

            final String jwtToken = tokenManager.generateJwtToken(userDetails);
            //System.out.println("hellso");
            //System.out.println(jwtToken);
            //System.out.println());
            return ResponseEntity.ok(new JwtResponseModel(jwtToken));
        }
        return ResponseEntity.ok(new JwtResponseModel("not"));
    }
    @GetMapping("/admindetails")
    public ResponseEntity<Admin> getdoctordetails(@RequestParam String email_id){
        System.out.println(email_id);
        System.out.println("fgdf");
        AdminLogin dl=admin_login_service.getadminlogindetails(email_id);
        Admin dd=adminDetailsRepository.findByAdminId(dl.getAdminId());
        if (dd!=null){
            return new ResponseEntity<>(dd,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
//    @Transactional
    @PostMapping("/PostAdminDetails")
    public ResponseEntity<String> addAdmin(@RequestBody AdminDetails admindetails) {
        try {
            //System.out.println("asdfhj");
            String ans = "";
            int flag = 1;

            System.out.println("dsdsds");
            List<AdminLogin> adminLogins = admin_login_service.getAdmins();

            for(int i=0; i<adminLogins.size(); i++)
            {
                if(adminLogins.get(i).getAdminEmailId().equalsIgnoreCase(admindetails.getEmail_id())){
                    flag = 0;
                    break;
                }
            }

            if(flag==1)
            {
                Admin admin = new Admin(admindetails.getName(), admindetails.getGender(),admindetails.getDob(), admindetails.getPhone_number());

                admin = admin_service.save(admin);

                AdminLogin adminLogin = new AdminLogin(admindetails.getEmail_id(),admindetails.getPassword(),admin.getAdminId(), true);
                System.out.println("dsdsds1");
                adminLogin = admin_login_service.save(adminLogin);

                ans = "Success";
            }
            else
                ans = "Failed. Email Id is already existing.";

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
                AdminLogin adminLoginDetails = admin_login_service.findById(singleAdmin.getAdminId());

                String name = singleAdmin.getAdminName();
                Date dob = singleAdmin.getAdminDob();
                int id = singleAdmin.getAdminId();
                String gender = singleAdmin.getAdminGender();
                String email_id = adminLoginDetails.getAdminEmailId();
                String phone_number = singleAdmin.getAdminPhoneNumber();
                String password = "qwertyjhgsa";

                AdminLogin adLogin = admin_login_service.findById(id);
                Admin admin1;

                if (adLogin.getIsAdminActive()==true){
                    AdminDetails admin = new AdminDetails(id,name, dob, gender, email_id, password, phone_number);
                    adminDetails.add(admin);
            }


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

    @Transactional
    @DeleteMapping("/DeleteAdmin")
    public ResponseEntity<String> deleteAdmin(@RequestParam int adminId)
    {
        try
        {
            AdminLogin adminLoginDetails = admin_login_service.findById(adminId);
            //adminLoginDetails.setIsAdminActive(false);
            adminLoginRepository.updateadminlogindetails(adminLoginDetails.getAdminId());
            //admin_login_service.save(adminLoginDetails);

            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
