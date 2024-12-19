package com.inf.Medical.Records.System.service;

import com.inf.Medical.Records.System.data.Patient;

import java.util.List;
import java.util.Map;

public interface ReportService {
    List<Patient> getPatientsByDiagnosisName(String diagnosisName);
    List<Map.Entry<String, Long>> getMostDiagnosedDiseases();
}
