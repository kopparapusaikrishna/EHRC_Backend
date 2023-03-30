package com.iiitb.tcp_backend.repository;

import com.iiitb.tcp_backend.model.Admin;
import com.iiitb.tcp_backend.model.Appointments;
import com.iiitb.tcp_backend.model.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentsRepository extends JpaRepository<Appointments, String> {

    @Query(value = "SELECT * FROM Appointments WHERE doctor_id = :id", nativeQuery = true)
    List<Appointments> findByDoctorId(@Param("id") int id);

    @Query(value = "SELECT * FROM Appointments", nativeQuery = true)
    List<Appointments> getAppointments();

}
