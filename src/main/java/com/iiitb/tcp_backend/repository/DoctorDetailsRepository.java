package com.iiitb.tcp_backend.repository;
import com.iiitb.tcp_backend.model.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, String>{
    @Query(value = "SELECT * FROM doctor_details WHERE doctor_id = :id", nativeQuery = true)
    DoctorDetails findByDoctorId(@Param("id") String id);
}
