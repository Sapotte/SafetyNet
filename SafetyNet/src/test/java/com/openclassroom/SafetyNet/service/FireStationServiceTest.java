package com.openclassroom.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.repositories.models.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static com.openclassroom.SafetyNet.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {
    @InjectMocks
    FireStationService fireStationService;
    @Mock
    FireStationRepository fireStationRepository;

    @Mock
    Datas datas;

    private FireStation fireStation;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        fireStation = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/FireStation.json"), FireStation.class);
        datas = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/DatasTest.json"), Datas.class);

    }
    @Test
    void addFireStationOk() {
        fireStationService.addFireStation(FIRESTATION_ADDRESS, FIRESTATION_ID);

        verify(fireStationRepository, times(1)).saveFirestation(any(FireStation.class));
    }

    @Test
    void deleteFireStationByAddressOk() throws ClassNotFoundException {
        fireStationService.datas = datas;

        fireStationService.deleteFirestationByAddress(FIRESTATION_ADDRESS);

        verify(fireStationRepository, times(1)).deleteFirestation(anyInt());
    }

    @Test
    void deleteFireStationByWrongAddressKo() throws ClassNotFoundException {
        fireStationService.datas = datas;

        assertThrows(ClassNotFoundException.class, () -> fireStationService.deleteFirestationByAddress(FIRESTATION_WRONG_ADDRESS));
        verify(fireStationRepository, times(0)).deleteFirestation(anyInt());
    }
}
