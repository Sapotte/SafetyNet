package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repository.PersonRepository;
import com.openclassroom.SafetyNet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/person")
public class PersonController {

    private Logger logger;
    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<Void> addPerson(@RequestBody Person person) {
         personService.addPerson(person);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> updatePerson(@PathVariable String firstName, @PathVariable String lastName, @RequestBody Person updatedPerson) {
        try {
            personService.updatePerson(firstName, lastName, updatedPerson);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable String firstName, @PathVariable String lastName) {
        try {
            personService.deleteByFirstNameAndLastName(firstName, lastName);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
