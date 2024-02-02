package com.openclassroom.SafetyNet.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FireStations {
    @JsonProperty("firestations")
    private List<FireStation> fireStations;

    public List<FireStation> getFireStations() {
        return fireStations;
    }

    public void setFireStations(List<FireStation> fireStations) {
        this.fireStations = fireStations;
    }
}
