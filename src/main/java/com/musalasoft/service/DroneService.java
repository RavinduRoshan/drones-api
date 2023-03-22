package com.musalasoft.service;

import com.musalasoft.entity.Drone;
import com.musalasoft.model.DroneRegistration;

import java.util.List;

public interface DroneService {

    void registerDrone(DroneRegistration droneRegistration);
    void loadDrone(String serialNumber, List<String> medicationCodes);

    List<Drone> getDrones();
}
