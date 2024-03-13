package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repositories.models.PersonRepository;
import com.openclassroom.SafetyNet.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final Logger logger = LogManager.getLogger(PersonRepository.class);

    @Autowired
    private PersonService personService;

    @PostMapping
    @Operation(summary = "Add person's infos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New Person saved")
    })
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        try {
            personService.addPerson(person);
            logger.debug("Person added");
            return new ResponseEntity<>("Person created", HttpStatusCode.valueOf(201));
        } catch (Exception e) {
            logger.error("Error adding person" + e.getMessage());
            return new ResponseEntity<>("Person not saved", HttpStatusCode.valueOf(500));
        }

    }

    @PutMapping
    @Operation(summary = "Update person's infos by first and last names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person updated")
    })
    public ResponseEntity<String> updatePerson(@RequestParam Person person) {
        try {
            personService.updatePerson(person);
            logger.debug("Person updated");
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            logger.error("Error updating person: " + e.getMessage());
            return new ResponseEntity<>("Error updating person", HttpStatusCode.valueOf(500));
        }


    }

    @DeleteMapping
    @Operation(summary = "Delete person's infos by first and last names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person deleted")
    })
    public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
       try {
           personService.deletePerson(firstName, lastName);
           logger.debug("Person deleted");
           return new ResponseEntity<>("Person deleted", HttpStatusCode.valueOf(200));
       } catch (Exception e) {
           logger.error("Error deleting person" + e.getMessage());
           return new ResponseEntity<>("Person not deleted", HttpStatusCode.valueOf(500));
       }

    }
}
