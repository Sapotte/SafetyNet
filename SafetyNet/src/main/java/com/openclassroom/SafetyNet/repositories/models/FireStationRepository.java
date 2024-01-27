package com.openclassroom.SafetyNet.repositories.models;

import com.openclassroom.SafetyNet.model.FireStation;

public interface FireStationRepository {


    public abstract void saveFirestation(FireStation fireStation);
    public abstract void updateFirestation(FireStation fireStation);
    public abstract void deleteFirestation(String stationNumber);
}
