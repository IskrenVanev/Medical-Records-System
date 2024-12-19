package com.inf.Medical.Records.System.web.view;


import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/reports")
public class ReportViewController {
    private final ReportService reportService;
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
}
