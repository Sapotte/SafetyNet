package com.openclassroom.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.dto.PersonsCoveredByFirestation;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.repositories.models.FireStationRepository;
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
import java.util.NoSuchElementException;

import static com.openclassroom.SafetyNet.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {
    @InjectMocks
    FireStationService fireStationService;
    @Mock
    FireStationRepository fireStationRepository;

    @Mock
    Datas datas;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        datas = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/DatasTest.json"), Datas.class);

        fireStationService.datas = datas;

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
    void getPersonsCoveredByFirestationOk() throws InvalidAttributeValueException {
        PersonsCoveredByFirestation result = fireStationService.getPersonCoveredByFirestation(Integer.parseInt(FIRESTATION_ID));

        assertTrue(result.getAdultsCount() == 1);
        assertTrue(result.getChildsCount() == 1);
        assertTrue(result.getPersonsBasicInfosList().size() == 2);
    }

    @Test
    void getPersonsCoveredByFirestationNoStationFound() throws InvalidAttributeValueException {

        assertThrows(NoSuchElementException.class, () -> fireStationService.getPersonCoveredByFirestation(Integer.parseInt(FIRESTATION_WRONG_ID)));

    }
    @Test
    void getPhoneNumbersByFirestationOk() {
        List<String> result = fireStationService.getPhoneNumbersByFirestation(FIRESTATION_ID);

        assertTrue(result.size() == 2);
    }
}
