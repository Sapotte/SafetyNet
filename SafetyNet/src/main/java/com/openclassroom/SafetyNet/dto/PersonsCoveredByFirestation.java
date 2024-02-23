package com.openclassroom.SafetyNet.dto;

import java.util.List;

public class PersonsCoveredByFirestation {
    public Integer adultsCount;
    public Integer childsCount;
    public List<PersonsBasicInfos> personsBasicInfosList;

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

    public List<PersonsBasicInfos> getPersonsBasicInfosList() {
        return personsBasicInfosList;
    }

    public void setPersonsBasicInfosList(List<PersonsBasicInfos> personsBasicInfosList) {
        this.personsBasicInfosList = personsBasicInfosList;
    }

    @Override
    public String toString() {
        return "PersonsCoveredByFirestation{" +
                "adultsCount=" + adultsCount +
                ", childsCount=" + childsCount +
                ", personsBasicInfosList=" + personsBasicInfosList +
                '}';
    }
}
