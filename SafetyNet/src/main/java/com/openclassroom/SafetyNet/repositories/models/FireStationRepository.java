package com.openclassroom.SafetyNet.repositories.models;

import com.openclassroom.SafetyNet.model.FireStation;

import java.util.List;

public interface FireStationRepository {

    String saveFirestation(List<FireStation> fireStation) throws Exception;
    void updateFirestation(FireStation fireStation);
    void deleteFirestation(String stationNumber);
}
