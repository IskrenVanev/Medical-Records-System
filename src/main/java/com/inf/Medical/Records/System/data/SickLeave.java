package com.inf.Medical.Records.System.data;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class SickLeave extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    private LocalDate startDate;

    private int durationInDays;
}
