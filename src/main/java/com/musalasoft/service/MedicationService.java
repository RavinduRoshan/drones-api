package com.musalasoft.service;

import com.musalasoft.entity.Medication;

import java.util.List;

public interface MedicationService {

    List<Medication> getAllMedications();

    Medication findByCode(String code);
}
