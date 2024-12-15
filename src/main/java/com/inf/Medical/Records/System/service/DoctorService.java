package com.inf.Medical.Records.System.service;

import com.inf.Medical.Records.System.data.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    List<Doctor> getDoctors();

    Optional<Doctor> getDoctorById(long id);

    List<Doctor> getAllGeneralPractitioners();

    List<Doctor> getDoctorsByIds(List<Long> doctorIds);

    boolean hasPatients(long doctorId);

    Doctor createDoctor(Doctor doctor);

    Doctor updateDoctor(Doctor doctor, long id);

    void deleteDoctor(long id);

    List<Doctor> findDoctorsByName(String name);

}
