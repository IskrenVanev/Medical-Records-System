package com.inf.Medical.Records.System.web.view;

import com.inf.Medical.Records.System.data.Diagnosis;
import com.inf.Medical.Records.System.data.SickLeave;
import com.inf.Medical.Records.System.data.Visit;
import com.inf.Medical.Records.System.service.DiagnosisService;
import com.inf.Medical.Records.System.service.SickLeaveService;
import com.inf.Medical.Records.System.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/sickLeaves")
public class SickLeaveViewController {
    private final SickLeaveService sickLeaveService;
    private final VisitService visitService;
    @GetMapping
    public String getSickLeaves(Model model) {
        model.addAttribute("sickLeaves", this.sickLeaveService.getSickLeaves());
        return "/sickLeaves/sickLeaves";
    }


//    @GetMapping("/create")
//    public String showCreateForm(Model model) {
//        model.addAttribute("sickLeave", new SickLeave());
//        return "sickLeaves/create-sickLeave";
//    }
//
//    @PostMapping("/create")
//    public String createSickLeave(SickLeave sickLeave, @RequestParam("visitId") Long visitId) {
//        // Fetch the Visit entity using its ID, and handle Optional
//        Visit visit = visitService.getVisitById(visitId)
//                .orElseThrow(() -> new IllegalArgumentException("Visit not found"));
//
//        // Set the visit in the SickLeave entity
//        sickLeave.setVisit(visit);
//
//        // Save the SickLeave
//        sickLeaveService.createSickLeave(sickLeave);
//
//        return "redirect:/view/sickLeaves"; // Redirect to the list view
//    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("sickLeave", sickLeaveService.getSickLeaveById(id).orElseThrow(() ->
                new IllegalArgumentException("Sick Leave not found")));
        return "sickLeaves/edit-sickLeave";
    }

    // Handle the update form submission
    @PostMapping("/{id}/edit")
    public String updateSickLeave(@PathVariable long id, @ModelAttribute SickLeave sickLeave, LocalDate startDate) {
        sickLeaveService.updateSickLeave(sickLeave, id, startDate);
        return "redirect:/view/sickLeaves";
    }



    @PostMapping("/{id}/delete")
    public String deleteSickLeave(@PathVariable long id) {
        sickLeaveService.deleteSickLeave(id);
        return "redirect:/view/sickLeaves"; // Redirect to the list view
    }
}
