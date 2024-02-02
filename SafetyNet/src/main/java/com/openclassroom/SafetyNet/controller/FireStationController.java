package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private FireStationService fireStationService;

    @PostMapping
    public String addFireStation(@RequestBody FireStation fireStation) throws Exception {
        String fireStationNumber = fireStationService.addFireStation(fireStation);
        return "The fireStation number "+fireStationNumber+" has been successfully saved";
    }

}
