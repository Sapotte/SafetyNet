package com.openclassroom.SafetyNet.dto;

import java.util.List;

public class PersonsByAddressDto {
    public String station;
    public List<PersonInfoExtendsDto> personInfoExtendsDtoList;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public List<PersonInfoExtendsDto> getPersonInfoExtendsDtoList() {
        return personInfoExtendsDtoList;
    }

    public void setPersonInfoExtendsDtoList(List<PersonInfoExtendsDto> personInfoExtendsDtoList) {
        this.personInfoExtendsDtoList = personInfoExtendsDtoList;
    }
}
