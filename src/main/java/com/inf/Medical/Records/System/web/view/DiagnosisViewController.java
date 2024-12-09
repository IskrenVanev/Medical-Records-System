package com.inf.Medical.Records.System.web.view;

import com.inf.Medical.Records.System.data.Diagnosis;
import org.springframework.ui.Model;
import com.inf.Medical.Records.System.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/diagnoses")
public class DiagnosisViewController {
    private final DiagnosisService diagnosisService;

    @GetMapping
    public String getDiagnoses(Model model) {
        model.addAttribute("diagnoses", this.diagnosisService.getDiagnoses());
        return "/diagnoses/diagnoses.html"; // Renders diagnoses list view
    }

    // Show form to create a new diagnosis
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("diagnosis", new Diagnosis());
        return "diagnoses/create-diagnosis"; // Renders the form for creating a diagnosis
    }

    @PostMapping
    public String createDiagnosis(Diagnosis diagnosis) {
        diagnosisService.createDiagnosis(diagnosis);
        return "redirect:/diagnoses"; // Redirect to the list view
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("diagnosis", diagnosisService.getDiagnosisById(id).orElseThrow(() ->
                new IllegalArgumentException("Diagnosis not found")));
        return "diagnoses/edit"; // Renders the form for editing a diagnosis
    }

    // Handle form submission to update a diagnosis
    @PostMapping("/{id}/edit")
    public String updateDiagnosis(@PathVariable long id, @ModelAttribute Diagnosis diagnosis) {
        diagnosisService.updateDiagnosis(diagnosis, id);
        return "redirect:/diagnoses"; // Redirect to the list view
    }

    // Delete a diagnosis
    @PostMapping("/{id}/delete")
    public String deleteDiagnosis(@PathVariable long id) {
        diagnosisService.deleteDiagnosis(id);
        return "redirect:/diagnoses"; // Redirect to the list view
    }
}
