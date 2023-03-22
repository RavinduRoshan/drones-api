package com.musalasoft.service.impl;

import com.musalasoft.entity.Drone;
import com.musalasoft.entity.Medication;
import com.musalasoft.model.DroneRegistration;
import com.musalasoft.repository.DroneRepository;
import com.musalasoft.service.DroneService;
import com.musalasoft.service.MedicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void loadDrone(String serialNumber, List<String> medicationCodes) {
        LOGGER.info("Ready to load the drone [{}] with medications [{}]", serialNumber, medicationCodes);
        Drone drone = droneRepository.findDroneBySerialNumber(serialNumber);
        List<String> loadedMedications = drone.getMedications().stream().map(Medication::getCode).collect(Collectors.toList());
        float weight = drone.getWeight();
        if (drone != null) {
            for (String code: medicationCodes) {
                Medication medication = medicationService.findByCode(code);
                if (medication != null && !loadedMedications.contains(code) && weight >= medication.getWeight()) {
                    drone.getMedications().add(medication);
                    weight -= medication.getWeight();
                }
            }
            droneRepository.save(drone);
        }
    }

    @Override
    public List<Drone> getDrones() {
        return droneRepository.findAll();
    }

    private Drone getNewDrone(DroneRegistration droneRegistration) {
        return new Drone(droneRegistration.getSerialNumber(), droneRegistration.getModel(), droneRegistration.getWeight(),
                droneRegistration.getBatteryCapacity(), droneRegistration.getState());
//        drone.setSerialNumber(droneRegistration.getSerialNumber());
//        drone.setModel(droneRegistration.getModel());
//        drone.setWeight(droneRegistration.getWeight());
//        drone.setBatteryCapacity(droneRegistration.getBatteryCapacity());
//        drone.setState(droneRegistration.getState());
//        return drone;
    }
}
