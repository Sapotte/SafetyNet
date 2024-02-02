package com.openclassroom.SafetyNet.repositories.implementations;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.model.FireStations;
import com.openclassroom.SafetyNet.repositories.models.FireStationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

@Repository
public class FireStationRepositoryImplementation implements FireStationRepository {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ObjectMapper mapper = new ObjectMapper();
    private final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    private final File fireStationsDatas = new File("src/main/resources/datas/FireStations.json");


    public FireStations getfireStationList() throws IOException {
            try {
                return mapper.readValue(fireStationsDatas , FireStations.class);
            } catch (final Exception e) {
                LOGGER.atWarn().log(e.getMessage());
                throw new IOException(e);
            }
    }



    @Override
    public String saveFirestation(FireStations fireStations) throws Exception {
        try {
            writer.writeValue(fireStationsDatas, fireStations);
            return fireStations.getFireStations().get(fireStations.getFireStations().size()-1).getStation();
        } catch (final Exception e) {
            LOGGER.atWarn().log(e.getMessage());
            throw new Exception("Error saving data: "+e.getMessage());
        }
    }

    @Override
    public void updateFirestation(FireStation fireStation) {

    }

    @Override
    public void deleteFirestation(String stationNumber) {

    }
}
