package com.inf.Medical.Records.System.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Diagnosis extends BaseEntity{
    private String name;

    @ManyToMany(mappedBy = "diagnosis")
    @JsonIgnore
    private List<Visit> visits;
}
