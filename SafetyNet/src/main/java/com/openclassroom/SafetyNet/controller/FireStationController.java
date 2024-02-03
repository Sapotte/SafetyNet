package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private FireStationService fireStationService;

    @PostMapping
    public void addFireStation(@RequestParam String address, @RequestParam String station) throws Exception {
        String fireStationNumber = fireStationService.addFireStation(address, station);
        System.out.println(MessageFormat.format("The fireStation number \"{0}\" has been successfully saved", fireStationNumber));
    }

    @DeleteMapping
    public void deleteFireStation(@RequestParam String address) throws Exception {
        try {
            fireStationService.deleteFirestationByAddress(address);
            System.out.println(MessageFormat.format("The station with the address \"{0}\" has been successfully deleted", address));
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }

    }

}
