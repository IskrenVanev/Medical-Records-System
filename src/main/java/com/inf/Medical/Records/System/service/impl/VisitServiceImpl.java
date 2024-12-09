package com.inf.Medical.Records.System.service.impl;

import com.inf.Medical.Records.System.data.Visit;
import com.inf.Medical.Records.System.repo.SickLeaveRepository;
import com.inf.Medical.Records.System.repo.VisitRepository;
import com.inf.Medical.Records.System.service.VisitService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public List<Visit> getVisits() {
        return this.visitRepository.findAll();
    }

    @Override
    public Optional<Visit> getVisitById(long id) {
        return this.visitRepository.findById(id);
    }

    @Override
    public Visit createVisit(Visit visit) {
        return this.visitRepository.save(visit);
    }

    @Override
    public Visit updateVisit(Visit visit, long id, LocalDate startDate) {
        return this.visitRepository.findById(id)
                .map(existingVisit -> {
                    existingVisit.setVisitDate(visit.getVisitDate());
                    existingVisit.setDiagnosis(visit.getDiagnosis());
                    existingVisit.setDoctor(visit.getDoctor());
                    existingVisit.setPatient(visit.getPatient());
                    existingVisit.setSickLeave(visit.getSickLeave());
                    existingVisit.setPrescribedTreatment(visit.getPrescribedTreatment());
                    return this.visitRepository.save(existingVisit);
                })
                .orElseThrow(() -> new IllegalArgumentException("Visit not found with id: " + id));
    }

    @Override
    public void deleteVisit(long id) {
        this.visitRepository.deleteById(id);
    }
}
