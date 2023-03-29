package com.iiitb.tcp_backend.repository;

import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.model.DoctorLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorLoginRepository extends JpaRepository<DoctorLogin, String> {



}
