package com.openclassroom.SafetyNet.repositories.models;

import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.model.FireStations;

import java.io.IOException;

public interface FireStationRepository {

    FireStations getfireStationList() throws IOException;
    String saveFirestation(FireStations fireStation) throws Exception;
    void updateFirestation(FireStation fireStation);
    void deleteFirestation(String stationNumber);
}
