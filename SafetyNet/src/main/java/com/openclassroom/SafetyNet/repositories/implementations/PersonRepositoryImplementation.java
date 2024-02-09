package com.openclassroom.SafetyNet.repositories.implementations;

import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repositories.models.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;

@Repository
public class PersonRepositoryImplementation implements PersonRepository {
    private static final Logger logger = LogManager.getLogger(PersonRepository.class);
    @Autowired
    public Datas datas;
    @Override
    public void savePerson(Person person) {
        datas.getPersons().add(person);
        logger.info(MessageFormat.format("{0} {1} has been added to the list of persons", person.getFirstName(), person.getLastName()));
    }

    @Override
    public void updatePerson(int index, Person person) {
        datas.getPersons().set(index, person);
        logger.info(MessageFormat.format("{0} {1} has been updated", person.getFirstName(), person.getLastName()));
    }

    @Override
    public void deletePerson(int index) {
        datas.getPersons().remove(index);
        logger.info("Person's info deleted");
    }
}
