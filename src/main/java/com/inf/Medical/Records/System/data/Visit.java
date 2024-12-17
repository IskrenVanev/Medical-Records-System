package com.inf.Medical.Records.System.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Visit extends BaseEntity {
    @ManyToOne
    private Patient patient;

    @ManyToMany
    @JoinTable(
            name = "visit_doctor",
            joinColumns = @JoinColumn(name = "visit_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<Doctor> doctors;

    @ManyToMany
    @JoinTable(
            name = "visit_diagnosis",
            joinColumns = @JoinColumn(name = "visit_id"),
            inverseJoinColumns = @JoinColumn(name = "diagnosis_id")
    )
    private List<Diagnosis> diagnosis;

    private LocalDate visitDate;

    private String prescribedTreatment;

    @OneToOne(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
    private SickLeave sickLeave;
}
