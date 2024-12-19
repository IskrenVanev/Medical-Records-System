package com.inf.Medical.Records.System.service.impl;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.data.SickLeave;
import com.inf.Medical.Records.System.data.Visit;
import com.inf.Medical.Records.System.repo.DoctorRepository;
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
    private final SickLeaveRepository sickLeaveRepository;
    public VisitServiceImpl(VisitRepository visitRepository, SickLeaveRepository sickLeaveRepository) {
        this.visitRepository = visitRepository;
        this.sickLeaveRepository = sickLeaveRepository;
    }

    public SickLeave saveSickLeave(SickLeave sickLeave) {
        return sickLeaveRepository.save(sickLeave);  // Saves the sick leave to the database
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
                    if (visit.getSickLeave() != null) {
                        // If there is an incoming sick leave, update or create one
                        SickLeave existingSickLeave = existingVisit.getSickLeave();
                        if (existingSickLeave == null) {
                            existingSickLeave = new SickLeave();
                            existingVisit.setSickLeave(existingSickLeave);
                        }
                        existingSickLeave.setStartDate(visit.getSickLeave().getStartDate());
                        existingSickLeave.setDurationInDays(visit.getSickLeave().getDurationInDays());
                    } else {
                        // If no sick leave, ensure it's removed
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
