package com.inf.Medical.Records.System.repo;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SickLeaveRepository extends JpaRepository<SickLeave, Long> {
}
