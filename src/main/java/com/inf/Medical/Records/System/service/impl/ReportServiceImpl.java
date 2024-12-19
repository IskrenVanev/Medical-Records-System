package com.inf.Medical.Records.System.service.impl;

import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.repo.VisitRepository;
import com.inf.Medical.Records.System.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final VisitRepository visitRepository;

    public ReportServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    // Method to get patients with a specific diagnosis
    public List<Patient> getPatientsByDiagnosisName(String diagnosisName) {

        return visitRepository.findPatientsByDiagnosisName(diagnosisName);
    }

    @Override
    public List<Map.Entry<String, Long>> getMostDiagnosedDiseases() {
        // Retrieve all visits with diagnoses and extract the diagnosis name/code (from the first diagnosis in the list)
        List<String> diagnoses = visitRepository.findAll()
                .stream()
                .flatMap(visit -> visit.getDiagnosis().stream()) // Assuming visit.getDiagnoses() returns List<Diagnosis>
                .map(diagnosis -> diagnosis.getName()) // Get the name of each diagnosis
                .filter(diagnosis -> diagnosis != null) // Ensure we don't have null diagnoses
                .collect(Collectors.toList());

        // Group the diagnoses and count their occurrences
        Map<String, Long> diagnosisCount = diagnoses.stream()
                .collect(Collectors.groupingBy(diagnosis -> diagnosis, Collectors.counting()));

        // Sort the results by frequency (highest to lowest)
        return diagnosisCount.entrySet()
                .stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .collect(Collectors.toList());
    }
}
