package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.openclassroom.SafetyNet.utils.Constants.FIRESTATION_ADDRESS;
import static com.openclassroom.SafetyNet.utils.Constants.FIRESTATION_ID;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FireStationControllerTest {
    @InjectMocks
    private static FireStationController fireStationController;
    @Mock
    private static FireStationService fireStationService;
    @Test
    void saveFirestationOk() throws Exception {
        Mockito.when(fireStationService.addFireStation(anyString(), anyString())).thenReturn(FIRESTATION_ID);

        fireStationController.addFireStation(FIRESTATION_ADDRESS, FIRESTATION_ID);

        verify(fireStationService, times(1)).addFireStation(FIRESTATION_ADDRESS, FIRESTATION_ID);
    }

    @Test
    void deleteFirestationOk() throws Exception {

        fireStationController.deleteFireStation(FIRESTATION_ADDRESS);

        verify(fireStationService, times(1)).deleteFirestationByAddress(FIRESTATION_ADDRESS);
    }
}
