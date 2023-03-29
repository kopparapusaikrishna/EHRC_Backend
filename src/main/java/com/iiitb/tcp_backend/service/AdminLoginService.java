package com.iiitb.tcp_backend.service;


import com.iiitb.tcp_backend.model.AdminLogin;
import com.iiitb.tcp_backend.model.DoctorLogin;
import com.iiitb.tcp_backend.repository.AdminLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdminLoginService {

    @Autowired
    private AdminLoginRepository repo;

    public AdminLogin save(AdminLogin movement) {
        System.out.println("Before" + movement.getAdminEmailId());
        repo.save(movement);
        System.out.println("After"+ movement.getAdminId());
        return movement;
    }

}