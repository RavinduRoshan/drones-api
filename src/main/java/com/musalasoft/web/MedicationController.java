package com.musalasoft.web;

import com.musalasoft.entity.Drone;
import com.musalasoft.entity.Medication;
import com.musalasoft.service.MedicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/medication"})
public class MedicationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicationController.class);

    @Autowired
    private MedicationService medicationService;

    @GetMapping(value = "")
    public List<Medication> getDrones() {
        return medicationService.getAllMedications();
    }
}