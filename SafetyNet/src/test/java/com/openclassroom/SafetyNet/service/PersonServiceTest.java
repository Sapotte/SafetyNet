package com.openclassroom.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.dto.ChildInfoDto;
import com.openclassroom.SafetyNet.dto.PersonInfoExtendsMailDto;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repositories.models.PersonRepository;
import com.openclassroom.SafetyNet.utils.errors.NotFoundException;
import com.openclassroom.SafetyNet.utils.helper.PatternHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.InvalidAttributeValueException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.openclassroom.SafetyNet.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
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
    MedicalrecordService medicalrecordService;
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
        doNothing().when(patternHelper).checkIsValidPerson(any());

        personService.addPerson(newPerson);

        verify(personRepository, times(1)).savePerson(any(Person.class));
    }

    @Test
    void addAlreadySavedPersonKo() {
        assertThrows(InvalidAttributeValueException.class, () -> personService.addPerson(datas.getPersons().get(0)));
        verify(personRepository, times(0)).savePerson(any(Person.class));
    }

    @Test
    void updatePersonOk() throws InvalidAttributeValueException {
        doNothing().when(patternHelper).checkIsValidPerson(any());

        // Get an already existing person and set the new person with first and last names
        Person personToUpdate = datas.getPersons().getFirst();
        newPerson.setFirstName(personToUpdate.getFirstName());
        newPerson.setLastName(personToUpdate.getLastName());

        personService.updatePerson(newPerson);

        verify(personRepository, times(1)).updatePerson(anyInt(), any(Person.class));
    }
    @Test
    void updateDuplicateKo() {
        // Duplicate a person in the list
        datas.getPersons().add(datas.getPersons().getFirst());
        assertThrows(InvalidAttributeValueException.class, () -> personService.updatePerson(datas.getPersons().get(0)));
        verify(personRepository, times(0)).updatePerson(anyInt(), any(Person.class));
    }
    @Test
    void deletePersonOk() throws InvalidAttributeValueException {
        Person personToDelete = datas.getPersons().getFirst();
        personService.deletePerson(personToDelete.getFirstName(), personToDelete.getLastName());

        verify(personRepository, times(1)).deletePerson(0);
    }
    @Test
    void deleteDuplicateKo() {
        // Duplicate a person in the list
        datas.getPersons().add(datas.getPersons().getFirst());
        assertThrows(InvalidAttributeValueException.class, () -> personService.deletePerson(datas.getPersons().get(0).getFirstName(), datas.getPersons().get(0).getFirstName()));
        verify(personRepository, times(0)).deletePerson(anyInt());
    }

    @Test
    void getPersonByAddressOk() {
        var result = personService.getPersonsByAddresses(List.of(ADDRESS));

        assertEquals(result, List.of(datas.getPersons().getFirst(), datas.getPersons().get(1)));
    }
    @Test
    void getPersonByNamesOk() {
        var result = personService.getPersonsByNames(FIRST_NAME, LAST_NAME);

        assertEquals(result, List.of(datas.getPersons().getFirst()));
    }
    @Test
    void getChildsByAddressOk() throws NotFoundException {
        when(medicalrecordService.getMedicalrecordsByName(FIRST_NAME, LAST_NAME)).thenReturn(List.of(datas.getMedicalRecords().getFirst()));
        when(medicalrecordService.getMedicalrecordsByName(FIRST_NAME_2, LAST_NAME)).thenReturn(List.of(datas.getMedicalRecords().get(1)));
        List<ChildInfoDto> result = personService.getChildsByAddress(ADDRESS);

        assertEquals(1, result.size());
        assertEquals(1, result.getFirst().getFamilyMembers().size());
    }

    @Test
    void getPersonInfoOk() throws NotFoundException {
        when(medicalrecordService.getMedicalrecordsByName(anyString(), anyString())).thenReturn(List.of(datas.getMedicalRecords().getFirst()));
        List<PersonInfoExtendsMailDto> result = personService.getPersonInfoExtendsByFirstAndLastName(FIRST_NAME, LAST_NAME);

        assertEquals(1, result.size());
        assertEquals(result.getFirst().getEmail(), MAIL_ADDRESS);
        assertEquals(result.getFirst().getMedications(), MEDICATIONS);
        assertEquals(result.getFirst().getAllergies(), ALLERGIES);
    }

    @Test
    void getMailsByCity() {
        List<String> result = personService.getMailsByCity(CITY);

        assertEquals(2, result.size());
        assertEquals(result.getFirst(), MAIL_ADDRESS);
        assertEquals(result.get(1), MAIL_ADDRESS_2);
    }
}
