package com.inf.Medical.Records.System.web.api;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.service.DoctorService;
import com.inf.Medical.Records.System.service.PatientService;
import com.inf.Medical.Records.System.service.impl.PatientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientApiController {
    private final PatientService patientService;


    @GetMapping
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{id}")
    public Optional<Patient> getPatientById(@PathVariable long id) {
        return this.patientService.getPatientById(id);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return this.patientService.createPatient(patient);
    }

    @PutMapping({"/{id}"})
    public Patient updatePatient(@RequestBody Patient patient,@PathVariable long id) {
        return this.patientService.updatePatient(patient, id);
    }

    @DeleteMapping({"/{id}"})
    public void deletePatient(@PathVariable long id) {
        this.patientService.deletePatient(id);
    }
}
