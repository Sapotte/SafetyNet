package com.openclassroom.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.helpers.PatternHelper;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repositories.models.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.InvalidAttributeValueException;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @InjectMocks
    PersonService personService;
    @Mock
    PersonRepository personRepository;
    @Mock
    Datas datas;
    @Mock
    PatternHelper patternHelper;
    private final ObjectMapper mapper = new ObjectMapper();
    Person newPerson;

    @BeforeEach
    void setUp() throws IOException {
        datas = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/DatasTest.json"), Datas.class);
        personService.datas = datas;
        newPerson = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/NewPerson.json"), Person.class);
    }

    @Test
    void addPersonOk() throws InvalidAttributeValueException {
        doNothing().when(patternHelper).checkisValidPerson(any());

        personService.addPerson(newPerson);

        verify(personRepository, times(1)).savePerson(any(Person.class));
    }

    @Test
    void addAlreadySavedPersonnKo() {
        assertThrows(InvalidAttributeValueException.class, () -> personService.addPerson(datas.getPersons().get(0)));
        verify(personRepository, times(0)).savePerson(any(Person.class));
    }
}
