package com.inf.Medical.Records.System.web.view;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.service.DoctorService;
import com.inf.Medical.Records.System.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/patients")
public class PatientViewController {
    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping
    public String getPatients(Model model) {
        List<Patient> patients = this.patientService.getPatients();
        model.addAttribute("patients", patients);
        return "/patients/patients"; // Renders patients list view
    }

    // Show form to create a new diagnosis
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("patient", new Patient());

        // Fetch all general practitioners from the doctor service
        List<Doctor> generalPractitioners = doctorService.getAllGeneralPractitioners();

        // Add the list of general practitioners to the model
        model.addAttribute("generalPractitioners", generalPractitioners);

        return "patients/create-patient"; // Render the form for creating a patient
    }

    @PostMapping
    public String createPatient(@ModelAttribute Patient patient, @RequestParam("generalPractitionerId") Long gpId) {
        // Resolve the GP from the database if an ID is provided
        if (gpId != null) {
            Doctor generalPractitioner = doctorService.getDoctorById(gpId)
                    .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + gpId));
            patient.setGeneralPractitioner(generalPractitioner);
        }

        // Save the patient
        if (patient != null) {
            System.out.println("Insurance Paid: " + patient.isInsurancePaid());
            if (patient.getGeneralPractitioner() != null) {
                System.out.println("GP ID: " + patient.getGeneralPractitioner().getId());
            } else {
                System.out.println("GP is null");
            }
        } else {
            System.out.println("Patient object is null");
        }

        patientService.createPatient(patient);
        return "redirect:/view/patients"; // Redirect to the list view
    }


    @GetMapping("/{id}/edit")
    public String showEditPatientForm(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id) // Assuming this returns an Optional
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient ID: " + id));
        model.addAttribute("patient", patient);
        return "patients/edit-patient"; // Thymeleaf template name
    }

    // Handle the update form submission
    @PostMapping("/{id}/edit")
    public String updatePatient(@PathVariable long id, @ModelAttribute Patient patient) {
        System.out.println("Updating patient with ID: " + id);
        patientService.updatePatient(patient, id); // Update the doctor
        return "redirect:/view/patients"; // Redirect to the doctors list page
    }


    // Delete a diagnosis
    @PostMapping("/{id}/delete")
    public String deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
        return "redirect:/view/patients"; // Redirect to the list view
    }
}
