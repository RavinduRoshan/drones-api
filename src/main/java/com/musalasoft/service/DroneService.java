package com.musalasoft.service;

import com.musalasoft.entity.Drone;
import com.musalasoft.entity.Medication;
import com.musalasoft.exception.DroneApiException;
import com.musalasoft.model.BatteryLevel;
import com.musalasoft.model.DroneRegistration;
import com.musalasoft.model.MedicationLoader;

import java.util.List;

public interface DroneService {

    void registerDrone(DroneRegistration droneRegistration);

    Drone loadDrone(String serialNumber, MedicationLoader medicationLoader) throws DroneApiException;

    List<Medication> getMedications(String serialNumber) throws DroneApiException;

    List<Drone> getDronesAvailForLoading() throws DroneApiException;

    BatteryLevel getBatteryLevel(String serialNumber) throws DroneApiException;

    List<Drone> getDrones();
}
