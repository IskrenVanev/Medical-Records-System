package com.inf.Medical.Records.System.web.view;

import com.inf.Medical.Records.System.data.Diagnosis;
import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.service.DiagnosisService;
import com.inf.Medical.Records.System.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/doctors")
public class DoctorViewController {
    private final DoctorService doctorService;

    @GetMapping
    public String getDoctors(Model model) {
        List<Doctor> doctors = this.doctorService.getDoctors();

        model.addAttribute("doctors", doctors);
        return "/doctors/doctors"; // Renders doctors list view
    }

    // Show form to create a new diagnosis
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctors/create-doctor"; // Renders the form for creating a doctor
    }

    @PostMapping
    public String createDoctor(Doctor doctor) {
        doctorService.createDoctor(doctor);
        return "redirect:/view/doctors"; // Redirect to the list view
    }

    @GetMapping("/{id}/edit")
    public String showEditDoctorForm(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id) // Assuming this returns an Optional
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID: " + id));
        model.addAttribute("doctor", doctor);
        return "doctors/edit-doctor"; // Thymeleaf template name
    }

    // Handle the update form submission
    @PostMapping("/{id}/edit")
    public String updateDoctor(@PathVariable long id, @ModelAttribute Doctor doctor) {
        System.out.println("Updating doctor with ID: " + id);
        doctorService.updateDoctor(doctor, id); // Update the doctor
        return "redirect:/view/doctors"; // Redirect to the doctors list page
    }


    // Delete a diagnosis
    @PostMapping("/{id}/delete")
    public String deleteDoctor(@PathVariable long id, RedirectAttributes redirectAttributes) {
        if (doctorService.hasPatients(id)) {
            // Add a warning message to display on the page
            redirectAttributes.addFlashAttribute("warning", "Cannot delete the doctor. There are patients assigned to this GP.");
            return "redirect:/view/doctors"; // Redirect back to the list view
        }

        doctorService.deleteDoctor(id);
        redirectAttributes.addFlashAttribute("success", "Doctor deleted successfully.");
        return "redirect:/view/doctors"; // Redirect to the list view
    }
}
