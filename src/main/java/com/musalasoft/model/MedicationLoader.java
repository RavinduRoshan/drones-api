package com.musalasoft.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public class MedicationLoader {
    private List<String> medicationCodes;
}
