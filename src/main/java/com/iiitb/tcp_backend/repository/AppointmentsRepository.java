package com.iiitb.tcp_backend.repository;

import com.iiitb.tcp_backend.model.Admin;
import com.iiitb.tcp_backend.model.Appointments;
import com.iiitb.tcp_backend.model.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.sql.Date;

import java.util.List;

public interface AppointmentsRepository extends JpaRepository<Appointments, String> {

    @Query(value = "SELECT appointment_id,doctor_id,patient_id,appointment_date,follow_up,follow_up_date,cast(AES_DECRYPT(from_base64(medicines), 'mykeystring') AS char) as medicines,patient_weight,patient_temperature,cast(AES_DECRYPT(from_base64(patient_bp), 'mykeystring') AS char) as patient_bp FROM Appointments a WHERE a.doctor_id = :id", nativeQuery = true)
    List<Appointments> findByDoctorId(@Param("id") int id);
    @Query(value = "SELECT appointment_id FROM appointments ORDER BY appointment_id DESC LIMIT 1",nativeQuery = true)
    int lastappointmentdetails();
    @Query(value = "SELECT appointment_id,doctor_id,patient_id,appointment_date,follow_up,follow_up_date,cast(AES_DECRYPT(from_base64(medicines), 'mykeystring') AS char) as medicines,patient_weight,patient_temperature,cast(AES_DECRYPT(from_base64(patient_bp), 'mykeystring') AS char) as patient_bp FROM Appointments a WHERE a.patient_id = :id", nativeQuery = true)
    List<Appointments> findByPatientId(@Param("id") int id);

    @Query(value = "SELECT appointment_id,doctor_id,patient_id,appointment_date,follow_up,follow_up_date,cast(AES_DECRYPT(from_base64(medicines), 'mykeystring') AS char) as medicines,patient_weight,patient_temperature,cast(AES_DECRYPT(from_base64(patient_bp), 'mykeystring') AS char) as patient_bp FROM Appointments a where a.doctor_id=:doctor_id and a.appointment_date between :start_date and :end_date", nativeQuery = true)
    List<Appointments> getAppointments(@Param("start_date") Date start_date, @Param("end_date") Date end_date, @Param("doctor_id") int doctor_id);

    @Query(value= "SELECT appointment_id,doctor_id,patient_id,appointment_date,follow_up,follow_up_date,cast(AES_DECRYPT(from_base64(medicines), 'mykeystring') AS char) as medicines,patient_weight,patient_temperature,cast(AES_DECRYPT(from_base64(patient_bp), 'mykeystring') AS char) as patient_bp FROM Appointments a where a.appointment_id = :id", nativeQuery = true)
    Appointments findByAppointmentId(@Param("id") int id);
//     this.doctorId = doctorId;
//        this.patientId = patientId;
//        this.appointmentDate = appointmentDate;
//        this.followUp = followUp;
//        this.followUpDate = followUpDate;
//        this.medicines = medicines;
//        this.patientWeight = patientWeight;
//        this.patientTemperature = patientTemperature;
//        this.patientBp = patientBp;
    @Modifying
    @Query(value = "INSERT INTO appointments(doctor_id,patient_id,appointment_date,follow_up,follow_up_date,medicines,patient_weight,patient_temperature,patient_bp) VALUES(:doctor_id,:patient_id,:appointment_date,:follow_up,:follow_up_date,cast(to_base64(AES_ENCRYPT(:medicines ,'mykeystring')) as char),:patient_weight,:patient_temperature,cast(to_base64(AES_ENCRYPT(:patient_bp ,'mykeystring')) as char))",nativeQuery = true)
    void addappointment(@Param("doctor_id") int doctor_id,@Param("patient_id") int patient_id,@Param("appointment_date") Date appointment_date,@Param("follow_up") boolean follow_up,@Param("follow_up_date") Date follow_up_date,@Param("medicines") String medicines,@Param("patient_weight") int patient_weight,@Param("patient_temperature") int patient_temperature,@Param("patient_bp") String patient_bp);

}
