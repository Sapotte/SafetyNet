package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.dto.ChildInfoDto;
import com.openclassroom.SafetyNet.dto.PersonInfoExtendsDto;
import com.openclassroom.SafetyNet.dto.PersonInfoExtendsMailDto;
import com.openclassroom.SafetyNet.dto.PersonsByAddressDto;
import com.openclassroom.SafetyNet.service.FireStationService;
import com.openclassroom.SafetyNet.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AlertsController {
    private final Logger logger = LogManager.getLogger(AlertsController.class);

    @Autowired
    private PersonService personService;
    @Autowired
    private FireStationService fireStationService;

    @GetMapping("/childAlert")
    @Operation(summary = "Get list of children at the given address",
            description = "Returns a list of children (aged 18 or below) living at the specified address. The list includes the first name, last name, age, and a list of other household members.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Children found successfully")
    })
    public ResponseEntity<List<ChildInfoDto>> getChildsInfosByAddress(@RequestParam String address) {
        List<ChildInfoDto> childInfoDtos = personService.getChildsByAddress(address);
        return new ResponseEntity<>(childInfoDtos, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/phoneAlert")
    @Operation(summary = "Get the phone numbers of persons living at an address covered by the firestation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone numbers found successfully")
    })
    public ResponseEntity<List<String>> getPhoneNumbersByFirestation(@RequestParam String firestation) {
        List<String> phoneNumberList = fireStationService.getPhoneNumbersByFirestation(firestation);
        return new ResponseEntity<>(phoneNumberList, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/fire")
    @Operation(summary = "Get persons living at an address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persons found successfully")
    })
    public ResponseEntity<PersonsByAddressDto> getPersonsByAddress(@RequestParam String address) {
        PersonsByAddressDto personsByAddressDto = fireStationService.getPersonsByAddress(address);
        return new ResponseEntity<>(personsByAddressDto, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/flood")
    @Operation(summary = "Get all adresses deserved by a station and the their residents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addresses and residents found successfully")
    })
    public ResponseEntity<Map<String, Map<String, List<PersonInfoExtendsDto>>>> getAddressesAndTheirResidentsDeservedByStations(@RequestParam List<String> stations) {
        Map<String, Map<String, List<PersonInfoExtendsDto>>> addressesAndResidents = fireStationService.getAddressesAndTheirResidentsCoveredByStations(stations);
        return new ResponseEntity<>(addressesAndResidents, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/personInfo")
    @Operation(summary = "Get infos for resident by firstName and lastName ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persons found successfully")
    })
    public ResponseEntity<List<PersonInfoExtendsMailDto>> getPersonInfos(@RequestParam String firstName, @RequestParam String lastName) {
        List<PersonInfoExtendsMailDto> personsInfo = personService.getPersonInfoExtendsByFirstAndLastName(firstName,lastName);
        return new ResponseEntity<>(personsInfo, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/communityEmail")
    @Operation(summary = "Get all mail addresses city's residents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mails found successfully")
    })
    public ResponseEntity<List<String>> getMailsByCity(@RequestParam String city) {
        List<String> mailsList = personService.getMailsByCity(city);
        return new ResponseEntity<>(mailsList, HttpStatusCode.valueOf(200));
    }
}
