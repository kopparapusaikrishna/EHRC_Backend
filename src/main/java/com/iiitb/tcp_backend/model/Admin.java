package com.iiitb.tcp_backend.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String adminId;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "admin_gender")
    private String adminGender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Column(name = "doctor_dob")
    private Date doctorDob;

    public Admin() {
    }

    public Admin(String adminId, String adminName, String adminGender, String doctorDob) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminGender = adminGender;
        this.doctorDob = Date.valueOf(doctorDob);
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminGender() {
        return adminGender;
    }

    public void setAdminGender(String adminGender) {
        this.adminGender = adminGender;
    }

    public Date getDoctorDob() {
        return doctorDob;
    }

    public void setDoctorDob(String doctorDob) {
        this.doctorDob = Date.valueOf(doctorDob);
    }
}
