package com.iiitb.tcp_backend.controller;

import com.iiitb.tcp_backend.model.Appointments;
import com.iiitb.tcp_backend.repository.AppointmentsRepository;
import com.iiitb.tcp_backend.service.DatabasePdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class PdfExportController {

    @Autowired
    AppointmentsRepository appointmentsRepository;

    @Autowired
    DatabasePdfService databasePdfService;

    @GetMapping(value = "/prescriptions/{appointmentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> prescriptionsReport(@PathVariable("appointmentId") int appointmentId)  throws IOException {
        System.out.println("inside");
        Appointments appointment = appointmentsRepository.findByAppointmentId(appointmentId);

        ByteArrayInputStream bis = databasePdfService.prescriptionPDFReport(appointment);
        System.out.println("below");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=prescription.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }



}
