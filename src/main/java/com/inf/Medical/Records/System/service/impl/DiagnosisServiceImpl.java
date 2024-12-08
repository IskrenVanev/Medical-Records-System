package com.inf.Medical.Records.System.service.impl;

import com.inf.Medical.Records.System.data.Diagnosis;
import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.repo.DiagnosisRepository;
import com.inf.Medical.Records.System.service.DiagnosisService;

import java.util.List;
import java.util.Optional;

public class DiagnosisServiceImpl implements DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;
    public DiagnosisServiceImpl(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }
    @Override
    public List<Diagnosis> getDiagnoses() {
        return this.diagnosisRepository.findAll();
    }

    @Override
    public Optional<Diagnosis> getDiagnosisById(long id) {
        return this.diagnosisRepository.findById(id);
    }

    @Override
    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return this.diagnosisRepository.save(diagnosis);
    }

    @Override
    public Diagnosis updateDiagnosis(Diagnosis diagnosis, long id) {
        return this.diagnosisRepository.findById(id)
                .map(existingDiagnose -> {
                    existingDiagnose.setName(diagnosis.getName());
                    existingDiagnose.setVisits(diagnosis.getVisits());
                    return this.diagnosisRepository.save(existingDiagnose);
                })
                .orElseThrow(() -> new IllegalArgumentException("Diagnosis not found with id: " + id));
    }

    @Override
    public void deleteDiagnosis(long id) {
        this.diagnosisRepository.deleteById(id);
    }

}
