package com.openclassroom.SafetyNet.repositories.implementations;

import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.repositories.models.FireStationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;

@Repository
public class FireStationRepositoryImplementation implements FireStationRepository {
    private static final Logger LOGGER = LogManager.getLogger(FireStationRepository.class);

    @Autowired
    private Datas datas;


    @Override
    public String saveFirestation(FireStation fireStation) {
        LOGGER.info(MessageFormat.format("Add station {0} located at {1} to the firestations list", fireStation.getStation(), fireStation.getAddress()));
        datas.getFireStations().add(fireStation);
        return fireStation.getStation();
    }

    @Override
    public void updateFirestation(int index, String newStation) {

    }

    @Override
    public void deleteFirestation(int index) {
        LOGGER.info("Station deleted");
        datas.getFireStations().remove(index);
    }
}
