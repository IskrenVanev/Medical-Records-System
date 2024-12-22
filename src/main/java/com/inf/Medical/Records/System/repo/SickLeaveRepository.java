package com.inf.Medical.Records.System.repo;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SickLeaveRepository extends JpaRepository<SickLeave, Long> {
    @Query("SELECT MONTH(sl.startDate) AS month, COUNT(sl) AS total " +
            "FROM SickLeave sl " +
            "GROUP BY MONTH(sl.startDate) " +
            "ORDER BY total DESC")
    List<Object[]> findMonthWithMostSickLeaves();


}
