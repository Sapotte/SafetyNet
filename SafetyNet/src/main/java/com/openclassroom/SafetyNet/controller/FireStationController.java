package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.model.FireStations;
import com.openclassroom.SafetyNet.repositories.models.FireStationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

    private Logger logger = LogManager.getLogger();
    @Autowired
    private FireStationRepository fireStationRepository;

    @GetMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE)
    public FireStations getFireStations() throws Exception {
        try {
            return fireStationRepository.getfireStationList();
        } catch (final Exception e) {
            logger.atWarn().log("Error reading JSON file", e);
            throw new Exception("Can't read JSON file", e);
        }
    }
}
