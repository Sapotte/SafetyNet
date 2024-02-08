package com.openclassroom.SafetyNet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repositories.implementations.PersonRepositoryImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PersonRepositoryImplTest {

    @InjectMocks
    PersonRepositoryImplementation personRepository;
    @Mock
    Datas datas;

    private final ObjectMapper mapper = new ObjectMapper();
    private Person newPerson;
    @BeforeEach
    void setUp() throws IOException {
        datas = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/DatasTest.json"), Datas.class);
        personRepository.datas = datas;

        newPerson = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/NewPerson.json"), Person.class);
    }

    @Test
    void savePersonOk() {
        personRepository.savePerson(newPerson);

        assertTrue(datas.getPersons().contains(newPerson));
    }
}
