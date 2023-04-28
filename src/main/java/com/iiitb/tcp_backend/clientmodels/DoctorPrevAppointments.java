package com.iiitb.tcp_backend.clientmodels;

import java.sql.Date;

public class DoctorPrevAppointments {

    private Date appointment_date;
    private String patient_name;
    private boolean follow_up;
    private int appointment_id;

    public DoctorPrevAppointments(Date appointment_date, String patient_name, boolean follow_up, int appointment_id) {
        this.appointment_date = appointment_date;
        this.patient_name = patient_name;
        this.follow_up = follow_up;
        this.appointment_id = appointment_id;
    }

    public Date getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(Date appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public boolean isFollow_up() {
        return follow_up;
    }

    public void setFollow_up(boolean follow_up) {
        this.follow_up = follow_up;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }
}
