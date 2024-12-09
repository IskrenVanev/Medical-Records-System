package com.inf.Medical.Records.System.service.impl;

import com.inf.Medical.Records.System.data.Diagnosis;
import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.repo.DoctorRepository;
import com.inf.Medical.Records.System.repo.PatientRepository;
import com.inf.Medical.Records.System.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getPatients() {
        return this.patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(long id) {
        return this.patientRepository.findById(id);
    }

    @Override
    public Patient createPatient(Patient patient) {
        return this.patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Patient patient, long id) {
        return this.patientRepository.findById(id)
                .map(existingPatient -> {
                    existingPatient.setName(patient.getName());// if she gets married haha
                    existingPatient.setInsurancePaid(patient.isInsurancePaid());
                    existingPatient.setVisits(patient.getVisits());
                    existingPatient.setGeneralPractitioner(patient.getGeneralPractitioner());
                    return this.patientRepository.save(existingPatient);
                })
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + id));
    }

    @Override
    public void deletePatient(long id) {
        this.patientRepository.deleteById(id);
    }

}
