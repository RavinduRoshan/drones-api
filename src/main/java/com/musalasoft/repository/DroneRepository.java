package com.musalasoft.repository;

import com.musalasoft.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {

    Drone findDroneBySerialNumber(String serialNumber);
}
