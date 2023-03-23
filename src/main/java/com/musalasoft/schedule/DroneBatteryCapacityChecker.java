package com.musalasoft.schedule;

import com.musalasoft.entity.Drone;
import com.musalasoft.entity.DroneBatteryAudit;
import com.musalasoft.repository.DroneBatteryAuditRepository;
import com.musalasoft.repository.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DroneBatteryCapacityChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(DroneBatteryCapacityChecker.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

    @Autowired
    private DroneRepository droneRepository;
    @Autowired
    private DroneBatteryAuditRepository droneBatteryAuditRepository;

    @Scheduled(cron = "${scheduled.audit.cron}")
    public void execute() {
        LOGGER.info("Checking the battery level for available drones... Time: {}", formatter.format(LocalDateTime.now()));
        for (Drone drone: droneRepository.findAll()) {
            droneBatteryAuditRepository.save(new DroneBatteryAudit(drone.getSerialNumber(), drone.getBatteryCapacity()));
            LOGGER.info("Saved battery capacity: [{}] of drone: [{}] in audit", drone.getBatteryCapacity(), drone.getSerialNumber());
        }
    }
}
