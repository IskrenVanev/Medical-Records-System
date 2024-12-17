package com.inf.Medical.Records.System.service;

import com.inf.Medical.Records.System.data.Diagnosis;
import com.inf.Medical.Records.System.data.Doctor;

import java.util.List;
import java.util.Optional;

public interface DiagnosisService {
    List<Diagnosis> getDiagnoses();

    Optional<Diagnosis> getDiagnosisById(long id);

    List<Diagnosis> getDiagnosesByIds(List<Long> diagnosisIds);

    Diagnosis createDiagnosis(Diagnosis diagnosis);

    Diagnosis updateDiagnosis(Diagnosis diagnosis, long id);

    void deleteDiagnosis(long id);

}
