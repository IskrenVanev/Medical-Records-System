package com.inf.Medical.Records.System.repo;

import com.inf.Medical.Records.System.data.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
