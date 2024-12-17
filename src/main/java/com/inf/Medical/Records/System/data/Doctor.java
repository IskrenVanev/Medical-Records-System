package com.inf.Medical.Records.System.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Getter
@Setter
public class Doctor extends BaseEntity {

    private String name;

    private String specialties;

    private boolean generalPractitioner;

    @ManyToMany(mappedBy = "doctors")
    @JsonIgnore
    private List<Visit> visits;
}
