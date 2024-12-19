package com.inf.Medical.Records.System.repo;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.data.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    // Fetch patients who have a particular diagnosis
    @Query("SELECT DISTINCT v.patient FROM Visit v " +
            "JOIN v.diagnosis d " +
            "WHERE d.name = :diagnosisName")
    List<Patient> findPatientsByDiagnosisName(@Param("diagnosisName") String diagnosisName);
}
