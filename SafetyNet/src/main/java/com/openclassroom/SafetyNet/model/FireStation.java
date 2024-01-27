package com.openclassroom.SafetyNet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;


public class FireStation {
    @JsonProperty("address")
    private String address;
    @JsonProperty("station")
    private String station;

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("station")
    public String getStation() {
        return station;
    }

    @JsonProperty("station")
    public void setStation(String station) {
        this.station = station;
    }
}
