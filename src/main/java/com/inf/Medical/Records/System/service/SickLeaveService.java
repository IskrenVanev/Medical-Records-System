package com.inf.Medical.Records.System.service;

import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.data.SickLeave;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SickLeaveService {
    List<SickLeave> getSickLeaves();

    Optional<SickLeave> getSickLeaveById(long id);

    SickLeave createSickLeave(SickLeave sickLeave);

    SickLeave updateSickLeave(SickLeave sickLeave, long id, LocalDate startDate);

    void deleteSickLeave(long id);
}
