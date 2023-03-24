package com.musalasoft.model;

import com.musalasoft.entity.Medication;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public class MedicationList implements Serializable {
    private List<Medication> medications;
}
