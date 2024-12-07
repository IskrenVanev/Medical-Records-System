package com.inf.Medical.Records.System.repo;

import com.inf.Medical.Records.System.data.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findDoctorsByName(String name);

    List<Doctor> findDoctorsByNameStartsWith(String name);

    List<Doctor> findDoctorsByNameStartsWithAndGeneralPractitioner(String name, boolean GeneralPractitioner);
}
