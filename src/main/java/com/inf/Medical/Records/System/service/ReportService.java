package com.inf.Medical.Records.System.service;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.data.Visit;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReportService {
    List<Patient> getPatientsByDiagnosisName(String diagnosisName);
    List<Map.Entry<String, Long>> getMostDiagnosedDiseases();
    List<Patient> getPatientsByGP(String gpName);
    List<Object[]> getPatientCountsByGP();
    List<Object[]> getVisitCountsByDoctor();
    List<Object[]> getVisitsByPatient();
    List<Object[]> getVisitsByDoctorsWithinPeriod(LocalDate startDate, LocalDate endDate);
    List<Visit> getVisitsByDoctorAndPeriod(Long doctorId, LocalDate startDate, LocalDate endDate);
    Object[] getMonthWithMostSickLeaves();
    List<Doctor> getDoctorsWithMostSickLeaves();
}
