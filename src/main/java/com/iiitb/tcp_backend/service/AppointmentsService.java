package com.iiitb.tcp_backend.service;


import com.iiitb.tcp_backend.model.Appointments;
import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.repository.AppointmentsRepository;
import com.iiitb.tcp_backend.repository.DoctorDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AppointmentsService {

    @Autowired
    private AppointmentsRepository repo;

    public Appointments save(Appointments appointments) {
        //System.out.println(movement.getDoctorId());
        //repo.save(appointments);
        repo.addappointment(appointments.getDoctorId(),appointments.getPatientId(),appointments.getAppointmentDate(),appointments.isFollowUp(),appointments.getFollowUpDate(),appointments.getMedicines(),appointments.getPatientWeight(),appointments.getPatientTemperature(),appointments.getPatientBp());
        //System.out.println(movement.getDoctorId());--
        int id= repo.lastappointmentdetails();
        return repo.findByAppointmentId(id);
    }

    public Appointments findByAppointmentId(int id) { return repo.findByAppointmentId(id);}

    public List<Appointments> findById(int id)
    {
        return repo.findByDoctorId(id);
    }

    public List<Appointments> findByPatientId(int id)
    {
        return repo.findByPatientId(id);
    }
    public List<Appointments> getAppointments(Date start_date, Date end_date, int doctor_id)
    {
        return repo.getAppointments(start_date, end_date, doctor_id);
    }

}
