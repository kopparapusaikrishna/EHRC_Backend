package com.iiitb.tcp_backend.repository;

import com.iiitb.tcp_backend.model.Admin;
import com.iiitb.tcp_backend.model.Appointments;
import com.iiitb.tcp_backend.model.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.sql.Date;

import java.util.List;

public interface AppointmentsRepository extends JpaRepository<Appointments, String> {

    @Query(value = "SELECT * FROM Appointments a WHERE a.doctor_id = :id", nativeQuery = true)
    List<Appointments> findByDoctorId(@Param("id") int id);

    @Query(value = "SELECT * FROM Appointments a WHERE a.patient_id = :id", nativeQuery = true)
    List<Appointments> findByPatientId(@Param("id") int id);

    @Query(value = "SELECT * FROM Appointments a where a.doctor_id=:doctor_id and a.appointment_date between :start_date and :end_date", nativeQuery = true)
    List<Appointments> getAppointments(@Param("start_date") Date start_date, @Param("end_date") Date end_date, @Param("doctor_id") int doctor_id);

    @Query(value= "SELECT * FROM Appointments a where a.appointment_id = :id", nativeQuery = true)
    Appointments findByAppointmentId(@Param("id") int id);

}
