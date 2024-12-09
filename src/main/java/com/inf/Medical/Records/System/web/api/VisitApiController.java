package com.inf.Medical.Records.System.web.api;

import com.inf.Medical.Records.System.data.SickLeave;
import com.inf.Medical.Records.System.data.Visit;
import com.inf.Medical.Records.System.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitApiController {
    private final VisitService visitService;

    @GetMapping
    public List<Visit> getVisits() {
        return visitService.getVisits();
    }

    @GetMapping("/{id}")
    public Optional<Visit> getVisits(@PathVariable long id) {
        return this.visitService.getVisitById(id);
    }

    @PostMapping
    public Visit createVisit(@RequestBody Visit visit) {
        return this.visitService.createVisit(visit);
    }

    @PutMapping({"/{id}"})
    public Visit updateSickLeave(@RequestBody Visit visit,@PathVariable long id, LocalDate startDate) {
        return this.visitService.updateVisit(visit, id, startDate);
    }

    @DeleteMapping({"/{id}"})
    public void deleteVisit(@PathVariable long id) {
        this.visitService.deleteVisit(id);
    }
}
