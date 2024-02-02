package com.openclassroom.SafetyNet.repositories.models;

import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.model.FireStations;

import java.io.IOException;

public interface FireStationRepository {

    FireStations getfireStationList() throws IOException;
    void saveFirestation(FireStation fireStation) ;
    void updateFirestation(FireStation fireStation);
    void deleteFirestation(String stationNumber);
}
