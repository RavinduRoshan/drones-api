package com.musalasoft.service.impl;

import com.musalasoft.entity.Medication;
import com.musalasoft.repository.MedicationRepository;
import com.musalasoft.service.MedicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicationServiceImpl.class);

    @Autowired
    private MedicationRepository medicationRepository;

    @Override
    public List<Medication> saveAll(List<Medication> medications) {
        return medicationRepository.saveAll(medications);
    }

    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    @Override
    public Medication findByCode(String code) {
        return medicationRepository.findByCode(code);
    }
}
