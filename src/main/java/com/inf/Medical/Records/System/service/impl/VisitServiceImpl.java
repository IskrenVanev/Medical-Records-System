package com.inf.Medical.Records.System.service.impl;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.SickLeave;
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
                    // Update scalar fields
                    existingVisit.setVisitDate(visit.getVisitDate());
                    existingVisit.setDiagnosis(visit.getDiagnosis());
                    existingVisit.setPatient(visit.getPatient());
                    existingVisit.setPrescribedTreatment(visit.getPrescribedTreatment());

                    // Handle the many-to-many relationship with doctors
                    List<Doctor> incomingDoctors = visit.getDoctors();  // assuming the Visit has a list of doctors
                    List<Doctor> existingDoctors = existingVisit.getDoctors();

                    if (incomingDoctors != null) {
                        // Clear the existing doctors and add the incoming ones
                        existingDoctors.clear();
                        existingDoctors.addAll(incomingDoctors);
                    }

                    // Handle SickLeave explicitly
                    SickLeave incomingSickLeave = visit.getSickLeave();
                    SickLeave existingSickLeave = existingVisit.getSickLeave();

                    if (incomingSickLeave != null) {
                        if (existingSickLeave == null) {
                            // Create new SickLeave if none exists
                            existingVisit.setSickLeave(incomingSickLeave);
                        } else {
                            // Update existing SickLeave
                            existingSickLeave.setStartDate(incomingSickLeave.getStartDate());
                            existingSickLeave.setDurationInDays(incomingSickLeave.getDurationInDays());
                        }
                    } else {
                        // Remove SickLeave if incoming is null
                        existingVisit.setSickLeave(null);
                    }

                    // Save the updated Visit entity
                    return this.visitRepository.save(existingVisit);
                })
                .orElseThrow(() -> new IllegalArgumentException("Visit not found with id: " + id));
    }

    @Override
    public void deleteVisit(long id) {
        this.visitRepository.deleteById(id);
    }
}
