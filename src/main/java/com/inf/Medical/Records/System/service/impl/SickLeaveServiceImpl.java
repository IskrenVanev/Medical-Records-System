package com.inf.Medical.Records.System.service.impl;

import com.inf.Medical.Records.System.data.SickLeave;
import com.inf.Medical.Records.System.repo.PatientRepository;
import com.inf.Medical.Records.System.repo.SickLeaveRepository;
import com.inf.Medical.Records.System.service.SickLeaveService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SickLeaveServiceImpl implements SickLeaveService {
    private final SickLeaveRepository sickLeaveRepository;

    public SickLeaveServiceImpl(SickLeaveRepository sickLeaveRepository) {
        this.sickLeaveRepository = sickLeaveRepository;
    }


    @Override
    public List<SickLeave> getSickLeaves() {
        return this.sickLeaveRepository.findAll();
    }

    @Override
    public Optional<SickLeave> getSickLeaveById(long id) {
        return this.sickLeaveRepository.findById(id);
    }

    @Override
    public SickLeave createSickLeave(SickLeave sickLeave) {
        return this.sickLeaveRepository.save(sickLeave);
    }

    @Override
    public SickLeave updateSickLeave(SickLeave sickLeave, long id, LocalDate startDate) {
        return this.sickLeaveRepository.findById(id)
                .map(existingSickLeave -> {
                    existingSickLeave.setVisit(sickLeave.getVisit());
                    existingSickLeave.setStartDate(startDate);
                    existingSickLeave.setDurationInDays(sickLeave.getDurationInDays());
                    return this.sickLeaveRepository.save(existingSickLeave);
                })
                .orElseThrow(() -> new IllegalArgumentException("Sick Leave not found with id: " + id));
    }

    @Override
    public void deleteSickLeave(long id) {
        this.sickLeaveRepository.deleteById(id);
    }
}
