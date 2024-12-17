package com.inf.Medical.Records.System.web.view;

import com.inf.Medical.Records.System.data.*;
import com.inf.Medical.Records.System.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/visits")
public class VisitViewController {

    private final VisitService visitService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DiagnosisService diagnosisService;

    // Display all visits
    @GetMapping
    public String getVisits(Model model) {
        model.addAttribute("visits", visitService.getVisits());
        return "visits/visits"; // Template to display all visits
    }

    // Show form to create a new visit
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("visit", new Visit()); // Pass empty Visit object for binding
        model.addAttribute("patients", patientService.getPatients()); // List of patients
        model.addAttribute("doctors", doctorService.getDoctors()); // List of doctors
        model.addAttribute("diagnoses", diagnosisService.getDiagnoses());
        return "visits/create-visit"; // Template for creating a visit
    }

    // Handle visit creation with optional sick leave
    @PostMapping("/create")
    public String createVisit(
            @ModelAttribute Visit visit,
            @RequestParam("patientId") Long patientId,
            @RequestParam("doctorId") List<Long> doctorIds,
            @RequestParam("diagnosisId") List<Long> diagnosisIds,
            @RequestParam(required = false) boolean hasSickLeave,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) Integer durationInDays) {

        // Fetch entities from the database
        Patient patient = patientService.getPatientById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        List<Diagnosis> diagnoses = diagnosisService.getDiagnosesByIds(diagnosisIds);
        // Fetch the list of doctors by their IDs
        List<Doctor> doctors = doctorService.getDoctorsByIds(doctorIds);

        // Set the fetched entities into the visit object
        visit.setPatient(patient);
        visit.setDoctors(doctors);
        visit.setDiagnosis(diagnoses);

        // If sick leave data is provided, populate it
        if (hasSickLeave) {
            SickLeave sickLeave = new SickLeave();
            sickLeave.setStartDate(startDate);
            sickLeave.setDurationInDays(durationInDays);
            sickLeave.setVisit(visit); // Associate SickLeave with Visit
            visit.setSickLeave(sickLeave); // If bidirectional, associate Visit with SickLeave
        }

        // Save the visit
        visitService.createVisit(visit);

        // Redirect to the visits list
        return "redirect:/view/visits";
    }

    // Show form to edit an existing visit
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Visit visit = visitService.getVisitById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found"));

        // Add the visit to the model
        model.addAttribute("visit", visit);

        // Add doctors and diagnoses to the model for selection in the form
        model.addAttribute("doctors", doctorService.getDoctors());  // Assuming you have a method to get doctors
        model.addAttribute("diagnoses", diagnosisService.getDiagnoses());  // Assuming you have a method to get diagnoses

        return "visits/edit-visit"; // Template for editing a visit
    }

    @PostMapping("/{id}/edit")
    public String updateVisit(@PathVariable Long id,
                              @ModelAttribute Visit updatedVisit,
                              @RequestParam(required = false) LocalDate startDate,
                              @RequestParam(required = false) Integer durationInDays,
                              @RequestParam(required = false) boolean hasSickLeave) {

        // Fetch the existing visit from the database
        Visit existingVisit = visitService.getVisitById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found"));

        // Update fields of the existing visit while preserving relationships
        existingVisit.setVisitDate(updatedVisit.getVisitDate());
        existingVisit.setPrescribedTreatment(updatedVisit.getPrescribedTreatment());
        existingVisit.setDoctors(updatedVisit.getDoctors());
        // Update additional fields from the updatedVisit if needed
        existingVisit.setDiagnosis(updatedVisit.getDiagnosis());

        // Handle sick leave
        if (hasSickLeave) {
            SickLeave sickLeave = existingVisit.getSickLeave();
            if (sickLeave == null) {
                sickLeave = new SickLeave();
            }
            sickLeave.setStartDate(startDate);
            sickLeave.setDurationInDays(durationInDays);
            sickLeave.setVisit(existingVisit); // Associate sick leave with the visit
            existingVisit.setSickLeave(sickLeave);
            visitService.saveSickLeave(sickLeave); // Save sick leave
        } else {
            existingVisit.setSickLeave(null);
        }

        // Save the updated visit
        visitService.createVisit(existingVisit);

        return "redirect:/view/visits";
    }






    // Delete a visit (and its associated sick leave if any)
    @PostMapping("/{id}/delete")
    public String deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return "redirect:/view/visits"; // Redirect to the visits list
    }
}
