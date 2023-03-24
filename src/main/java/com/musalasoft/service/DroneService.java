package com.musalasoft.service;

import com.musalasoft.entity.Drone;
import com.musalasoft.entity.Medication;
import com.musalasoft.exception.DroneApiException;
import com.musalasoft.model.BatteryLevel;
import com.musalasoft.model.DroneRegistration;
import com.musalasoft.model.MedicationList;

import java.util.List;

/**
 * This interface provides the functionalities related to the drone interactions
 */
public interface DroneService {

    /**
     * Registers the specified drone after validation
     *
     * @param droneRegistration Drone details
     * @return registered drone
     */
    Drone registerDrone(DroneRegistration droneRegistration);

    /**
     * Loads the drone with the provided medications
     *
     * @param serialNumber Serial Number of the drone to be loaded
     * @param medicationList Medications to load
     * @return Drone after loading medications
     * @throws DroneApiException DroneApiException
     */
    Drone loadDrone(String serialNumber, MedicationList medicationList) throws DroneApiException;

    /**
     * Fetches loaded medications of specified drone
     *
     * @param serialNumber Serial number of the drone
     * @return List of medications loaded
     * @throws DroneApiException DroneApiException
     */
    List<Medication> getMedications(String serialNumber) throws DroneApiException;

    /**
     * Fetches drones that are available for loading medications
     *
     * @return List of drones
     * @throws DroneApiException DroneApiException
     */
    List<Drone> getDronesAvailForLoading() throws DroneApiException;

    /**
     * Provides the battery level of the specified drone
     *
     * @param serialNumber Serial number of the drone to be check the battery level
     * @return Battery level
     * @throws DroneApiException DroneApiException
     */
    BatteryLevel getBatteryLevel(String serialNumber) throws DroneApiException;

    /**
     * Fetches all registered drones
     *
     * @return List of drones
     */
    List<Drone> getDrones();
}
