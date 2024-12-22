package com.inf.Medical.Records.System.repo;

import com.inf.Medical.Records.System.data.Doctor;
import com.inf.Medical.Records.System.data.Patient;
import com.inf.Medical.Records.System.data.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    @Query("SELECT d.name, COUNT(v) FROM Visit v JOIN v.doctors d GROUP BY d.name")
    List<Object[]> findVisitCountsByDoctor();

    @Query("SELECT p.name, v.visitDate, d.name FROM Visit v " +
            "JOIN v.patient p " +
            "JOIN v.doctors d " +
            "ORDER BY p.name, v.visitDate")
    List<Object[]> findVisitsByPatient();

    @Query("SELECT d.name, v.visitDate, p.name FROM Visit v " +
            "JOIN v.doctors d " +
            "JOIN v.patient p " +
            "WHERE v.visitDate BETWEEN :startDate AND :endDate " +
            "ORDER BY d.name, v.visitDate")
    List<Object[]> findVisitsByDoctorsWithinPeriod(@Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

    @Query("SELECT v FROM Visit v JOIN v.doctors d WHERE d.id = :doctorId AND v.visitDate BETWEEN :startDate AND :endDate")
    List<Visit> findVisitsByDoctorAndPeriod(@Param("doctorId") Long doctorId,
                                            @Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);
    }
