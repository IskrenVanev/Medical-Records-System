package com.inf.Medical.Records.System.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Patient extends BaseEntity {
    private String name;

    private String egn;

    private Boolean insurancePaid;

    @ManyToOne
    @JoinColumn(name = "general_practitioner_id", nullable = true)
    private Doctor generalPractitioner;//GP

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Visit> visits;
}
