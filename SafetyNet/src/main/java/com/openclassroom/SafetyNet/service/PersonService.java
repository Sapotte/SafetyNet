package com.openclassroom.SafetyNet.service;

import com.openclassroom.SafetyNet.dto.ChildInfo;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repositories.models.PersonRepository;
import com.openclassroom.SafetyNet.utils.helper.AgeHelper;
import com.openclassroom.SafetyNet.utils.helper.PatternHelper;
import com.openclassroom.SafetyNet.utils.mapper.ChildInfoMapper;
import com.openclassroom.SafetyNet.utils.mapper.ChildInfoMapperImpl;
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
    PatternHelper patternHelper;

    ChildInfoMapper childInfoMapper = new ChildInfoMapperImpl();

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

    public List<ChildInfo> getChildsByAddress(String address) {
        List<ChildInfo> childsInfoByAddress = new ArrayList<>();
        List<Person> personsAtAddress = datas.getPersons().stream().filter(person -> address.equals(person.getAddress())).collect(Collectors.toList());
        Map<Person, Integer> childsAtAddress = new HashMap<>();
        List<Person> adultsAtAddress = new ArrayList<>();
        for(Person person : personsAtAddress) {
            String birthDate = datas.getMedicalRecords()
                    .stream()
                    .filter(record -> person.getFirstName().equals(record.getFirstName()) && person.getLastName().equals(record.getLastName()))
                    .toList()
                    .get(0)
                    .getBirthdate();
            if(AgeHelper.INSTANCE.isAdult(birthDate)) {
                adultsAtAddress.add(person);
            }else {
                childsAtAddress.put(person, AgeHelper.INSTANCE.getAge(birthDate));
            }
        }

        for (Map.Entry<Person, Integer> child : childsAtAddress.entrySet()) {
            List<Person> familyMembers = adultsAtAddress.stream().filter(adult -> child.getKey().getLastName().equals(adult.getLastName())).collect(Collectors.toList());
            childsInfoByAddress.add(childInfoMapper.mapChildInfoFromPersonAgeAndFamilyMemberList(child.getKey(), child.getValue(), familyMembers));
        }
        return childsInfoByAddress;
    }
}
