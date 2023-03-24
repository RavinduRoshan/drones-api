package com.musalasoft.service.impl;

import com.musalasoft.entity.Drone;
import com.musalasoft.entity.Medication;
import com.musalasoft.exception.DroneApiException;
import com.musalasoft.model.BatteryLevel;
import com.musalasoft.model.DroneRegistration;
import com.musalasoft.model.MedicationList;
import com.musalasoft.model.State;
import com.musalasoft.repository.DroneRepository;
import com.musalasoft.service.DroneService;
import com.musalasoft.service.MedicationService;
import com.musalasoft.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DroneServiceImpl implements DroneService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DroneServiceImpl.class);
    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationService medicationService;

    @Override
    public Drone registerDrone(DroneRegistration droneRegistration) {
        LOGGER.info("Ready to register a new drone. [{}]", droneRegistration);
        ValidationUtil.validateDrone(droneRegistration);
        checkExistence(droneRegistration.getSerialNumber());
        Drone drone = getNewDrone(droneRegistration);
        return droneRepository.save(drone);
    }

    @Override
    public Drone loadDrone(String serialNumber, MedicationList medicationList) throws DroneApiException {
        LOGGER.info("Ready to load the drone [{}] with medications [{}]", serialNumber, medicationList.getMedications());
        Drone drone = droneRepository.findDroneBySerialNumber(serialNumber);
        validateDrone(drone);
        validateBatteryCapacity(drone);
        validateMedications(medicationList);
        List<Medication> savedMedications = persistMedications(medicationList);
        drone = persistLoadingState(drone);
        return loadDroneWithMedications(savedMedications, drone);
    }

    @Override
    public List<Medication> getMedications(String serialNumber) throws DroneApiException {
        LOGGER.info("Ready to fetch the loaded medications of the drone [{}] ", serialNumber);
        Drone drone = droneRepository.findDroneBySerialNumber(serialNumber);
        validateDrone(drone);
        return drone.getMedications();
    }

    @Override
    public List<Drone> getDronesAvailForLoading() throws DroneApiException {
        LOGGER.info("Ready to fetch the drones available for loading");
        List<Drone> droneList = new ArrayList<>();
        for (Drone drone: droneRepository.findAll()) {
            float availCapacity = getAvailableWeightCapacity(drone);
            if (availCapacity > 0f && drone.getBatteryCapacity() >= 0.25f) {
                droneList.add(drone);
            }
        }
        return droneList;
    }

    @Override
    public BatteryLevel getBatteryLevel(String serialNumber) throws DroneApiException {
        LOGGER.info("Ready to fetch the battery capacity of the drone [{}]", serialNumber);
        Drone drone = droneRepository.findDroneBySerialNumber(serialNumber);
        validateDrone(drone);
        return new BatteryLevel(drone.getSerialNumber(), drone.getBatteryCapacity());
    }

    @Override
    public List<Drone> getDrones() {
        return droneRepository.findAll();
    }

    private Drone loadDroneWithMedications(List<Medication> medicationList, Drone drone) {
        List<String> loadedMedications = drone.getMedications().stream().map(Medication::getCode).collect(Collectors.toList());
        float availWeightCapacity = getAvailableWeightCapacity(drone);
        for (Medication medication : medicationList) {
            checkAlreadyLoaded(loadedMedications, medication);
            checkAvailableWeight(availWeightCapacity, medication);
            drone.getMedications().add(medication);
            availWeightCapacity -= medication.getWeight();
        }
        drone.setState(State.LOADED);
        return droneRepository.save(drone);
    }

    private Drone getNewDrone(DroneRegistration droneRegistration) {
        return new Drone(droneRegistration.getSerialNumber(), droneRegistration.getModel(), droneRegistration.getWeightLimit(),
                droneRegistration.getBatteryCapacity(), droneRegistration.getState());
    }

    private float getAvailableWeightCapacity(Drone drone) {
        float availWeightCapacity = drone.getWeightLimit();
        for (Medication medication: drone.getMedications()) {
            availWeightCapacity -= medication.getWeight();
        }
        return availWeightCapacity;
    }

    private Drone persistLoadingState(Drone drone) {
        LOGGER.info("Setting LOADING state for the drone: [{}]", drone.getSerialNumber());
        drone.setState(State.LOADING);
        return droneRepository.save(drone);
    }

    private void checkExistence(String serialNumber) {
        if (droneRepository.findDroneBySerialNumber(serialNumber) != null) {
            LOGGER.error("Drone is already available. drone: [{}]", serialNumber);
            throw new DroneApiException(HttpStatus.BAD_REQUEST, "Drone is already available..");
        }
    }

    private void checkMedicationExistence(String code) {
        if (medicationService.findByCode(code) != null) {
            LOGGER.error("Medication is already available. code: [{}]", code);
            throw new DroneApiException(HttpStatus.BAD_REQUEST, "Medication is already available. code: " + code);
        }
    }

    private void checkAlreadyLoaded(List<String> loadedMedications, Medication medication) {
        String code = medication.getCode();
        if (loadedMedications.contains(code)) {
            LOGGER.error("Medication is already loaded to the drone. code: [{}]", code);
            throw new DroneApiException(HttpStatus.BAD_REQUEST, "Medication is already loaded to the drone. code: " + code);
        }
    }

    private void checkAvailableWeight(float availWeightCapacity, Medication medication) {
        if (availWeightCapacity < medication.getWeight()) {
            LOGGER.error("Ignore loading medication [{}] due to exceeding drone weight limit.", medication.getCode());
            throw new DroneApiException(HttpStatus.BAD_REQUEST, "Ignore loading due to exceeding drone weight limit.. code: " + medication.getCode());
        }
    }

    private void validateMedications(MedicationList medicationList) {
        LOGGER.info("Validating the medications info..");
        for (Medication medication: medicationList.getMedications()) {
            ValidationUtil.validateMedication(medication);
            checkMedicationExistence(medication.getCode());
        }
    }

    private List<Medication> persistMedications(MedicationList medicationList) {
        return medicationService.saveAll(medicationList.getMedications());
    }

    private void validateBatteryCapacity(Drone drone) {
        if (drone.getBatteryCapacity() < 0.25f) {
            LOGGER.error("Battery capacity is less than 25% of drone: [{}] battery: [{}]", drone.getSerialNumber(), drone.getBatteryCapacity());
            throw new DroneApiException(HttpStatus.PRECONDITION_FAILED, "Preventing from loading. Battery capacity is less than 25%");
        }
    }

    private void validateDrone(Drone drone) throws DroneApiException {
        if (drone == null) {
            LOGGER.error("Drone cannot be found.");
            throw new DroneApiException(HttpStatus.BAD_REQUEST, "Drone cannot be found.");
        }
    }
}
