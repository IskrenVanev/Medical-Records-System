package com.inf.Medical.Records.System.web.view;


import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.data.Visit;
import com.inf.Medical.Records.System.service.DoctorService;
import com.inf.Medical.Records.System.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/reports")
public class ReportViewController {
    private final ReportService reportService;
    private final DoctorService doctorService;
    @GetMapping
    public String getReports(Model model) {
        return "/reports/reports"; // Renders patients list view
    }
    // Endpoint for getting patients with a specific diagnosis
    @GetMapping("/patients-by-diagnosis")
    public String getPatientsByDiagnosis(@RequestParam(required = false) String diagnosisName, Model model) {
        if (diagnosisName != null && !diagnosisName.isEmpty()) {
            List<Patient> patients = reportService.getPatientsByDiagnosisName(diagnosisName);
            model.addAttribute("patients", patients);
        } else {
            model.addAttribute("patients", List.of()); // No patients found
        }
        return "reports/patients-by-diagnosis"; // Make sure this matches the name of your Thymeleaf template
    }

    @GetMapping("/most-diagnosed-diseases")
    public String getMostDiagnosedDiseases(Model model) {
        List<Map.Entry<String, Long>> mostDiagnosed = reportService.getMostDiagnosedDiseases();
        model.addAttribute("mostDiagnosed", mostDiagnosed);
        return "reports/most-diagnosed-diseases";
    }
    @GetMapping("/patients-by-gp")
    public String getPatientsByGP(@RequestParam(required = false) String gpName, Model model) {
        List<Patient> patients = new ArrayList<>(); // Initialize with an empty list
        if (gpName != null && !gpName.isEmpty()) {
            patients = reportService.getPatientsByGP(gpName);
        }
        model.addAttribute("patients", patients);
        model.addAttribute("gpName", gpName); // Optional: Keep the GP name in the model
        return "reports/patients-by-gp";
    }

    @GetMapping("/patient-counts-by-gp")
    public String getPatientCountsByGP(Model model) {
        List<Object[]> patientCounts = reportService.getPatientCountsByGP();
        model.addAttribute("patientCounts", patientCounts);
        return "reports/patient-counts-by-gp";
    }

    @GetMapping("/visit-counts-by-doctor")
    public String getVisitCountsByDoctor(Model model) {
        List<Object[]> visitCounts = reportService.getVisitCountsByDoctor();
        model.addAttribute("visitCounts", visitCounts);
        return "reports/visit-counts-by-doctor";
    }

    @GetMapping("/visits-by-patient")
    public String getVisitsByPatient(Model model) {
        List<Object[]> visitsByPatient = reportService.getVisitsByPatient();
        model.addAttribute("visitsByPatient", visitsByPatient);
        return "reports/visits-by-patient";
    }

    @GetMapping("/visits-by-doctors")
    public String getVisitsByDoctorsWithinPeriod(@RequestParam(required = false) String startDate,
                                                 @RequestParam(required = false) String endDate,
                                                 Model model) {
        List<Object[]> visits = Collections.emptyList(); // Default to empty list

        if (startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            visits = reportService.getVisitsByDoctorsWithinPeriod(start, end);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
        }

        model.addAttribute("visits", visits); // Always add 'visits' to the model
        return "reports/visits-by-doctors";
    }

    @GetMapping("/visits-by-doctor")
    public String getVisitsByDoctorAndPeriod(@RequestParam(required = false) Long doctorId,
                                             @RequestParam(required = false) String startDate,
                                             @RequestParam(required = false) String endDate,
                                             Model model) {
        List<Visit> visits = Collections.emptyList();
        List<Doctor> doctors = doctorService.getDoctors();

        if (doctorId != null && startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            visits = reportService.getVisitsByDoctorAndPeriod(doctorId, start, end);
        }

        model.addAttribute("visits", visits);
        model.addAttribute("doctors", doctors);
        model.addAttribute("doctorId", doctorId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "reports/visits-by-doctor";
    }

    @GetMapping("/most-sick-leaves")
    public String getMonthWithMostSickLeaves(Model model) {
        Object[] mostSickLeaves = reportService.getMonthWithMostSickLeaves();
        if (mostSickLeaves != null) {
            int month = (int) mostSickLeaves[0];
            long count = (long) mostSickLeaves[1];
            model.addAttribute("month", month);
            model.addAttribute("count", count);
        } else {
            model.addAttribute("month", null);
            model.addAttribute("count", 0);
        }
        return "reports/most-sick-leaves";
    }

    @GetMapping("/most-sick-leaves-doctors")
    public String getDoctorsWithMostSickLeaves(Model model) {
        // Fetch the doctors with the most sick leaves from the service
        List<Map.Entry<Doctor, Long>> doctorsWithSickLeaves = reportService.getDoctorsWithMostSickLeaves()
                .stream()
                .map(doctor -> new AbstractMap.SimpleEntry<>(doctor, getSickLeaveCount(doctor))) // Create a map entry with doctor and their sick leave count
                .collect(Collectors.toList());

        model.addAttribute("doctors", doctorsWithSickLeaves);
        return "reports/most-sick-leaves-doctors";
    }

    // Method to get sick leave count for a doctor (you can adjust based on your actual logic)
    private long getSickLeaveCount(Doctor doctor) {
        return doctor.getVisits().stream()
                .filter(visit -> visit.getSickLeave() != null)
                .count();
    }

}
