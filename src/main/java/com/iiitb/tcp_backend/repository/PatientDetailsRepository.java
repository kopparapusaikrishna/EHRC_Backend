package com.iiitb.tcp_backend.repository;

import com.iiitb.tcp_backend.model.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientDetailsRepository extends JpaRepository<PatientDetails, String> {

    @Query(value = "SELECT * FROM patient_details where patient_phone_number = :phone_number", nativeQuery = true)
    List<PatientDetails> getuserprofiles(@Param("phone_number") String phone_number);
    @Query(value = "SELECT * FROM patient_details where patient_id = :patient_id",nativeQuery = true)
    PatientDetails getuserprofilepass(@Param("patient_id") int patient_id);

    @Query(value = "SELECT * FROM patient_details WHERE patient_id = :id", nativeQuery = true)
    PatientDetails findByPatientId(@Param("id") int id);

    @Query(value = "SELECT * FROM patient_details WHERE patient_phone_number = :ph_no", nativeQuery = true)
    List<PatientDetails> findByPhoneNumber(@Param("ph_no") String ph_no);




}