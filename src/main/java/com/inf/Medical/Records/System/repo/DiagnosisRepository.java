package com.inf.Medical.Records.System.repo;

import com.inf.Medical.Records.System.data.Diagnosis;
import com.inf.Medical.Records.System.data.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
}
