package com.iiitb.tcp_backend.clientmodels;

import java.sql.Date;

public class PatientPrevAppointments {
    private Date appointmemt_date;
    private String doctor_dept;
    private String doctor_name;
    private int appointment_id;

    public PatientPrevAppointments(Date appointmemt_date, String doctor_dept, String doctor_name, int appointment_id) {
        this.appointmemt_date = appointmemt_date;
        this.doctor_dept = doctor_dept;
        this.doctor_name = doctor_name;
        this.appointment_id = appointment_id;
    }

    public Date getAppointmemt_date() {
        return appointmemt_date;
    }

    public void setAppointmemt_date(Date appointmemt_date) {
        this.appointmemt_date = appointmemt_date;
    }

    public String getDoctor_dept() {
        return doctor_dept;
    }

    public void setDoctor_dept(String doctor_dept) {
        this.doctor_dept = doctor_dept;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }
}
