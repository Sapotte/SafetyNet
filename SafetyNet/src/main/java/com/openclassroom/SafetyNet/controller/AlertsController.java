package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.dto.ChildInfo;
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
    public ResponseEntity<List<ChildInfo>> getChildsInfosByAddress(@RequestParam String address) {
        List<ChildInfo> childInfos = personService.getChildsByAddress(address);
        return new ResponseEntity<>(childInfos, HttpStatusCode.valueOf(200));
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

}
