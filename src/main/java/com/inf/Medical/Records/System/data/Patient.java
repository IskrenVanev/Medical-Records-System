package com.inf.Medical.Records.System.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Patient extends BaseEntity{
    private String name;

    private String egn;

    private boolean isInsurancePaid;

    @ManyToOne
    private Doctor generalPractitioner;//GP

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Visit> visits;
}
