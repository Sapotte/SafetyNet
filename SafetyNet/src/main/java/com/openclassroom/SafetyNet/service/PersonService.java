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
}
