package com.iiitb.tcp_backend.service;


import com.iiitb.tcp_backend.clientmodels.Medicine;
import com.iiitb.tcp_backend.model.Appointments;
import com.iiitb.tcp_backend.model.DoctorDetails;
import com.iiitb.tcp_backend.model.PatientDetails;
import com.iiitb.tcp_backend.repository.DoctorDetailsRepository;
import com.iiitb.tcp_backend.repository.PatientDetailsRepository;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
public class DatabasePdfService {

    @Autowired
    DoctorDetailsRepository doctorDetailsRepository;

    @Autowired
    PatientDetailsRepository patientDetailsRepository;

    public ByteArrayInputStream prescriptionPDFReport(Appointments appointment) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        DoctorDetails doctor = doctorDetailsRepository.findByDoctorId(appointment.getDoctorId());
        //String medicines = appointment.getMedicines();
        PatientDetails patient = patientDetailsRepository.findByPatientId(appointment.getPatientId());
        String medicines = "paracetomol%300 mg%3 times per day %5 days;dolo%650 mg%3 times per day%5 days";
        List<Medicine> medicineList = new ArrayList<>();
        List<String> strMedList = new ArrayList<>(Arrays.asList(medicines.split(";")));

        for(int i=0 ; i< strMedList.size() ; i++)
        {
            List<String> medDetails = new ArrayList<>(Arrays.asList(strMedList.get(i).split("%")));
            Medicine medicine = new Medicine(medDetails.get(0),medDetails.get(1),medDetails.get(2),medDetails.get(3));
            System.out.println(medicine.toString());
            medicineList.add(medicine);
        }


        try {

            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 28);
            Paragraph heading = new Paragraph("Prescription", titleFont);
            heading.setAlignment(Element.ALIGN_CENTER);
            document.add(heading);

            Font fontHeader = FontFactory.getFont(FontFactory.TIMES, 10);
            Paragraph para = new Paragraph("Doctor:" + doctor.getDoctorName(), fontHeader);
            para.setAlignment(Element.ALIGN_LEFT);
            document.add(para);
            //document.add(Chunk.NEWLINE);
            Paragraph name = new Paragraph("Patient Name:" + patient.getPatientName(), fontHeader);
            name.setAlignment(Element.ALIGN_LEFT);
            document.add(name);
            //document.add(Chunk.NEWLINE);
            Paragraph bp = new Paragraph("BP:" + appointment.getPatientBp(), fontHeader);
            name.setAlignment(Element.ALIGN_LEFT);
            document.add(bp);
            //document.add(Chunk.NEWLINE);
            Paragraph weight = new Paragraph("Weight:" + appointment.getPatientWeight() + " Kgs", fontHeader);
            name.setAlignment(Element.ALIGN_LEFT);
            document.add(weight);
            //document.add(Chunk.NEWLINE);
            Paragraph temperature = new Paragraph("Temperature (Fahrenheit):" + appointment.getPatientTemperature(), fontHeader);
            name.setAlignment(Element.ALIGN_LEFT);
            document.add(temperature);
            document.add(Chunk.NEWLINE);

            // Add Content to PDF file ->
            PdfPTable table = new PdfPTable(4);
            // Add PDF Table Header ->
            Stream.of( "Medicine Name", "Medicine Power", "Medicine Dosage", "Duration").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
                header.setBackgroundColor(Color.CYAN);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });

            for (Medicine medicine : medicineList) {
                PdfPCell nameCell = new PdfPCell(new Phrase(String.valueOf(medicine.getMedicineName())));
                nameCell.setPaddingLeft(4);
                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(nameCell);

                PdfPCell powerCell = new PdfPCell(new Phrase(medicine.getPower()));
                powerCell.setPaddingLeft(4);
                powerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                powerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(powerCell);

                PdfPCell dosageCell = new PdfPCell(new Phrase(medicine.getDosage()));
                dosageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                dosageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                dosageCell.setPaddingRight(4);
                table.addCell(dosageCell);

                PdfPCell durationCell = new PdfPCell(new Phrase(medicine.getDuration()));
                durationCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                durationCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                durationCell.setPaddingRight(4);
                table.addCell(durationCell);
            }
            document.add(table);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}