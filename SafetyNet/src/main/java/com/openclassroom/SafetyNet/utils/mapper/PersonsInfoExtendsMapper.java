package com.openclassroom.SafetyNet.utils.mapper;

import com.openclassroom.SafetyNet.dto.PersonInfoExtendsDto;
import com.openclassroom.SafetyNet.dto.PersonInfoExtendsMailDto;
import com.openclassroom.SafetyNet.model.Medicalrecord;
import com.openclassroom.SafetyNet.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class PersonsInfoExtendsMapper {
    @Mapping(source = "person.firstName", target = "firstName")
    @Mapping(source = "person.lastName", target = "lastName")
    @Mapping(source = "person.phone", target = "phoneNumber")
    @Mapping(source = "medicalrecord.medications", target = "medications")
    @Mapping(source = "medicalrecord.allergies", target = "allergies")
    public abstract PersonInfoExtendsDto mapPersonAndMedicalRecordToPersonsInfoExtends(Person person, Medicalrecord medicalrecord, Integer age);

    @Mapping(source = "person.firstName", target = "firstName")
    @Mapping(source = "person.lastName", target = "lastName")
    @Mapping(source = "person.email", target = "email")
    @Mapping(source = "medicalrecord.medications", target = "medications")
    @Mapping(source = "medicalrecord.allergies", target = "allergies")
    public abstract PersonInfoExtendsMailDto mapPersonAndMedicalRecordToPersonsInfoExtendsMailDto(Person person, Medicalrecord medicalrecord, Integer age);


}
