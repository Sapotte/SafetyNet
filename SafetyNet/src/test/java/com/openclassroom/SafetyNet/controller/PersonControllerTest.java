package com.openclassroom.SafetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.InvalidAttributeValueException;
import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {
    @InjectMocks
    private static PersonController personController;
    @Mock
    private static PersonService personService;
    private final ObjectMapper mapper = new ObjectMapper();
    Person newPerson;

    @BeforeEach
    void setUp() throws IOException {
        newPerson = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/NewPerson.json"), Person.class);
    }

    @Test
    void savePersonOk() throws InvalidAttributeValueException {
        personController.addPerson(newPerson);

        verify(personService, times(1)).addPerson(newPerson);
    }

    @Test
    void updatePersonOk() throws InvalidAttributeValueException {
        personController.updatePerson(newPerson);

        verify(personService, times(1)).updatePerson(newPerson);
    }
}
