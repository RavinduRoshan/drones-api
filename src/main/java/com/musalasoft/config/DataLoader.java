package com.musalasoft.config;

import com.musalasoft.entity.Drone;
import com.musalasoft.entity.Medication;
import com.musalasoft.model.Model;
import com.musalasoft.model.State;
import com.musalasoft.repository.DroneRepository;
import com.musalasoft.repository.MedicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);
    private static final String PRE_LOADING = "Preloading {}";

    @Bean
    CommandLineRunner initDatabase(DroneRepository droneRepository, MedicationRepository medicationRepository) {

        return args -> {
            LOGGER.info(PRE_LOADING, droneRepository.save(new Drone("DR01-NIKON_45", Model.LIGHTWEIGHT, 250.5f, 9.8f, State.IDLE)));
            LOGGER.info(PRE_LOADING, droneRepository.save(new Drone("DR02-NIKON_4522", Model.HEAVYWEIGHT, 450.5f, 7.8f, State.IDLE)));

            LOGGER.info(PRE_LOADING, medicationRepository.save(new Medication("MED_001", "Paracetemol", 130.5f, "/resources/images/MED_001.png")));
            LOGGER.info(PRE_LOADING, medicationRepository.save(new Medication("MED_002", "Piriton", 170.5f, "/resources/images/MED_002.png")));
            LOGGER.info(PRE_LOADING, medicationRepository.save(new Medication("MED_003", "Amoxilin", 30.5f, "/resources/images/MED_003.png")));
            LOGGER.info(PRE_LOADING, medicationRepository.save(new Medication("MED_004", "Omeprazole", 78.7f, "/resources/images/MED_004.png")));
            LOGGER.info(PRE_LOADING, medicationRepository.save(new Medication("MED_005", "Cetrizine", 501.5f, "/resources/images/MED_005.png")));
        };
    }
}
