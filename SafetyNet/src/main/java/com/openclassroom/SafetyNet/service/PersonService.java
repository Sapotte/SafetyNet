package com.openclassroom.SafetyNet.service;

import com.openclassroom.SafetyNet.helpers.PatternHelper;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repositories.models.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.text.MessageFormat;

@Service
public class PersonService {
    private static final Logger logger = LogManager.getLogger(FireStationService.class);
    @Autowired
    PersonRepository personRepository;
    @Autowired
    Datas datas;
    @Autowired
    PatternHelper patternHelper;
    public void addPerson(Person newPerson) throws InvalidAttributeValueException {
        patternHelper.checkisValidPerson(newPerson);
        // Check if the person is not already in the list
        if (datas.getPersons().stream().anyMatch(person -> person.getFirstName().equals(newPerson.getFirstName()) && person.getLastName().equals(newPerson.getLastName()))) {
            throw new InvalidAttributeValueException(MessageFormat.format("{0} {1} is already in the list", newPerson.getFirstName(), newPerson.getLastName()));
        }
        personRepository.savePerson(newPerson);
    }
}
