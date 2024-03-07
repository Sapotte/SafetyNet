package com.openclassroom.SafetyNet.dto;

import java.util.List;

public class PersonsCoveredByFirestationDto {
    public Integer adultsCount;
    public Integer childsCount;
    public List<PersonsBasicInfosDto> personsBasicInfosDtoList;


    public Integer getAdultsCount() {
        return adultsCount;
    }

    public void setAdultsCount(Integer adultsCount) {
        this.adultsCount = adultsCount;
    }

    public Integer getChildsCount() {
        return childsCount;
    }

    public void setChildsCount(Integer childsCount) {
        this.childsCount = childsCount;
    }

    public List<PersonsBasicInfosDto> getPersonsBasicInfosDtoList() {
        return personsBasicInfosDtoList;
    }

    public void setPersonsBasicInfosDtoList(List<PersonsBasicInfosDto> personsBasicInfosDtoList) {
        this.personsBasicInfosDtoList = personsBasicInfosDtoList;
    }
}
