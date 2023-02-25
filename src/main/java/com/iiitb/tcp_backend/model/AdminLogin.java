package com.iiitb.tcp_backend.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "admin_login")
public class AdminLogin {
    @Id
    @Column(name = "admin_email_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String adminEmailId ;

    @Column(name = "admin_password")
    private String adminPassword ;

    @Column(name = "admin_id")
    private String adminId;

    public AdminLogin() {

    }

    public AdminLogin(String adminEmailId, String adminPassword, String adminId) {
        this.adminEmailId = adminEmailId;
        this.adminPassword = adminPassword;
        this.adminId = adminId;
    }

    public String getAdminEmailId() {
        return adminEmailId;
    }

    public void setAdminEmailId(String adminEmailId) {
        this.adminEmailId = adminEmailId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
