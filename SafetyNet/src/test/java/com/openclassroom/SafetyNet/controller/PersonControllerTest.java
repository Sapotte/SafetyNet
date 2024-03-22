package com.openclassroom.SafetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.service.PersonService;
import com.openclassroom.SafetyNet.utils.errors.InvalidNumberOfMatches;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import javax.management.InvalidAttributeValueException;
import java.io.File;
import java.io.IOException;

import static com.openclassroom.SafetyNet.utils.Constants.FIRST_NAME;
import static com.openclassroom.SafetyNet.utils.Constants.LAST_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        var response = personController.addPerson(newPerson);

        verify(personService, times(1)).addPerson(newPerson);
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
    }
    @Test
    void savePersonKo() throws InvalidAttributeValueException {
        doThrow(InvalidAttributeValueException.class).when(personService).addPerson(newPerson);
        var response = personController.addPerson(newPerson);
        assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
    }

    @Test
    void updatePersonOk() throws InvalidAttributeValueException, InvalidNumberOfMatches {
        var response = personController.updatePerson(newPerson);

        verify(personService, times(1)).updatePerson(newPerson);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
    @Test
    void updatePersonKo() throws InvalidAttributeValueException, InvalidNumberOfMatches {
        doThrow(InvalidAttributeValueException.class).when(personService).updatePerson(any());
        var response = personController.updatePerson(newPerson);
        assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
    }
    @Test
    void deletePersonOk() throws Exception {
       var response = personController.deletePerson(newPerson.getFirstName(), newPerson.getLastName());

        verify(personService, times(1)).deletePerson(newPerson.getFirstName(), newPerson.getLastName());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
    @Test
    void deletePersonKo() throws InvalidNumberOfMatches {
        doThrow(InvalidNumberOfMatches.class).when(personService).deletePerson(anyString(), anyString());
        var response = personController.deletePerson(FIRST_NAME, LAST_NAME);
        assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
    }
}
