package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.dto.ChildInfoDto;
import com.openclassroom.SafetyNet.dto.PersonInfoExtendsDto;
import com.openclassroom.SafetyNet.dto.PersonInfoExtendsMailDto;
import com.openclassroom.SafetyNet.dto.PersonsByAddressDto;
import com.openclassroom.SafetyNet.service.FireStationService;
import com.openclassroom.SafetyNet.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.openclassroom.SafetyNet.utils.Constants.*;
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
        List<ChildInfoDto> mockResult = List.of(new ChildInfoDto());
        when(personService.getChildsByAddress(ADDRESS)).thenReturn(mockResult);

        ResponseEntity<List<ChildInfoDto>> response = alertsController.getChildsInfosByAddress(ADDRESS);

        verify(personService, times(1)).getChildsByAddress(ADDRESS);
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
    @Test
    void getPersonsByAddressOk() {
        PersonsByAddressDto mockResult = new PersonsByAddressDto();
        when(fireStationService.getPersonsByAddress(ADDRESS)).thenReturn(mockResult);

        ResponseEntity<PersonsByAddressDto> response = alertsController.getPersonsByAddress(ADDRESS);

        verify(fireStationService, times(1)).getPersonsByAddress(ADDRESS);
        assertEquals(mockResult, response.getBody());
    }

    @Test
    void getAddressesAndTheirResidentsOk() {
        Map<String, Map<String, List<PersonInfoExtendsDto>>> mockResult = new HashMap<>();
        when(fireStationService.getAddressesAndTheirResidentsCoveredByStations(List.of(FIRESTATION_ID))).thenReturn(mockResult);

        ResponseEntity<Map<String, Map<String, List<PersonInfoExtendsDto>>>> response = alertsController.getAddressesAndTheirResidentsDeservedByStations(List.of(FIRESTATION_ID));

        verify(fireStationService, times(1)).getAddressesAndTheirResidentsCoveredByStations(List.of(FIRESTATION_ID));
        assertEquals(mockResult, response.getBody());
    }

    @Test
    void getPersonInfosOk() {
        List<PersonInfoExtendsMailDto> mockResult = new ArrayList<>();
        when(personService.getPersonInfoExtendsByFirstAndLastName(FIRST_NAME, LAST_NAME)).thenReturn(mockResult);

        ResponseEntity<List<PersonInfoExtendsMailDto>> response = alertsController.getPersonInfos(FIRST_NAME, LAST_NAME);

        verify(personService, times(1)).getPersonInfoExtendsByFirstAndLastName(FIRST_NAME, LAST_NAME);
        assertEquals(mockResult, response.getBody());
    }

    @Test
    void getMailsByCityOk() {
        List<String> mockResult = new ArrayList<>();
        when(personService.getMailsByCity(CITY)).thenReturn(mockResult);

        ResponseEntity<List<String>> response = alertsController.getMailsByCity(CITY);

        verify(personService, times(1)).getMailsByCity(CITY);
        assertEquals(mockResult, response.getBody());
    }

}
