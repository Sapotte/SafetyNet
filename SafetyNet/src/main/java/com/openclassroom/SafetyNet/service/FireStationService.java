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

    public String addFireStation(String address, String station) {
        FireStation newFirestation = new FireStation();
        newFirestation.setAddress(address);
        newFirestation.setStation(station);
        logger.debug("New object FireStation created");
        return fireStationRepository.saveFirestation(newFirestation);
    }

    public void updateFireStation(String address, String station) {
        var firstationToUpdate = IntStream.range(0, datas.getFireStations().size()).filter(i -> datas.getFireStations().get(i).getAddress().equals(address)).findFirst();
        if (firstationToUpdate.isPresent()) {
            logger.debug(MessageFormat.format("Firestation at {0} to be updated", firstationToUpdate.getAsInt()));
            fireStationRepository.updateFirestation(firstationToUpdate.getAsInt(), station);
        } else {
            logger.error("No station found");
            throw new NullPointerException(MessageFormat.format("No station found at the address {0}", address));
        }
    }

    public void deleteFirestationByAddress(String address) throws ClassNotFoundException {
        var optionalIndex = IntStream.range(0, datas.getFireStations().size()).filter(i -> datas.getFireStations().get(i).getAddress().equals(address)).findFirst();

        if (optionalIndex.isPresent()) {
            int index = optionalIndex.getAsInt();
            logger.debug(MessageFormat.format("Firestation at index {0} to be deleted", optionalIndex));
            fireStationRepository.deleteFirestation(index);
        } else {
            logger.error("No firestation found at this address");
            throw new ClassNotFoundException(MessageFormat.format("There is no station for the address \" {0} \"", address));
        }
    }

}
