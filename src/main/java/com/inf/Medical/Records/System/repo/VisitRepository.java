package com.inf.Medical.Records.System.repo;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
