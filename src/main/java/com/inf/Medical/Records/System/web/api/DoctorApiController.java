package com.inf.Medical.Records.System.web.api;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorApiController {

    private final DoctorService doctorService;

    @GetMapping
    public List<Doctor> getDoctors() {
        return doctorService.getDoctors();
    }

    @GetMapping("/{id}")
    public Optional<Doctor> getDoctorById(@PathVariable long id) {
        return this.doctorService.getDoctorById(id);
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return this.doctorService.createDoctor(doctor);
    }

    @PutMapping({"/{id}"})
    public Doctor updateDoctor(@RequestBody Doctor doctor,@PathVariable long id) {
        return this.doctorService.updateDoctor(doctor, id);
    }

    @DeleteMapping({"/{id}"})
    public void deleteDoctor(@PathVariable long id) {
        this.doctorService.deleteDoctor(id);
    }
}
