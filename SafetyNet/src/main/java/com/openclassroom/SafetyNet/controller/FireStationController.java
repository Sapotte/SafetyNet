package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.dto.PersonsCoveredByFirestationDto;
import com.openclassroom.SafetyNet.service.FireStationService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private FireStationService fireStationService;

    @Operation(summary = "Adding new firestation")
    @PostMapping
    public ResponseEntity<String> addFireStation(@RequestParam String address, @RequestParam String station) {
        String fireStationNumber = fireStationService.addFireStation(address, station);
        logger.info(MessageFormat.format("The fireStation number \"{0}\" has been successfully saved", fireStationNumber));
        return new ResponseEntity<>("Firestation created", HttpStatusCode.valueOf(201));
    }

    @Operation(summary = "Update firestation")
    @PutMapping
    public ResponseEntity<String> updateFireStationNumber(@RequestParam String address, @RequestParam String station) {
        try{
            fireStationService.updateFireStation(address, station);
            logger.info(MessageFormat.format("The fireStation at the address \"{0}\" has been successfully updated", address));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity<>("Firestation updated", HttpStatusCode.valueOf(200));
    }
    @Operation(summary = "Delete firestation")
    @DeleteMapping
    public ResponseEntity<String> deleteFireStation(@RequestParam String address) throws Exception {
        try{
            fireStationService.deleteFirestationByAddress(address);
            logger.info(MessageFormat.format("The station with the address \"{0}\" has been successfully deleted", address));
            return new ResponseEntity<>("Deleted", HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            logger.error("Error deleting firestation: "+ e.getMessage());
            return new ResponseEntity<>("Error deleting firestation: "+ e.getMessage(), HttpStatusCode.valueOf(500));
        }

    }

    @GetMapping
    public ResponseEntity<PersonsCoveredByFirestationDto> getPersonCoveredByFirestation(@RequestParam int stationNumber) {
        PersonsCoveredByFirestationDto personCoveredByFirestation = fireStationService.getPersonCoveredByFirestation(stationNumber);
        logger.info(MessageFormat.format("{0} persons found", personCoveredByFirestation.getPersonsBasicInfosDtoList().size()));
        return new ResponseEntity<>(personCoveredByFirestation, HttpStatusCode.valueOf(200));
    }

}
