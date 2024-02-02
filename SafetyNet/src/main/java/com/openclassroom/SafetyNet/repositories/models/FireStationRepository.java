package com.openclassroom.SafetyNet.repositories.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.model.FireStations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FireStationRepository {

    public abstract FireStations getfireStationList() throws IOException;
    public abstract void saveFirestation(FireStation fireStation) ;
    public abstract void updateFirestation(FireStation fireStation);
    public abstract void deleteFirestation(String stationNumber);
}
