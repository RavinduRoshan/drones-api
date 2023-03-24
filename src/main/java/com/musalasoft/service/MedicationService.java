package com.musalasoft.service;

import com.musalasoft.entity.Medication;

import java.util.List;

/**
 * This interface provides the functionalities related to the medication interactions
 */
public interface MedicationService {
    /**
     * Save the specified medications prior to loading
     *
     * @param medications List of medications
     * @return saved medications
     */
    List<Medication> saveAll(List<Medication> medications);

    /**
     * Fetches all saved medications
     *
     * @return List of medications
     */
    List<Medication> getAllMedications();

    /**
     * Fetches the medication by specified medication code
     *
     * @param code Medication code
     * @return Medication
     */
    Medication findByCode(String code);
}
