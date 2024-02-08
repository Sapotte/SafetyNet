package com.openclassroom.SafetyNet.repositories.implementations;

import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repositories.models.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.ErrorResponseException;

import java.text.MessageFormat;

@Repository
public class PersonRepositoryImplementation implements PersonRepository {
    private static final Logger logger = LogManager.getLogger(PersonRepository.class);
    @Autowired
    public Datas datas;
    @Override
    public void savePerson(Person person) {
        datas.getPersons().add(person);

        if(datas.getPersons().contains(person)) {
            logger.info(MessageFormat.format("{0} {1} has been added to the list of persons", person.getFirstName(), person.getLastName()));
        } else {
            throw new ErrorResponseException(HttpStatusCode.valueOf(417), new Throwable("Error adding the person's infos to the list"));
        }
    }

    @Override
    public void updatePerson(Person person) {

    }

    @Override
    public void deletePerson(Person person) {

    }
}
