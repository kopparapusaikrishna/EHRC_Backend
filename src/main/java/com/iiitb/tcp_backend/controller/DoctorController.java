package com.iiitb.tcp_backend.controller;
import com.iiitb.tcp_backend.JwtUtil.TokenManager;
import com.iiitb.tcp_backend.JwtUtil.models.JwtResponseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.iiitb.tcp_backend.JwtUtil.JwtUserDetailsService;
import com.iiitb.tcp_backend.JwtUtil.models.JwtResponseModel;
import com.iiitb.tcp_backend.clientmodels.Doctor;
import com.iiitb.tcp_backend.clientmodels.DoctorAvailable;
import com.iiitb.tcp_backend.clientmodels.DoctorPrevAppointments;
import com.iiitb.tcp_backend.clientmodels.PatientPrevAppointments;
import com.iiitb.tcp_backend.model.Appointments;
import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.model.DoctorLogin;
import com.iiitb.tcp_backend.model.PatientDetails;
import com.iiitb.tcp_backend.repository.DoctorDetailsRepository;
import com.iiitb.tcp_backend.repository.DoctorLoginRepository;
import com.iiitb.tcp_backend.service.AppointmentsService;
import com.iiitb.tcp_backend.service.DoctorDetailsService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import com.iiitb.tcp_backend.service.DoctorLoginService;
import com.iiitb.tcp_backend.service.PatientsDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class DoctorController {
    @Autowired
    DoctorDetailsService doctor_service;
	@Autowired
	TokenManager tokenManager;
	@Autowired
	DoctorLoginService doctor_login_service;
    @Autowired
	JwtUserDetailsService userDetailsService;
	@Autowired
	DoctorDetailsRepository doctorDetailsRepository;
	@Autowired
	AppointmentsService appointments_service;

	@Autowired
	PatientsDetailsService patientsDetailsService;

    @PutMapping("/DoctorAvailability")
    public ResponseEntity<Integer> change_status(@RequestBody DoctorAvailable doctor) {
        try {
			//System.out.println("asdfhj");
            String ans = "";
            DoctorDetails doc = doctor_service.findById(doctor.getDoctorId());
            doc.setDoctorAvailability(doctor.isStatus());
            doctor_service.save(doc);
            ans = "Success";
			System.out.println(ans);
            return new ResponseEntity<>(1, HttpStatus.OK);
        } catch (Exception e) {
			System.out.println("endddddddddddddddddddddddnnnnnnnnnnnnnnnnnnnnnnnnnn");
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	/*@CrossOrigin
	@GetMapping("/verifyOTP")
	public ResponseEntity createToken(@RequestParam String phone_number,@RequestParam String otp) {

		String auth = patientLoginService.verifyOTP(phone_number,otp);
		System.out.println("reached: "+ auth);

		if (Objects.equals(auth, "approved")) {
			System.out.println("entered");
			final UserDetails userDetails = userDetailsService.loadUserByUsername(phone_number);
			final String jwtToken = tokenManager.generateJwtToken(userDetails);
			System.out.println("hellso");
			System.out.println(jwtToken);
			//System.out.println());
			return ResponseEntity.ok(new JwtResponseModel(jwtToken));

		}
		else
		{
			return ResponseEntity.ok(new JwtResponseModel("not"));
		}
		//return auth;
	}*/

	@GetMapping("/Doctor")
	public ResponseEntity Doctorlogin(@RequestParam String username,@RequestParam String password){
		DoctorLogin s = doctor_login_service.findByemail(username);
		if(s!=null && s.getDoctorPassword().equals(password)) {
			System.out.println("entered");
			final UserDetails userDetails = userDetailsService.loadDoctorByUsername(username,password);

			final String jwtToken = tokenManager.generateJwtToken(userDetails);
			System.out.println("hellso");
			//System.out.println(jwtToken);
			//System.out.println());
			return ResponseEntity.ok(new JwtResponseModel(jwtToken));
		}
		return ResponseEntity.ok(new JwtResponseModel("not"));
	}
	@GetMapping("/doctordetails")
	public ResponseEntity<DoctorDetails> getdoctordetails(@RequestParam String email_id){
		DoctorLogin dl=doctor_login_service.getdoctorlogindetails(email_id);
		DoctorDetails dd=doctorDetailsRepository.findByDoctorId(dl.getDoctorId());
		if (dd!=null){
			return new ResponseEntity<>(dd,HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@PostMapping ("/PostDoctorDetails")
	public ResponseEntity<String> addDoctor(@RequestBody Doctor doctor) {
		try {
			//System.out.println("asdfhj");
			String ans = "";
			int flag = 1;
			List<DoctorLogin> doctorLogins = doctor_login_service.getDoctors();

			for(int i=0; i<doctorLogins.size(); i++)
			{
				if(doctorLogins.get(i).getDoctorEmailId().equalsIgnoreCase(doctor.getEmail_id())){
					flag = 0;
					break;
				}
			}

			if(flag==1)
			{
				DoctorDetails doctorDetails = new DoctorDetails(doctor.getName(), doctor.getGender(), doctor.getDob(),doctor.getDepartment_name(),doctor.getQualification(),doctor.getClinic_address(), doctor.getPhone_number(), false, doctor.getDoctor_start_date());
				doctorDetails = doctor_service.save(doctorDetails);

				DoctorLogin doctorLogin = new DoctorLogin(doctor.getEmail_id(), doctor.getPassword(), doctorDetails.getDoctorId(), true);

				doctorLogin = doctor_login_service.save(doctorLogin);

				ans = "Success";
			}
			else
				ans = "Failed. Email Id is already existing.";

			return new ResponseEntity<>(ans, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    @GetMapping("/GetDoctorsList")
    public ResponseEntity<List<Doctor>> sendDoctorList(){
    	
    	try {
    		//System.out.println("HI");
    		List<Doctor> doctors = new ArrayList<Doctor>(); 
    		List<DoctorDetails> doctorDetails = doctor_service.getDoctors();
    		
    		//System.out.println(doctorDetails.toString());
    		
    		for (int i=0 ; i<doctorDetails.size() ; i++)
    		{
    			DoctorDetails singleDoctorDetails = doctorDetails.get(i);

    			String name = singleDoctorDetails.getDoctorName();
				int id = singleDoctorDetails.getDoctorId();
    		    Date dob = singleDoctorDetails.getDoctorDob();
    		    String gender = singleDoctorDetails.getDoctorGender();
    		    String email_id = "nobody@gmail.com";   
    		    Date doctor_start_date = singleDoctorDetails.getDoctorStartDate();
    		    String qualification = singleDoctorDetails.getDoctorQualification();
    			String department_name = singleDoctorDetails.getDepartmentName();
    		    String phone_number = singleDoctorDetails.getDoctorPhoneNumber();
    		    String clinic_address = singleDoctorDetails.getDoctorClinicAddress();
    		    String password = "qwertyjhgsaADGNN";

				DoctorLogin docLogin = doctor_login_service.findById(id);
				if(docLogin.getIsDoctorActive()==true){
					Doctor doctor = new Doctor(id,name,dob,gender,email_id,doctor_start_date,
							qualification,department_name, phone_number, clinic_address, password);

					doctors.add(doctor);
				}
    		}
    		
    		for(int i=0 ; i<doctors.size() ; i++)
    		{
    			//System.out.println(doctors.get(i).toString());
    		}
    		
    		return new ResponseEntity<>(doctors, HttpStatus.OK);
    		
    	}
    	
    	catch (Exception e)
    	{
    		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
		 	
    	
    }

	@DeleteMapping("/DeleteDoctor")
	public ResponseEntity<String> deleteDoctor(@RequestParam int doctorId)
	{
		System.out.println("Inside");
		try
		{
			DoctorLogin doctorLoginDetails = doctor_login_service.findById(doctorId);
			System.out.println("Inside1" + doctorId);
			doctorLoginDetails.setIsDoctorActive(false);
			System.out.println("Inside2");
			doctor_login_service.save(doctorLoginDetails);
			System.out.println("Inside3");

			return new ResponseEntity<>("Success", HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/NoOfConsultations")
	public ResponseEntity<Integer> noOfAppointments(@RequestParam int doctorId, @RequestParam int noOfDays)
	{
		try{
			List<Appointments> appointments = appointments_service.findById(doctorId);

			int count = 0;
			java.util.Date today = new java.util.Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -noOfDays);
			java.util.Date startDate = cal.getTime();

			for(int i=0 ; i<appointments.size() ; i++)
			{
				java.util.Date appointmentDate = new java.util.Date(appointments.get(i).getAppointmentDate().getTime());


				if(!(appointmentDate.before(startDate) || appointmentDate.after(today)))
				{
					count++;
				}

			}

			return new ResponseEntity<>(count, HttpStatus.OK);

		} catch (Exception e){
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/doctorPreviousAppointmentsList")
	public ResponseEntity<List<DoctorPrevAppointments>> getDoctorPrevAppointmentsLst(@RequestParam int doctor_id) {
		try
		{
			System.out.println("inside doctorPreviousAppointmentsList");
			List<Appointments> appointmentsLst =  appointments_service.findById(doctor_id);

			List<DoctorPrevAppointments> doctorPrevAppointments = new ArrayList<>();
			System.out.println("Work");
			for(int i=0; i<appointmentsLst.size(); i++) {
				Appointments presentAppointment = appointmentsLst.get(i);
				PatientDetails patientDetails = patientsDetailsService.findById(presentAppointment.getPatientId());
				DoctorPrevAppointments doctorPrevAppointment = new DoctorPrevAppointments(presentAppointment.getAppointmentDate(), patientDetails.getPatientName(), presentAppointment.isFollowUp(), presentAppointment.getAppointmentId());
				doctorPrevAppointments.add(doctorPrevAppointment);
			}
			System.out.println("Work");
			return new ResponseEntity<>(doctorPrevAppointments, HttpStatus.OK);

		}
		catch(Exception e){
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
