package com.iiitb.tcp_backend.repository;

import com.iiitb.tcp_backend.model.AdminLogin;
import com.iiitb.tcp_backend.model.DoctorLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminLoginRepository extends JpaRepository<AdminLogin, String> {
}
