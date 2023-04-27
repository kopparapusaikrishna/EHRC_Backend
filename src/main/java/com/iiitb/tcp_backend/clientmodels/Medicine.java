package com.iiitb.tcp_backend.clientmodels;

public class Medicine {
    String medicineName;
    String power;

    String dosage;

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "medicineName='" + medicineName + '\'' +
                ", power='" + power + '\'' +
                ", dosage='" + dosage + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Medicine(String medicineName, String power, String dosage, String duration) {
        this.medicineName = medicineName;
        this.power = power;
        this.dosage = dosage;
        this.duration = duration;
    }

    String duration;

}
