package com.inf.Medical.Records.System.service;

import com.inf.Medical.Records.System.data.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    List<Doctor> getDoctors();

    Optional<Doctor> getDoctorById(long id);

    Doctor createDoctor(Doctor doctor);

    Doctor updateDoctor(Doctor doctor, long id);

    void deleteDoctor(long id);
}
