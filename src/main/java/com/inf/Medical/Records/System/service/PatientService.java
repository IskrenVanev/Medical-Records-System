package com.inf.Medical.Records.System.service;

import com.inf.Medical.Records.System.data.Diagnosis;
import com.inf.Medical.Records.System.data.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<Patient> getPatients();

    Optional<Patient> getPatientById(long id);

    Patient createPatient(Patient patient);

    Patient updatePatient(Patient patient, long id);

    void deletePatient(long id);

}
