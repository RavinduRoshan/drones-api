package com.musalasoft.repository;

import com.musalasoft.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {

    Medication findByCode(String code);
}
