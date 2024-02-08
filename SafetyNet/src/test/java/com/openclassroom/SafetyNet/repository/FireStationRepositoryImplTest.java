package com.openclassroom.SafetyNet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.repositories.implementations.FireStationRepositoryImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FireStationRepositoryImplTest {
    @InjectMocks
    FireStationRepositoryImplementation fireStationRepository;
    @Mock
    Datas datas;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        datas = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/DatasTest.json"), Datas.class);

        fireStationRepository.datas = datas;
    }

    @Test
    void saveFirestationOk() {
        FireStation newFireStation = new FireStation();
        newFireStation.setStation("4");
        newFireStation.setAddress("4 Jkl Str");

        String result = fireStationRepository.saveFirestation(newFireStation);

        assertEquals("4", result );
        assertTrue(datas.getFireStations().contains(newFireStation));
    }

    @Test
    void updateFirestationOk() {

        fireStationRepository.updateFirestation(0, "10");

        assertEquals("10", datas.getFireStations().get(0).getStation());
    }

    @Test
    void deleteFirestationOk() {
        FireStation firstFirestation = datas.getFireStations().getFirst();

        fireStationRepository.deleteFirestation(0);

        assertFalse(datas.getFireStations().contains(firstFirestation));
    }

}
