package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.InvalidAttributeValueException;
import java.text.MessageFormat;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final Logger logger = LogManager.getLogger();

    @Autowired
    private PersonService personService;

    @PostMapping
    @Operation(summary = "Add person's infos")
    public void addPerson(@RequestBody Person person) throws InvalidAttributeValueException {
        personService.addPerson(person);
        logger.info("The fireStation number had been successfully saved");
    }

    @PutMapping
    public void updatePerson(@RequestParam Person person) {

        logger.info("The fireStation at the address had been successfully updated");
    }

    @DeleteMapping
    public void deletePerson(@RequestParam String address) throws Exception {

        logger.info(MessageFormat.format("The station with the address \"{0}\" has been successfully deleted", address));
    }
}
