package com.inf.Medical.Records.System.service.impl;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.repo.DoctorRepository;
import com.inf.Medical.Records.System.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> getDoctors() {
        return this.doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> getDoctorById(long id) {
        return this.doctorRepository.findById(id);
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        return this.doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor, long id) {
        return this.doctorRepository.findById(id)
                .map(existingDoctor -> {
                    existingDoctor.setName(doctor.getName()); // if she gets married haha
                    existingDoctor.setSpecialties(doctor.getSpecialties());
                    existingDoctor.setGeneralPractitioner(doctor.isGeneralPractitioner());
                    return this.doctorRepository.save(existingDoctor);
                })
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + id));
    }

    @Override
    public void deleteDoctor(long id) {
        this.doctorRepository.deleteById(id);
    }

    @Override
    public List<Doctor> findDoctorsByName(String name) {
        return this.doctorRepository.findDoctorsByName(name);
    }

}
