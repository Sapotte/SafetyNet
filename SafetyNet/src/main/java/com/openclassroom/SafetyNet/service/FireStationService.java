package com.openclassroom.SafetyNet.service;

import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.repositories.models.FireStationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.stream.IntStream;

@Service
public class FireStationService {
    private static final Logger logger = LogManager.getLogger(FireStationService.class);
    @Autowired
    FireStationRepository fireStationRepository;

    @Autowired
    Datas datas;

    public String addFireStation(String address, String station) throws Exception {
        FireStation newFirestation = new FireStation();
        newFirestation.setAddress(address);
        newFirestation.setStation(station);
        try {
            return fireStationRepository.saveFirestation(newFirestation);
        } catch (Exception e) {
            throw new Exception("An error occured during fireStation's saving");
        }
    }

    public void deleteFirestationByAddress(String address) throws ClassNotFoundException{
        Integer index = IntStream.range(0, datas.getFireStations().size()).filter(i -> datas.getFireStations().get(i).getAddress().equals(address)).findFirst().getAsInt();
        if (index != null) {
            fireStationRepository.deleteFirestation(index);
        } else {
            throw new ClassNotFoundException(MessageFormat.format("There is no station for the address \" {0} \"", address));
        }
    }

}
