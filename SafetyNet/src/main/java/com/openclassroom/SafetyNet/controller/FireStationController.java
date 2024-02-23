package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.dto.PersonsCoveredByFirestation;
import com.openclassroom.SafetyNet.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.InvalidAttributeValueException;
import java.text.MessageFormat;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private FireStationService fireStationService;

    @PostMapping
    public void addFireStation(@RequestParam String address, @RequestParam String station) {
        String fireStationNumber = fireStationService.addFireStation(address, station);
        logger.info(MessageFormat.format("The fireStation number \"{0}\" has been successfully saved", fireStationNumber));
    }

    @PutMapping
    public void updateFireStationNumber(@RequestParam String address, @RequestParam String station) {
            fireStationService.updateFireStation(address, station);
            logger.info(MessageFormat.format("The fireStation at the address \"{0}\" has been successfully updated", address));
    }

    @DeleteMapping
    public void deleteFireStation(@RequestParam String address) throws Exception {
        fireStationService.deleteFirestationByAddress(address);
        logger.info(MessageFormat.format("The station with the address \"{0}\" has been successfully deleted", address));
    }

    @GetMapping
    public PersonsCoveredByFirestation getPersonCoveredByFirestation(@RequestParam int stationNumber) throws InvalidAttributeValueException {
        PersonsCoveredByFirestation personCoveredByFirestation = fireStationService.getPersonCoveredByFirestation(stationNumber);
        logger.info(MessageFormat.format("{0} persons found", personCoveredByFirestation.getPersonsBasicInfosList().size()));
        return personCoveredByFirestation;
    }

}
