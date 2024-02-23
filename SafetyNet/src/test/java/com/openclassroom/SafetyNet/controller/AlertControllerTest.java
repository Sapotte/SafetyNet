package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.dto.ChildInfo;
import com.openclassroom.SafetyNet.service.FireStationService;
import com.openclassroom.SafetyNet.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.openclassroom.SafetyNet.utils.Constants.FIRESTATION_ADDRESS;
import static com.openclassroom.SafetyNet.utils.Constants.FIRESTATION_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertControllerTest {
    @InjectMocks
    AlertsController alertsController;
    @Mock
    PersonService personService;
    @Mock
    FireStationService fireStationService;

    @Test
    void getChildsInfoByAddressOk() {
        List<ChildInfo> mockResult = List.of(new ChildInfo());
        when(personService.getChildsByAddress(FIRESTATION_ADDRESS)).thenReturn(mockResult);

        ResponseEntity<List<ChildInfo>> response = alertsController.getChildsInfosByAddress(FIRESTATION_ADDRESS);

        verify(personService, times(1)).getChildsByAddress(FIRESTATION_ADDRESS);
        assertEquals(mockResult, response.getBody());
    }

    @Test
    void getPhoneNumbersCoveredByFirestationOk() {
        List<String> mockResult = List.of(anyString());
        when(fireStationService.getPhoneNumbersByFirestation(FIRESTATION_ID)).thenReturn(mockResult);

        ResponseEntity<List<String>> response = alertsController.getPhoneNumbersByFirestation(FIRESTATION_ID);

        verify(fireStationService, times(1)).getPhoneNumbersByFirestation(FIRESTATION_ID);
        assertEquals(mockResult, response.getBody());
    }

}
