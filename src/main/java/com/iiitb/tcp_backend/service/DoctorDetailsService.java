package com.iiitb.tcp_backend.service;
import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.repository.DoctorDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
public class DoctorDetailsService {
    @Autowired
    private DoctorDetailsRepository repo;
    public DoctorDetails findById(String id)
    {
        return repo.findByDoctorId(id);
    }

}
