package com.iiitb.tcp_backend.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "doctor_login")
public class DoctorLogin {
    @Id
    @Column(name = "doctor_email_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String doctorEmailId ;

    @Column(name = "doctor_password")
    private String doctorPassword ;

    @Column(name = "doctor_id")
    private String doctorId;

    @Column(name = "is_doctor_active")
    private boolean isDoctorActive;
    
    public DoctorLogin() {
    }

    public DoctorLogin(String doctorEmailId, String doctorPassword, String doctorId, boolean isDoctorActive) {
        this.doctorEmailId = doctorEmailId;
        this.doctorPassword = doctorPassword;
        this.doctorId = doctorId;
        this.isDoctorActive = isDoctorActive;
    }

    public String getDoctorEmailId() {
        return doctorEmailId;
    }

    public void setDoctorEmailId(String doctorEmailId) {
        this.doctorEmailId = doctorEmailId;
    }

    public String getDoctorPassword() {
        return doctorPassword;
    }

    public boolean getIsDoctorActive()
    {
    	return isDoctorActive;
    }

    public void setIsDoctorActive(boolean isDoctorActive)
    {
    	this.isDoctorActive = isDoctorActive;
    }
    
    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
