package com.iiitb.tcp_backend.clientmodels;

public class DoctorAvailable {
    private String doctorId;
    private boolean status;

    public DoctorAvailable() {
    }

    public DoctorAvailable(String doctorId, boolean status) {
        this.doctorId = doctorId;
        this.status = status;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
