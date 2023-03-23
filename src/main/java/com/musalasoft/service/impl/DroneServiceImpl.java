package com.musalasoft.service.impl;

import com.musalasoft.entity.Drone;
import com.musalasoft.entity.Medication;
import com.musalasoft.exception.DroneApiException;
import com.musalasoft.model.DroneRegistration;
import com.musalasoft.model.MedicationLoader;
import com.musalasoft.model.State;
import com.musalasoft.repository.DroneRepository;
import com.musalasoft.service.DroneService;
import com.musalasoft.service.MedicationService;
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
    public void registerDrone(DroneRegistration droneRegistration) {
        LOGGER.info("Ready to register a new drone. [{}]", droneRegistration);
        Drone drone = getNewDrone(droneRegistration);
        droneRepository.save(drone);
        LOGGER.info("Successfully registered the drone. [{}]", droneRegistration);
    }

    @Override
    public Drone loadDrone(String serialNumber, MedicationLoader medicationLoader) throws DroneApiException {
        LOGGER.info("Ready to load the drone [{}] with medications [{}]", serialNumber, medicationLoader.getMedicationCodes());
        Drone drone = droneRepository.findDroneBySerialNumber(serialNumber);
        validateDrone(drone);
        validateBatteryCapacity(drone);
        drone = persistLoadingState(drone);
        return loadDroneWithMedications(medicationLoader, drone);
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

    private Drone loadDroneWithMedications(MedicationLoader medicationLoader, Drone drone) {
        List<String> loadedMedications = drone.getMedications().stream().map(Medication::getCode).collect(Collectors.toList());
        float availWeightCapacity = getAvailableWeightCapacity(drone);
        for (String code : medicationLoader.getMedicationCodes()) {
            Medication medication = medicationService.findByCode(code);
            if (medication != null && !loadedMedications.contains(code)) {
                if (availWeightCapacity >= medication.getWeight()) {
                    drone.getMedications().add(medication);
                    availWeightCapacity -= medication.getWeight();
                } else {
                    LOGGER.error("Ignore loading medication [{}] due to exceeding drone weight limit..", medication.getCode());
                }
            } else {
                LOGGER.error("Invalid medication to load the drone [{}]", drone.getSerialNumber());
            }
        }
        drone.setState(State.LOADED);
        return droneRepository.save(drone);
    }

    @Override
    public List<Drone> getDrones() {
        return droneRepository.findAll();
    }

    private Drone getNewDrone(DroneRegistration droneRegistration) {
        return new Drone(droneRegistration.getSerialNumber(), droneRegistration.getModel(), droneRegistration.getWeightLimit(),
                droneRegistration.getBatteryCapacity(), droneRegistration.getState());
//        drone.setSerialNumber(droneRegistration.getSerialNumber());
//        drone.setModel(droneRegistration.getModel());
//        drone.setWeight(droneRegistration.getWeight());
//        drone.setBatteryCapacity(droneRegistration.getBatteryCapacity());
//        drone.setState(droneRegistration.getState());
//        return drone;
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
