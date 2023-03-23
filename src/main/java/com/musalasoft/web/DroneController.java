package com.musalasoft.web;

import com.musalasoft.entity.Drone;
import com.musalasoft.entity.Medication;
import com.musalasoft.exception.DroneApiException;
import com.musalasoft.model.BatteryLevel;
import com.musalasoft.model.DroneRegistration;
import com.musalasoft.model.Error;
import com.musalasoft.model.MedicationLoader;
import com.musalasoft.service.DroneService;
import com.musalasoft.util.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/drone"})
public class DroneController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DroneController.class);

    @Autowired
    private DroneService droneService;

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDrone(@RequestBody DroneRegistration droneRegistration) {
        LOGGER.info("Request received to register a drone. [{}]", droneRegistration);
        droneService.registerDrone(droneRegistration);
    }

    @PutMapping(value = "{serialNumber}/load", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> loadDrone(@RequestBody MedicationLoader medicationLoader, @PathVariable String serialNumber) {
        LOGGER.info("Request received to load the drone. [{}]", serialNumber);
        try {
            Drone loadedDrone = droneService.loadDrone(serialNumber, medicationLoader);
            return new ResponseEntity<>(JsonConverter.toJson(loadedDrone), HttpStatus.OK);
        } catch (DroneApiException e) {
            Error error = new Error(e.getMessage(), e.getError().value());
            return new ResponseEntity<>(JsonConverter.toJson(error), e.getError());
        }
    }

    @GetMapping(value = "/{serialNumber}/medications", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getLoadedMedications(@PathVariable String serialNumber) {
        LOGGER.info("Request received to get the loaded medications for the drone. [{}]", serialNumber);
        try {
            List<Medication> medicationList = droneService.getMedications(serialNumber);
            return new ResponseEntity<>(JsonConverter.toJson(medicationList), HttpStatus.OK);
        } catch (DroneApiException e) {
            Error error = new Error(e.getMessage(), e.getError().value());
            return new ResponseEntity<>(JsonConverter.toJson(error), e.getError());
        }
    }

    @GetMapping(value = "/availableForLoading", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> fetchAvailableDronesForLoading() {
        LOGGER.info("Request received to fetch available drones for loading");
        try {
            List<Drone> droneList = droneService.getDronesAvailForLoading();
            return new ResponseEntity<>(JsonConverter.toJson(droneList), HttpStatus.OK);
        } catch (DroneApiException e) {
            Error error = new Error(e.getMessage(), e.getError().value());
            return new ResponseEntity<>(JsonConverter.toJson(error), e.getError());
        }
    }

    @GetMapping(value = "/{serialNumber}/checkBattery", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> checkBattery(@PathVariable String serialNumber) {
        LOGGER.info("Request received to check the battery capacity of the drone. [{}]", serialNumber);
        try {
            BatteryLevel batteryLevel = droneService.getBatteryLevel(serialNumber);
            return new ResponseEntity<>(JsonConverter.toJson(batteryLevel), HttpStatus.OK);
        } catch (DroneApiException e) {
            Error error = new Error(e.getMessage(), e.getError().value());
            return new ResponseEntity<>(JsonConverter.toJson(error), e.getError());
        }
    }

    @GetMapping(value = "")
    public List<Drone> getDrones() {
        return droneService.getDrones();
    }
}
