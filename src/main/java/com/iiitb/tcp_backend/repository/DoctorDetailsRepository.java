package com.iiitb.tcp_backend.repository;
import com.iiitb.tcp_backend.clientmodels.DepartmentStats;
import com.iiitb.tcp_backend.model.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, String>{
    @Query(value = "SELECT DISTINCT doctor_id,cast(AES_DECRYPT(from_base64(doctor_name), 'mykeystring') AS char) as doctor_name, doctor_dob, doctor_gender, cast(AES_DECRYPT(from_base64(department_name), 'mykeystring') AS char) as department_name,cast(AES_DECRYPT(from_base64(doctor_qualification), 'mykeystring') AS char) as doctor_qualification,cast(AES_DECRYPT(from_base64(doctor_clinic_address), 'mykeystring') AS char) as doctor_clinic_address, cast(AES_DECRYPT(from_base64(doctor_phone_number), 'mykeystring') AS char) as doctor_phone_number, doctor_availability, doctor_start_date FROM doctor_details WHERE doctor_id = :id", nativeQuery = true)
    DoctorDetails findByDoctorId(@Param("id") int id);



    //@Query()
   
    @Query(value = "SELECT doctor_details.doctor_id,cast(AES_DECRYPT(from_base64(doctor_name), 'mykeystring') AS char) as doctor_name, doctor_dob, doctor_gender, cast(AES_DECRYPT(from_base64(department_name), 'mykeystring') AS char) as department_name,cast(AES_DECRYPT(from_base64(doctor_qualification), 'mykeystring') AS char) as doctor_qualification,cast(AES_DECRYPT(from_base64(doctor_clinic_address), 'mykeystring') AS char) as doctor_clinic_address, cast(AES_DECRYPT(from_base64(doctor_phone_number), 'mykeystring') AS char) as doctor_phone_number, doctor_availability, doctor_start_date FROM doctor_details INNER JOIN doctor_login ON doctor_details.doctor_id=doctor_login.doctor_id", nativeQuery = true)
    List<DoctorDetails> getDoctors();

    @Query(value = "SELECT doctor_id FROM doctor_details ORDER BY doctor_id DESC LIMIT 1",nativeQuery = true)
    int lastdoctordetails();

    @Modifying
    @Query(value = "update doctor_details set doctor_availability = :status where doctor_id = :doctor_id ",nativeQuery = true)
    void updatedoctordetails(@Param("doctor_id") int doctor_id,@Param("status") boolean status);
    @Query(value = "SELECT distinct cast(AES_DECRYPT(from_base64(department_name), 'mykeystring') AS char) FROM doctor_details", nativeQuery = true)
    List<String> getDepartments();

    @Query(value = "SELECT count(department_name) FROM doctor_details where doctor_availability = true and  department_name = cast(to_base64(AES_ENCRYPT(:department_name, 'mykeystring')) AS char)", nativeQuery = true)
    int countDepartment(@Param("department_name") String department_name);

    @Query(value = "SELECT cast(AES_DECRYPT(from_base64(department_name), 'mykeystring') AS char) as department_name FROM appointments INNER JOIN doctor_details ON doctor_details.doctor_id = appointments.doctor_id where appointment_date between :start_date and :end_date GROUP BY department_name", nativeQuery = true)
    List<String> getDepartmentStats(@Param("start_date") String start_date, @Param("end_date") String end_Date);

    @Query(value = "SELECT count(department_name) FROM appointments INNER JOIN doctor_details ON doctor_details.doctor_id = appointments.doctor_id where department_name = cast(to_base64(AES_ENCRYPT(:department_name, 'mykeystring')) AS char) and appointment_date between :start_date and :end_date GROUP BY department_name", nativeQuery = true)
    int getDepartmentCount(@Param("department_name") String department, @Param("start_date") String start_date, @Param("end_date") String end_Date);

    @Query(value = "SELECT COUNT(cast(AES_DECRYPT(from_base64(department_name), 'mykeystring') AS char)) AS department_name_count FROM doctor_details WHERE department_name = cast(to_base64(AES_ENCRYPT(:department_name, 'mykeystring')) AS char) GROUP BY department_name", nativeQuery = true)
    int getDoctorCount(@Param("department_name") String department_name);



    //  this.doctorName = doctorName;
//        this.doctorDob = doctorDob;
//        this.doctorGender = doctorGender;
//        this.departmentName = departmentName;
//        this.doctorQualification = doctorQualification;
//        this.doctorClinicAddress = doctorClinicAddress;
//        this.doctorPhoneNumber = doctorPhoneNumber;
//        this.doctorAvailability = doctorAvailability;
//        this.doctorStartDate = doctorStartDate;
    @Modifying
    @Query(value = "INSERT INTO doctor_details (doctor_name, doctor_dob, doctor_gender, department_name, doctor_qualification, doctor_clinic_address, doctor_phone_number, doctor_availability, doctor_start_date) VALUES (cast(to_base64(AES_ENCRYPT(:doctor_name, 'mykeystring')) as char), :doctor_dob, :doctor_gender, cast(to_base64(AES_ENCRYPT(:department_name, 'mykeystring')) as char), cast(to_base64(AES_ENCRYPT(:doctor_qualification, 'mykeystring')) as char), cast(to_base64(AES_ENCRYPT(:doctor_clinic_address, 'mykeystring')) as char), cast(to_base64(AES_ENCRYPT(:doctor_phone_number, 'mykeystring')) as char), :doctor_availability, :doctor_start_date)", nativeQuery = true)
void adddoctor(@Param("doctor_name") String doctor_name, @Param("doctor_dob") Date doctor_dob, @Param("doctor_gender") String doctor_gender, @Param("department_name") String department_name, @Param("doctor_qualification") String doctor_qualification, @Param("doctor_clinic_address") String doctor_clinic_address, @Param("doctor_phone_number") String doctor_phone_number, @Param("doctor_availability") boolean doctor_availability, @Param("doctor_start_date") Date doctor_start_date);

}
