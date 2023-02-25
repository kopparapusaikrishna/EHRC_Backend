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

    public DoctorLogin() {
    }

    public DoctorLogin(String doctorEmailId, String doctorPassword, String doctorId) {
        this.doctorEmailId = doctorEmailId;
        this.doctorPassword = doctorPassword;
        this.doctorId = doctorId;
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
