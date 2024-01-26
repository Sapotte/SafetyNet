package com.openclassroom.SafetyNet.service;

import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repository.PersonRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public void addPerson(Person person) {
        personRepository.save(person);
    }

    public void updatePerson(String firstName, String lastName, Person updatedPerson) {
    }

    public void deleteByFirstNameAndLastName(String firstName, String lastName) {
        Long id = personRepository.findByFirstNameAndLastName(firstName, lastName);
        personRepository.deleteById(id);
    }

}
