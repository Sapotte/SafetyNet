package com.openclassroom.SafetyNet.utils.mapper;

import com.openclassroom.SafetyNet.dto.ChildInfo;
import com.openclassroom.SafetyNet.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ChildInfoMapper {
    @Mapping(target = "firstName", source = "person.firstName")
    @Mapping(target = "lastName", source = "person.lastName")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "familyMembers", source = "familyMembers")
    ChildInfo mapChildInfoFromPersonAgeAndFamilyMemberList(Person person, Integer age, List<Person> familyMembers);
}
