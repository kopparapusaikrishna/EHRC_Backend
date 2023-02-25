package com.iiitb.tcp_backend.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "appointments")
public class Appointments {

    @Id
    @Column(name = "appointment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String appointmentId;
    @Column(name = "doctor_id")
    private String doctorId;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "appointment_date")
    private Date appointmentDate;

    @Column(name = "prescription_id")
    private String prescriptionId;

    @Column(name = "follow_up")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean followUp;

    @Column(name = "follow_up_date")
    private Date followUpDate;

    public Appointments() {
    }

    public Appointments(String appointmentId, String doctorId, String patientId, String appointmentDate, String prescriptionId, boolean followUp, String followUpDate) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDate = Date.valueOf(appointmentDate);
        this.prescriptionId = prescriptionId;
        this.followUp = followUp;
        this.followUpDate = Date.valueOf(followUpDate);
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = Date.valueOf(appointmentDate);
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
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

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = Date.valueOf(followUpDate);
    }
}
