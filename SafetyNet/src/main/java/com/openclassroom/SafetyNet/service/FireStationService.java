package com.openclassroom.SafetyNet.service;

import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.model.FireStations;
import com.openclassroom.SafetyNet.repositories.models.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FireStationService {
    @Autowired
    FireStationRepository fireStationRepository;

    public String addFireStation(FireStation fireStation) throws Exception {
        FireStations fireStations = fireStationRepository.getfireStationList();
        fireStations.getFireStations().add(fireStation);
        try {
            return fireStationRepository.saveFirestation(fireStations);
        } catch (Exception e) {
            throw new Exception("An error occured during fireStation's saving");
        }
    }
}
