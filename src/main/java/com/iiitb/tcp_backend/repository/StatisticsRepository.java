package com.iiitb.tcp_backend.repository;
import com.iiitb.tcp_backend.model.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<DoctorDetails, String>{

    @Query(value = "SELECT count(*) FROM appointments", nativeQuery = true)
    int getAppointmentCount();
    @Query(value = "SELECT count(*) FROM doctor_details", nativeQuery = true)
    int getDocCount();

}
