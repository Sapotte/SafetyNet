package com.openclassroom.SafetyNet.repositories.models;

import com.openclassroom.SafetyNet.model.FireStation;

public interface FireStationRepository {

    String saveFirestation(FireStation fireStation);
    void updateFirestation(int index, String newStation);
    void deleteFirestation(int index);
}
