package com.openclassroom.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.dto.PersonInfoExtendsDto;
import com.openclassroom.SafetyNet.dto.PersonsByAddressDto;
import com.openclassroom.SafetyNet.dto.PersonsCoveredByFirestationDto;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.model.Medicalrecord;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repositories.models.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.openclassroom.SafetyNet.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {
    @InjectMocks
    FireStationService fireStationService;
    @Mock
    FireStationRepository fireStationRepository;
    @Mock
    PersonService personService;
    @Mock
    MedicalrecordService medicalrecordService;

    @Mock
    Datas datas;

    private List<Person> personsAtAddress;
    private List<Medicalrecord> medicalrecordList1;
    private List<Medicalrecord> medicalrecordList2;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        datas = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/DatasTest.json"), Datas.class);
        fireStationService.datas = datas;
        personsAtAddress = List.of(datas.getPersons().get(0), datas.getPersons().get(1));
        medicalrecordList1 = List.of(datas.getMedicalRecords().get(0));
        medicalrecordList2 = List.of(datas.getMedicalRecords().get(2));
    }
    @Test
    void addFireStationOk() {
        fireStationService.addFireStation(FIRESTATION_ADDRESS, FIRESTATION_ID);

        verify(fireStationRepository, times(1)).saveFirestation(any(FireStation.class));
    }

    @Test
    void updateFireStationOk() {

        fireStationService.updateFireStation(FIRESTATION_ADDRESS, FIRESTATION_ID);

        verify(fireStationRepository, times(1)).updateFirestation(0, FIRESTATION_ID);
    }

    @Test
    void updateFireStationByWrongAddressKo() {
        assertThrows(NullPointerException.class, () -> fireStationService.updateFireStation(FIRESTATION_WRONG_ADDRESS, FIRESTATION_ID));
        verify(fireStationRepository, times(0)).updateFirestation(anyInt(), anyString());
    }

    @Test
    void deleteFireStationByAddressOk() throws ClassNotFoundException {

        fireStationService.deleteFirestationByAddress(FIRESTATION_ADDRESS);

        verify(fireStationRepository, times(1)).deleteFirestation(anyInt());
    }

    @Test
    void deleteFireStationByWrongAddressKo() {

        assertThrows(ClassNotFoundException.class, () -> fireStationService.deleteFirestationByAddress(FIRESTATION_WRONG_ADDRESS));
        verify(fireStationRepository, times(0)).deleteFirestation(anyInt());
    }

    @Test
    void getPersonsCoveredByFirestationOk() {
        when(personService.getPersonsByAddresses(anyList())).thenReturn(personsAtAddress);
        when(medicalrecordService.getMedicalrecordsByName(FIRST_NAME, LAST_NAME)).thenReturn(medicalrecordList1);
        when(medicalrecordService.getMedicalrecordsByName(FIRST_NAME_2, LAST_NAME)).thenReturn(medicalrecordList2);
        PersonsCoveredByFirestationDto result = fireStationService.getPersonCoveredByFirestation(Integer.parseInt(FIRESTATION_ID));

        assertEquals(1, result.getAdultsCount());
        assertEquals(1, result.getChildsCount());
        assertEquals(2, result.getPersonsBasicInfosDtoList().size());
    }

    @Test
    void getPersonsCoveredByFirestationNoStationFound() {

        assertThrows(NoSuchElementException.class, () -> fireStationService.getPersonCoveredByFirestation(Integer.parseInt(FIRESTATION_WRONG_ID)));

    }
    @Test
    void getPhoneNumbersByFirestationOk() {
        when(personService.getPersonsByAddresses(anyList())).thenReturn(personsAtAddress);

        List<String> result = fireStationService.getPhoneNumbersByFirestation(FIRESTATION_ID);

        assertEquals(2, result.size());
    }

    @Test
    void getPersonsByAddressOk() {
        when(personService.getPersonsByAddresses(anyList())).thenReturn(personsAtAddress);
        when(medicalrecordService.getMedicalrecordsByName(FIRST_NAME, LAST_NAME)).thenReturn(medicalrecordList1);
        when(medicalrecordService.getMedicalrecordsByName(FIRST_NAME_2, LAST_NAME)).thenReturn(medicalrecordList2);
        PersonsByAddressDto result = fireStationService.getPersonsByAddress(FIRESTATION_ADDRESS);

        assertEquals(FIRESTATION_ID, result.getStation());
        assertEquals(FIRST_NAME, result.getPersonInfoExtendsDtoList().getFirst().getFirstName());
        assertEquals(MEDICATIONS, result.getPersonInfoExtendsDtoList().getFirst().getMedications()) ;
        assertEquals(List.of(), result.getPersonInfoExtendsDtoList().getFirst().getAllergies());
    }

    @Test
    void getAddressesAndResidentsOk() {
        Map<String, Map<String, List<PersonInfoExtendsDto>>> result = fireStationService.getAddressesAndTheirResidentsCoveredByStations(List.of(FIRESTATION_ID));

        assertTrue(result.get(FIRESTATION_ID).containsKey(FIRESTATION_ADDRESS));
        assertEquals(FIRST_NAME, result.get(FIRESTATION_ID).get(FIRESTATION_ADDRESS).getFirst().getFirstName());
        assertEquals(MEDICATIONS, result.get(FIRESTATION_ID).get(FIRESTATION_ADDRESS).getFirst().getMedications()) ;
        assertEquals(List.of(), result.get(FIRESTATION_ID).get(FIRESTATION_ADDRESS).getFirst().getAllergies());
    }
}
