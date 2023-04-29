package com.iiitb.tcp_backend.clientmodels;

public class DoctorMeetData {
    private String channel_name;
    private int appointment_id;

    public DoctorMeetData(String channel_name, int appointment_id) {
        this.channel_name = channel_name;
        this.appointment_id = appointment_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }
}
