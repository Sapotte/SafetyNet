package com.openclassroom.SafetyNet.service;

import com.openclassroom.SafetyNet.dto.ChildInfoDto;
import com.openclassroom.SafetyNet.dto.PersonInfoExtendsMailDto;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repositories.models.PersonRepository;
import com.openclassroom.SafetyNet.utils.errors.NotFoundException;
import com.openclassroom.SafetyNet.utils.helper.AgeHelper;
import com.openclassroom.SafetyNet.utils.helper.PatternHelper;
import com.openclassroom.SafetyNet.utils.mapper.ChildInfoMapper;
import com.openclassroom.SafetyNet.utils.mapper.ChildInfoMapperImpl;
import com.openclassroom.SafetyNet.utils.mapper.PersonsInfoExtendsMapper;
import com.openclassroom.SafetyNet.utils.mapper.PersonsInfoExtendsMapperImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PersonService {
    private static final Logger logger = LogManager.getLogger(PersonService.class);
    @Autowired
    PersonRepository personRepository;
    @Autowired
    Datas datas;
    @Autowired
    MedicalrecordService medicalrecordService;
    @Autowired
    PatternHelper patternHelper;

    ChildInfoMapper childInfoMapper = new ChildInfoMapperImpl();
    PersonsInfoExtendsMapper personsInfoExtendsMapper = new PersonsInfoExtendsMapperImpl();

    public void addPerson(Person newPerson) throws InvalidAttributeValueException {
        patternHelper.checkIsValidPerson(newPerson);
        // Check if the person is not already in the list
        if (datas.getPersons().stream().anyMatch(person -> person.getFirstName().equals(newPerson.getFirstName()) && person.getLastName().equals(newPerson.getLastName()))) {
            logger.error("Person already in the list");
            throw new InvalidAttributeValueException(MessageFormat.format("{0} {1} is already in the list", newPerson.getFirstName(), newPerson.getLastName()));
        }
        logger.debug("Save person");
        personRepository.savePerson(newPerson);
    }

    public void updatePerson(Person updatedPerson) throws InvalidAttributeValueException {
        patternHelper.checkIsValidPerson(updatedPerson);
        // Find the index of the person to be updated
        var indexList = IntStream.range(0, datas.getPersons().size())
                .filter(i -> datas.getPersons().get(i).getFirstName().equals(updatedPerson.getFirstName()) && datas.getPersons().get(i).getLastName().equals(updatedPerson.getLastName()))
                .boxed()
                .toList();
        if(indexList.size() == 1) {
            logger.debug(MessageFormat.format("Person at index {0} to be updated", indexList.getFirst()));
            personRepository.updatePerson(indexList.getFirst(), updatedPerson);
        } else {
            logger.error("Invalid number of matches in the list for this person");
            throw new InvalidAttributeValueException(MessageFormat.format("Their is {0} matches for {1} {2}", indexList.size(), updatedPerson.getFirstName(), updatedPerson.getLastName()));
        }
    }

    public void deletePerson(String firstName, String lastName) throws InvalidAttributeValueException {
        // Find the index of the person to be deleted
        var indexList = IntStream.range(0, datas.getPersons().size())
                .filter(i -> datas.getPersons().get(i).getFirstName().equals(firstName) && datas.getPersons().get(i).getLastName().equals(lastName))
                .boxed()
                .toList();
        if(indexList.size() == 1) {
            logger.debug(MessageFormat.format("Person at index {0} to be deleted", indexList.getFirst()));
            personRepository.deletePerson(indexList.getFirst());
        } else {
            logger.error("Invalid number of matches in the list for this person");
            throw new InvalidAttributeValueException(MessageFormat.format("Their is {0} matches for {1} {2}", indexList.size(), firstName, lastName));
        }
    }

    public List<Person> getPersonsByAddresses(List<String> addresses) {
        return datas.getPersons().stream().filter(person -> addresses.contains(person.getAddress())).collect(Collectors.toList());
    }

    public List<Person> getPersonsByNames(String firstName, String lastName) {
        return datas.getPersons().stream().filter(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName())).collect(Collectors.toList());
    }

    public List<ChildInfoDto> getChildsByAddress(String address) {
        List<ChildInfoDto> childrenInfoByAddress = new ArrayList<>();
        Map<Person, Integer> childrenAtAddress = new HashMap<>();
        List<Person> adultsAtAddress = new ArrayList<>();
        logger.debug("Fetch persons and medicalrecords");
        for(Person person : getPersonsByAddresses(List.of(address))) {
            medicalrecordService.getMedicalrecordsByName(person.getFirstName(), person.getLastName()).forEach(medicalrecord -> {
                if(AgeHelper.INSTANCE.isAdult(medicalrecord.getBirthdate())) {
                    adultsAtAddress.add(person);
                }else {
                    childrenAtAddress.put(person, AgeHelper.INSTANCE.getAge(medicalrecord.getBirthdate()));
                }
            });
        }
        for (Map.Entry<Person, Integer> child : childrenAtAddress.entrySet()) {
            List<Person> familyMembers = adultsAtAddress.stream().filter(adult -> child.getKey().getLastName().equals(adult.getLastName())).collect(Collectors.toList());
            childrenInfoByAddress.add(childInfoMapper.mapChildInfoFromPersonAgeAndFamilyMemberList(child.getKey(), child.getValue(), familyMembers));
        }
        logger.info("Children's info successfully retrieved");
        return childrenInfoByAddress;
    }

    public List<PersonInfoExtendsMailDto> getPersonInfoExtendsByFirstAndLastName(String firstName, String lastName) {
        List<PersonInfoExtendsMailDto> personInfoExtendsDtoList = new ArrayList<>();
        try{
            List<Person> personsList= getPersonsByNames(firstName, lastName);
            if(!personsList.isEmpty()) {
                logger.info("Get medicalrecords");
                for (Person person : getPersonsByNames(firstName, lastName)) {
                    medicalrecordService.getMedicalrecordsByName(firstName, lastName).forEach(medicalrecord -> {
                        if(medicalrecord == null) {
                            personInfoExtendsDtoList.add(personsInfoExtendsMapper.mapPersonAndMedicalRecordToPersonsInfoExtendsMailDto(person, null, null));
                        } else {
                            personInfoExtendsDtoList.add(personsInfoExtendsMapper.mapPersonAndMedicalRecordToPersonsInfoExtendsMailDto(person, medicalrecord, AgeHelper.INSTANCE.getAge(medicalrecord.getBirthdate())));
                        }
                    });
                }
            } else {
                throw  new NotFoundException("No person found");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return personInfoExtendsDtoList ;
    }

    public List<String> getMailsByCity(String city) {
        return datas.getPersons().stream().filter(person -> city.equals(person.getCity())).map(Person::getEmail).collect(Collectors.toList());
    }
}
