package com.inf.Medical.Records.System.service.impl;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.DoctorRepository;
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
}