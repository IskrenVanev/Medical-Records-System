package com.inf.Medical.Records.System.data;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Visit extends BaseEntity {
    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Diagnosis diagnosis;

    private LocalDate visitDate;

    private String prescribedTreatment;

    @OneToOne(mappedBy = "visit", cascade = CascadeType.ALL)
    private SickLeave sickLeave;
}
