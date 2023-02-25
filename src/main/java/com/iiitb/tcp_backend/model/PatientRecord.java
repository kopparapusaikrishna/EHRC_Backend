package com.iiitb.tcp_backend.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "patient_record")
public class PatientRecord {

    @Id
    @Column(name = "patient_record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String patientRecordId;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "patient_weight")
    private int patientWeight;

    @Column(name = "patient_temperature")
    private int patientTemperature;

    @Column(name = "patient_bp")
    private String patientBp;

    public PatientRecord() {
    }

    public PatientRecord(String patientRecordId, String patientId, int patientWeight, int patientTemperature, String patientBp) {
        this.patientRecordId = patientRecordId;
        this.patientId = patientId;
        this.patientWeight = patientWeight;
        this.patientTemperature = patientTemperature;
        this.patientBp = patientBp;
    }

    public String getPatientRecordId() {
        return patientRecordId;
    }

    public void setPatientRecordId(String patientRecordId) {
        this.patientRecordId = patientRecordId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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
