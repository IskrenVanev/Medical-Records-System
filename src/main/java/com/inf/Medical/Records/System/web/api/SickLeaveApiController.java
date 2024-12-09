package com.inf.Medical.Records.System.web.api;

import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.data.SickLeave;
import com.inf.Medical.Records.System.service.PatientService;
import com.inf.Medical.Records.System.service.SickLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sickLeaves")
public class SickLeaveApiController {
    private final SickLeaveService sickleaveService;


    @GetMapping
    public List<SickLeave> getSickLeaves() {
        return sickleaveService.getSickLeaves();
    }

    @GetMapping("/{id}")
    public Optional<SickLeave> getSickLeaveById(@PathVariable long id) {
        return this.sickleaveService.getSickLeaveById(id);
    }

    @PostMapping
    public SickLeave createSickLeave(@RequestBody SickLeave sickLeave) {
        return this.sickleaveService.createSickLeave(sickLeave);
    }

    @PutMapping({"/{id}"})
    public SickLeave updateSickLeave(@RequestBody SickLeave sickLeave,@PathVariable long id, LocalDate startDate) {
        return this.sickleaveService.updateSickLeave(sickLeave, id, startDate);
    }

    @DeleteMapping({"/{id}"})
    public void deleteSickLeave(@PathVariable long id) {
        this.sickleaveService.deleteSickLeave(id);
    }
}
