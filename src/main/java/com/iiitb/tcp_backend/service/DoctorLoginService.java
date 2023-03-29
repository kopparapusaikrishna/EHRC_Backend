package com.iiitb.tcp_backend.service;

import com.iiitb.tcp_backend.model.DoctorLogin;
import com.iiitb.tcp_backend.repository.DoctorLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DoctorLoginService {

    @Autowired
    private DoctorLoginRepository repo;

    public DoctorLogin save(DoctorLogin movement) {
        System.out.println("Before" + movement.getDoctorEmailId());
        repo.save(movement);
        System.out.println("After"+ movement.getDoctorId());
        return movement;
    }

}
