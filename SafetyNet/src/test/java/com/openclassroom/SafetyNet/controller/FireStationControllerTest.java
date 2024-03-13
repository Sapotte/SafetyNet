package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.dto.PersonsCoveredByFirestationDto;
import com.openclassroom.SafetyNet.service.FireStationService;
import com.openclassroom.SafetyNet.utils.errors.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static com.openclassroom.SafetyNet.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireStationControllerTest {
    @InjectMocks
    private static FireStationController fireStationController;
    @Mock
    private static FireStationService fireStationService;
    @Test
    void saveFirestationOk() {
        when(fireStationService.addFireStation(anyString(), anyString())).thenReturn(FIRESTATION_ID);

        fireStationController.addFireStation(ADDRESS, FIRESTATION_ID);

        verify(fireStationService, times(1)).addFireStation(ADDRESS, FIRESTATION_ID);
    }

    @Test
    void updateFirestationOk() throws NotFoundException {
        fireStationController.updateFireStationNumber(ADDRESS, FIRESTATION_ID);

        verify(fireStationService, times(1)).updateFireStation(ADDRESS, FIRESTATION_ID);
    }

    @Test
    void updateFirestationNotFoundKo() throws NotFoundException {
        doThrow(NotFoundException.class).when(fireStationService).updateFireStation(anyString(), anyString());

        var response  = fireStationController.updateFireStationNumber(WRONG_ADDRESS, FIRESTATION_ID);

        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(404));
    }

    @Test
    void deleteFirestationOk() throws Exception {

        fireStationController.deleteFireStation(ADDRESS);

        verify(fireStationService, times(1)).deleteFirestationByAddress(ADDRESS);
    }

    @Test
    void getPersonsCoveredByFirestationOk() {
        PersonsCoveredByFirestationDto mockResult = new PersonsCoveredByFirestationDto();
        mockResult.setPersonsBasicInfosDtoList(Collections.emptyList());
        when(fireStationService.getPersonCoveredByFirestation(Integer.parseInt(FIRESTATION_ID))).thenReturn(mockResult);

        ResponseEntity<PersonsCoveredByFirestationDto> response = fireStationController.getPersonCoveredByFirestation(Integer.parseInt(FIRESTATION_ID));

        verify(fireStationService, times(1)).getPersonCoveredByFirestation(Integer.parseInt(FIRESTATION_ID));
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
    }
}
