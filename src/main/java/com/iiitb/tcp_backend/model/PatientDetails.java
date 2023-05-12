package com.iiitb.tcp_backend.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "patient_details")
public class PatientDetails {
    @Id
    @Column(name = "patient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patientId;
    @Column(name = "patient_name")
    //@Convert(converter = PIIAttributeConverter.class)
    private String patientName;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Column(name = "patient_dob")
    //@Convert(converter = PIIAttributeConverter.class)
    private Date patientDob;

    @Column(name = "gender")
    //@Convert(converter = PIIAttributeConverter.class)
    private String gender;

    @Column(name = "patient_phone_number")
    //@Convert(converter = PIIAttributeConverter.class)
    private String patientPhoneNumber;

    @Column(name = "patient_location")
    //@Convert(converter = PIIAttributeConverter.class)
    private String patientLocation;

    @Column(name = "patient_pin")
    private String patientPin;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Column(name= "is_active")
    private boolean isActive;

    @Override
    public String toString() {
        return "PatientDetails{" +
                "patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", patientDob=" + patientDob +
                ", gender='" + gender + '\'' +
                ", patientPhoneNumber='" + patientPhoneNumber + '\'' +
                ", patientLocation='" + patientLocation + '\'' +
                ", patientPin='" + patientPin + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    public PatientDetails() {
    }

    public PatientDetails(String patientName,  Date patientDob, String gender, String patientPhoneNumber, String patientLocation, boolean isActive, String pin) {
        this.patientName = patientName;
        this.patientDob = patientDob;
        this.gender = gender;
        this.patientPhoneNumber = patientPhoneNumber;
        this.patientLocation = patientLocation;
        this.isActive = isActive;
        this.patientPin = pin;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Date getPatientDob() {
        return patientDob;
    }

    public void setPatientDob(String patientDob) {
        this.patientDob = Date.valueOf(patientDob);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPatientPhoneNumber() {
        return patientPhoneNumber;
    }

    public void setPatientPhoneNumber(String patientPhoneNumber) {
        this.patientPhoneNumber = patientPhoneNumber;
    }

    public String getPatientLocation() {
        return patientLocation;
    }

    public void setPatientLocation(String patientLocation) {
        this.patientLocation = patientLocation;
    }

    public String getPatientPin() {
        return patientPin;
    }

    public void setPatientPin(String patientPin) {
        this.patientPin = patientPin;
    }
}