package com.inf.Medical.Records.System.web.api;

import com.inf.Medical.Records.System.data.Diagnosis;
import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diagnoses")
public class DiagnosisApiController {
    private final DiagnosisService diagnosisService;


    @GetMapping
    public List<Diagnosis> getDiagnoses() {
        return diagnosisService.getDiagnoses();
    }

    @GetMapping("/{id}")
    public Optional<Diagnosis> getDiagnosisById(@PathVariable long id) {
        return this.diagnosisService.getDiagnosisById(id);
    }

    @PostMapping
    public Diagnosis createDiagnosis(@RequestBody Diagnosis diagnosis) {
        return this.diagnosisService.createDiagnosis(diagnosis);
    }

    @PutMapping({"/{id}"})
    public Diagnosis updateDiagnosis(@RequestBody Diagnosis diagnosis,@PathVariable long id) {
        return this.diagnosisService.updateDiagnosis(diagnosis, id);
    }

    @DeleteMapping({"/{id}"})
    public void deleteDiagnosis(@PathVariable long id) {
        this.diagnosisService.deleteDiagnosis(id);
    }
}
