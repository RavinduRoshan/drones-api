package com.musalasoft.service;

import com.musalasoft.entity.Medication;

import java.util.List;

public interface MedicationService {
    List<Medication> saveAll(List<Medication> medications);

    List<Medication> getAllMedications();

    Medication findByCode(String code);
}
