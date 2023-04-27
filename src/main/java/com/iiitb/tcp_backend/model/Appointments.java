package com.iiitb.tcp_backend.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.xml.bind.v2.schemagen.xmlschema.Appinfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "appointments")
public class Appointments {

    @Id
    @Column(name = "appointment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;
    @Column(name = "doctor_id")
    //@Convert(converter = PIIAttributeConverter.class)
    private int doctorId;

    @Column(name = "patient_id")
    //@Convert(converter = PIIAttributeConverter.class)
    private int patientId;

    @Column(name = "appointment_date")
    //@Convert(converter = PIIAttributeConverter.class)
    private Date appointmentDate;

    @Column(name = "follow_up")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    //@Convert(converter = PIIAttributeConverter.class)
    private boolean followUp;

    @Column(name = "follow_up_date")
    //@Convert(converter = PIIAttributeConverter.class)
    private Date followUpDate;

    @Column(name = "medicines")
    private String medicines;
    @Column(name = "patient_weight")
    //@Convert(converter = PIIAttributeConverter.class)
    private int patientWeight;

    @Column(name = "patient_temperature")
    //@Convert(converter = PIIAttributeConverter.class)
    private int patientTemperature;

    @Column(name = "patient_bp")
    //@Convert(converter = PIIAttributeConverter.class)
    private String patientBp;

    public Appointments(){

    }

    public Appointments(int doctorId, int patientId, Date appointmentDate, boolean followUp, Date followUpDate, String medicines, int patientWeight, int patientTemperature, String patientBp) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
        this.followUp = followUp;
        this.followUpDate = followUpDate;
        this.medicines = medicines;
        this.patientWeight = patientWeight;
        this.patientTemperature = patientTemperature;
        this.patientBp = patientBp;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }


    public boolean isFollowUp() {
        return followUp;
    }

    public void setFollowUp(boolean followUp) {
        this.followUp = followUp;
    }

    public Date getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(Date followUpDate) {
        this.followUpDate = followUpDate;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public int getPatientWeight() {
        return patientWeight;
    }

    public void setPatientWeight(int patientWeight) {
        this.patientWeight = patientWeight;
    }

    public int getPatientTemperature() {
        return patientTemperature;
    }

    public void setPatientTemperature(int patientTemperature) {
        this.patientTemperature = patientTemperature;
    }

    public String getPatientBp() {
        return patientBp;
    }

    public void setPatientBp(String patientBp) {
        this.patientBp = patientBp;
    }

}
