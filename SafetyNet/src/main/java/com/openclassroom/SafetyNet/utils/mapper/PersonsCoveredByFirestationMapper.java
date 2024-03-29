package com.openclassroom.SafetyNet.utils.mapper;

import com.openclassroom.SafetyNet.dto.PersonsBasicInfosDto;
import com.openclassroom.SafetyNet.dto.PersonsCoveredByFirestationDto;
import com.openclassroom.SafetyNet.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;

@Mapper
public interface PersonsCoveredByFirestationMapper {
    @Mapping(target="firstName", source="person.firstName")
    @Mapping(target="lastName", source="person.lastName")
    @Mapping(target="address", source="person.address")
    @Mapping(target="phoneNumber", source="person.phone")
    PersonsBasicInfosDto mapPersonToPersonBasicsInfos(Person person);


    @Mapping(target = "adultsCount", source = "adultsChildsCount.adultsCount")
    @Mapping(target = "childsCount", source = "adultsChildsCount.childsCount")
    @Mapping(target = "personsBasicInfosDtoList", source = "personsList")
    PersonsCoveredByFirestationDto mapPersonAndAdultChildCountToPersonCoveredByFirestation(List<Person> personsList, Map<String, Integer> adultsChildsCount);
}
