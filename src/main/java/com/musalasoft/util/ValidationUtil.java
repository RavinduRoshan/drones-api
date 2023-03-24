package com.musalasoft.util;

import com.musalasoft.entity.Medication;
import com.musalasoft.exception.DroneApiException;
import com.musalasoft.model.DroneRegistration;
import com.musalasoft.model.MedicationList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class ValidationUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtil.class);
    public static void validateDrone(DroneRegistration droneRegistration) {
        LOGGER.info("Validating the drone info..");
        validateEmptySerialNumber(droneRegistration);
        validateSerialNumberLength(droneRegistration);
        validateWeight(droneRegistration);
        validateBattery(droneRegistration);
    }

    public static void validateMedication(Medication medication) {
        LOGGER.info("Validating the medication info..");
        validateMedicationName(medication);
        validateMedicationCode(medication);
        validateImage(medication);
    }

    private static void validateMedicationName(Medication medication) {
        String name = medication.getName();
        String regex = "^[a-zA-Z0-9_-]+$";
        if (name == null || !name.matches(regex)) {
            LOGGER.error("Invalid medication name is provided.");
            throw new DroneApiException(HttpStatus.BAD_REQUEST, "Medication name is invalid. name can contains only letters, numbers, '-' and '_'");
        }
    }

    private static void validateMedicationCode(Medication medication) {
        String code = medication.getCode();
        String regex = "^[A-Z_\\d]+$";
        if (code == null || !code.matches(regex)) {
            LOGGER.error("Invalid medication code is provided.");
            throw new DroneApiException(HttpStatus.BAD_REQUEST, "Medication code is invalid. code can contains only uppercase letters, numbers, and '_'");
        }
    }

    private static void validateImage(Medication medication) {
        String image = medication.getImage();
        String regex = "^(.*[\\\\/])?([\\w\\d-]+\\.(jpg|jpeg|png|gif))$";
        if (image == null || !image.matches(regex)) {
            LOGGER.error("Invalid medication image is provided.");
            throw new DroneApiException(HttpStatus.BAD_REQUEST, "Medication image path is invalid. Image should be a file path");
        }
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
