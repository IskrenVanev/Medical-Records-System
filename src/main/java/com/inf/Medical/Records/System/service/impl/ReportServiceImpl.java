package com.inf.Medical.Records.System.service.impl;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.data.Visit;
import com.inf.Medical.Records.System.repo.PatientRepository;
import com.inf.Medical.Records.System.repo.SickLeaveRepository;
import com.inf.Medical.Records.System.repo.VisitRepository;
import com.inf.Medical.Records.System.service.ReportService;
import com.inf.Medical.Records.System.service.VisitService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final SickLeaveRepository sickLeaveRepository;

    public ReportServiceImpl(VisitRepository visitRepository, PatientRepository patientRepository, SickLeaveRepository sickLeaveRepository) {
        this.visitRepository = visitRepository;
        this.patientRepository = patientRepository;
        this.sickLeaveRepository = sickLeaveRepository;
    }

    // Method to get patients with a specific diagnosis
    public List<Patient> getPatientsByDiagnosisName(String diagnosisName) {

        return patientRepository.findPatientsByDiagnosisName(diagnosisName);
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

    public List<Patient> getPatientsByGP(String gpName) {
        return patientRepository.findByGeneralPractitionerName(gpName);
    }

    public List<Object[]> getPatientCountsByGP() {
        // Custom query in the repository to get GP name and count of patients
        return patientRepository.findPatientCountsByGP();
    }
    public List<Object[]> getVisitCountsByDoctor() {
        // Custom query to get the doctor name and count of visits
        return visitRepository.findVisitCountsByDoctor();
    }
    public List<Object[]> getVisitsByPatient() {
        // Custom query to get patient name and list of visits
        return visitRepository.findVisitsByPatient();
    }
    public List<Object[]> getVisitsByDoctorsWithinPeriod(LocalDate startDate, LocalDate endDate) {
        return visitRepository.findVisitsByDoctorsWithinPeriod(startDate, endDate);
    }

    public List<Visit> getVisitsByDoctorAndPeriod(Long doctorId, LocalDate startDate, LocalDate endDate) {
        return visitRepository.findVisitsByDoctorAndPeriod(doctorId, startDate, endDate);
    }

    public Object[] getMonthWithMostSickLeaves() {
        List<Object[]> result = sickLeaveRepository.findMonthWithMostSickLeaves();
        return result.isEmpty() ? null : result.get(0); // Return the first result or null if no data
    }
    public List<Doctor> getDoctorsWithMostSickLeaves() {
        // Fetch all visits with their associated doctors and sick leaves
        List<Visit> visitsWithSickLeaves = visitRepository.findAll().stream()
                .filter(visit -> visit.getSickLeave() != null) // Filter only visits with sick leaves
                .toList();

        // Count the number of sick leaves issued by each doctor
        Map<Doctor, Long> doctorSickLeaveCount = visitsWithSickLeaves.stream()
                .flatMap(visit -> visit.getDoctors().stream()) // Flatten the doctor list from visits
                .collect(Collectors.groupingBy(doctor -> doctor, Collectors.counting()));

        // Find the maximum sick leave count
        long maxCount = doctorSickLeaveCount.values().stream()
                .max(Long::compareTo)
                .orElse(0L);

        // Return doctors with the maximum sick leave count
        return doctorSickLeaveCount.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .toList();
    }
}
