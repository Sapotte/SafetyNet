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
    private static final Logger logger = LogManager.getLogger(FireStationRepository.class);

    @Autowired
    public Datas datas;


    @Override
    public String saveFirestation(FireStation fireStation) {
        logger.debug(MessageFormat.format("Add station {0} located at {1} to the firestations' list", fireStation.getStation(), fireStation.getAddress()));
        datas.getFireStations().add(fireStation);
        return fireStation.getStation();
    }

    @Override
    public void updateFirestation(int index, String newStation) {
        logger.debug(MessageFormat.format("Update station'number to {0}", newStation));
        datas.getFireStations().get(index).setStation(newStation);
    }

    @Override
    public void deleteFirestation(int index) {
        logger.debug("Delete station");
        datas.getFireStations().remove(index);
    }
}
