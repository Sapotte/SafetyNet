package com.openclassroom.SafetyNet.repositories.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.model.FireStations;
import com.openclassroom.SafetyNet.repositories.models.FireStationRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.internal.Logger;

import java.io.File;
import java.io.IOException;

@Repository
public class FireStationRepositoryImplementation implements FireStationRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File fireStationsDatas = new File("src/main/resources/datas/FireStations.json");

    public FireStations getfireStationList() throws IOException {
        if(fireStationsDatas.exists()) {
            return mapper.readValue(fireStationsDatas , FireStations.class);
        } else {
            Logger.getLogger("File").warn("File not found");
            return null;
        }
    }


    @Override
    public void saveFirestation(FireStation fireStation) {

    }

    @Override
    public void updateFirestation(FireStation fireStation) {

    }

    @Override
    public void deleteFirestation(String stationNumber) {

    }
}
