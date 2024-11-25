package com.inf.Medical.Records.System.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Doctor {
    @Id
    private long id;
    private String name;
}
