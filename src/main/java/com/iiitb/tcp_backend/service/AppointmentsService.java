package com.iiitb.tcp_backend.service;


import com.iiitb.tcp_backend.model.Appointments;
import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.repository.AppointmentsRepository;
import com.iiitb.tcp_backend.repository.DoctorDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AppointmentsService {

    @Autowired
    private AppointmentsRepository repo;

    public List<Appointments> findById(int id)
    {
        return repo.findByDoctorId(id);
    }
    public List<Appointments> getAppointments()
    {
        return repo.getAppointments();
    }

}
