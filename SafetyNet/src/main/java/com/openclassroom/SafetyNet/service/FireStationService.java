package com.openclassroom.SafetyNet.service;

import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.repositories.models.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService {
    @Autowired
    FireStationRepository fireStationRepository;
    @Autowired
    Datas datas;

    public String addFireStation(FireStation fireStation) throws Exception {
        List<FireStation> fireStations = datas.getFireStations();
        fireStations.add(fireStation);
        try {
            return fireStationRepository.saveFirestation(fireStations);
        } catch (Exception e) {
            throw new Exception("An error occured during fireStation's saving");
        }
    }
}
