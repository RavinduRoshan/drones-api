package com.musalasoft.util;

import com.musalasoft.exception.DroneApiException;
import com.musalasoft.model.DroneRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class ValidationUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtil.class);
    public static void validateDrone(DroneRegistration droneRegistration) {
        validateEmptySerialNumber(droneRegistration);
        validateSerialNumberLength(droneRegistration);
        validateWeight(droneRegistration);
        validateBattery(droneRegistration);
    }

    private static void validateEmptySerialNumber(DroneRegistration droneRegistration) {
        if (droneRegistration.getSerialNumber() == null) {
            LOGGER.error("Serial number is not provided.");
            throw new DroneApiException(HttpStatus.BAD_REQUEST, "Mandatory field serialNumber is missing.");
        }
    }

    private static void validateSerialNumberLength(DroneRegistration droneRegistration) {
        if (droneRegistration.getSerialNumber().length() > 100) {
            LOGGER.error("Serial number length exceeds character limit.");
            throw new DroneApiException(HttpStatus.BAD_REQUEST, "Serial number character limit should not be exceeded 100.");
        }
    }

    private static void validateWeight(DroneRegistration droneRegistration) {
        if (droneRegistration.getWeightLimit() > 500) {
            LOGGER.error("Max weight limit is greater than 500gr.");
            throw new DroneApiException(HttpStatus.BAD_REQUEST, "Weight limit cannot be greater that 500gr");
        }
    }

    private static void validateBattery(DroneRegistration droneRegistration) {
        if (droneRegistration.getBatteryCapacity() > 1f || droneRegistration.getBatteryCapacity() < 0f) {
            LOGGER.error("Invalid battery capacity");
            throw new DroneApiException(HttpStatus.BAD_REQUEST, "Battery capacity should be a percentage value (0-1)");
        }
    }
}
