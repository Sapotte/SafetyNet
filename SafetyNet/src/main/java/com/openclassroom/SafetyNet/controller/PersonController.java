package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repositories.models.PersonRepository;
import com.openclassroom.SafetyNet.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.InvalidAttributeValueException;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final Logger logger = LogManager.getLogger(PersonRepository.class);

    @Autowired
    private PersonService personService;

    @PostMapping
    @Operation(summary = "Add person's infos")
    public void addPerson(@RequestBody Person person) throws InvalidAttributeValueException {
        personService.addPerson(person);
        logger.debug("Person added");
    }

    @PutMapping
    @Operation(summary = "Update person's infos by first and last names")
    public void updatePerson(@RequestParam Person person) throws InvalidAttributeValueException {
        personService.updatePerson(person);
        logger.debug("Person updated");

    }

    @DeleteMapping
    @Operation(summary = "Delete person's infos by first and last names")
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) throws Exception {
        personService.deletePerson(firstName, lastName);
        logger.debug("Person deleted");

    }
}
