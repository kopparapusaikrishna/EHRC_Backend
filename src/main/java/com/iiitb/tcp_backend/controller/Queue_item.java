package com.iiitb.tcp_backend.controller;

public class Queue_item {
    private int patient_id;
    private int prev_appointment_id;

    public Queue_item(int patient_id, int prev_appointment_id) {
        this.patient_id = patient_id;
        this.prev_appointment_id = prev_appointment_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getPrev_appointment_id() {
        return prev_appointment_id;
    }

    public void setPrev_appointment_id(int prev_appointment_id) {
        this.prev_appointment_id = prev_appointment_id;
    }
}
