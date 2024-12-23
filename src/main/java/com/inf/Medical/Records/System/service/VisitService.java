package com.inf.Medical.Records.System.service;

import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.data.SickLeave;
import com.inf.Medical.Records.System.data.Visit;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisitService {
    List<Visit> getVisits();

    SickLeave saveSickLeave(SickLeave sickLeave);

    Optional<Visit> getVisitById(long id);

    Visit createVisit(Visit visit);

    Visit updateVisit(Visit visit, long id, LocalDate startDate);

    void deleteVisit(long id);


}
