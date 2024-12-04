package com.inf.Medical.Records.System.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Doctor extends BaseEntity {

    private String name;
    @ElementCollection
    private List<String> specialties;

    private boolean isGeneralPractitioner;

    @OneToMany(mappedBy = "doctor")
    private List<Visit> visits;
}
