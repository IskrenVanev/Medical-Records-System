package com.inf.Medical.Records.System.repo;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
