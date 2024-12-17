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
    public String createPatient(@ModelAttribute Patient patient,
                                @RequestParam(value = "generalPractitionerId", required = false) Long gpId) {
        // Resolve the GP from the database if an ID is provided
        if (gpId != null) {
            Doctor generalPractitioner = doctorService.getDoctorById(gpId)
                    .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + gpId));
            patient.setGeneralPractitioner(generalPractitioner);
        }

        if (patient.getInsurancePaid() == null) {
            patient.setInsurancePaid(false);
        }
        patientService.createPatient(patient);
        return "redirect:/view/patients"; // Redirect to the list view
    }


    @GetMapping("/{id}/edit")
    public String showEditPatientForm(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id) // Assuming this returns an Optional
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient ID: " + id));
        List<Doctor> generalPractitioners = doctorService.getAllGeneralPractitioners();

        model.addAttribute("patient", patient);
        model.addAttribute("generalPractitioners", generalPractitioners);

        return "patients/edit-patient"; // Thymeleaf template name
    }

    // Handle the update form submission
    @PostMapping("/{id}/edit")
    public String updatePatient(@PathVariable long id, @ModelAttribute Patient patient, @RequestParam(required = false) Long generalPractitionerId) {
        System.out.println("Updating patient with ID: " + id);

        // Fetch the patient from the database
        Patient existingPatient = patientService.getPatientById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient ID: " + id));

        // Set the patient's basic properties (name, insurancePaid, etc.)
        existingPatient.setName(patient.getName());
        existingPatient.setInsurancePaid(patient.getInsurancePaid());
        existingPatient.setVisits(patient.getVisits());

        // If a general practitioner ID was provided, fetch the Doctor and set it
        if (generalPractitionerId != null) {
            Doctor generalPractitioner = doctorService.getDoctorById(generalPractitionerId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid general practitioner ID: " + generalPractitionerId));
            existingPatient.setGeneralPractitioner(generalPractitioner);
        } else {
            // If no GP is selected, set it to null (or some default value if needed)
            existingPatient.setGeneralPractitioner(null);
        }

        // Pass the patient and ID to the service for updating
        patientService.updatePatient(existingPatient, id);

        // Redirect to the patients list page
        return "redirect:/view/patients";
    }



    // Delete a diagnosis
    @PostMapping("/{id}/delete")
    public String deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
        return "redirect:/view/patients"; // Redirect to the list view
    }
}
