package com.musalasoft.web;

import com.musalasoft.entity.Medication;
import com.musalasoft.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/medication"})
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @GetMapping(value = "")
    public List<Medication> getMedications() {
        return medicationService.getAllMedications();
    }
}
