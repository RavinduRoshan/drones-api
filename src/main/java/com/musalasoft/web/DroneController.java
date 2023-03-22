package com.musalasoft.web;

import com.musalasoft.entity.Drone;
import com.musalasoft.model.DroneRegistration;
import com.musalasoft.service.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PutMapping(value = "{serialNumber}/load")
    @ResponseStatus(HttpStatus.CREATED)
    public void loadDrone(@RequestBody List<String> medicationCodes, @PathVariable String serialNumber) {
        LOGGER.info("Request received to load the drone. [{}]", serialNumber);
        droneService.loadDrone(serialNumber, medicationCodes);
    }

    @GetMapping(value = "")
    public List<Drone> getDrones() {
        return droneService.getDrones();
    }
}
