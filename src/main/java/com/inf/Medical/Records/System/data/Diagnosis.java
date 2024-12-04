package com.inf.Medical.Records.System.data;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Diagnosis extends BaseEntity{
    private String name;

    @OneToMany(mappedBy = "diagnosis")
    private List<Visit> visits;
}
