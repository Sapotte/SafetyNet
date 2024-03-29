package com.openclassroom.SafetyNet.controller;

import com.openclassroom.SafetyNet.model.Medicalrecord;
import com.openclassroom.SafetyNet.service.MedicalrecordService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InvalidAttributeValueException;
import java.text.MessageFormat;

@RestController
@RequestMapping("medicalrecord")
public class MedicalRecordController {
    private final Logger logger = LogManager.getLogger(MedicalrecordService.class);

    @Autowired
    private MedicalrecordService medicalRecordService;

    @PostMapping
    @Operation(summary = "Add a medical record")
    public ResponseEntity<String> addMedicalRecord(@RequestBody Medicalrecord medicalrecord) throws InvalidAttributeValueException {
        medicalRecordService.addMedicalRecord(medicalrecord);
        logger.info(MessageFormat.format("The medical record of {0} {1} has been saved", medicalrecord.getFirstName(), medicalrecord.getLastName()));
        return new ResponseEntity<>("Medicalrecord added", HttpStatusCode.valueOf(201));
    }

    @PutMapping
    @Operation(summary = "Update a medical record by first and last name")
    public ResponseEntity<String> updateMedicalRecord(@RequestBody Medicalrecord medicalrecord) {
        try {
            medicalRecordService.updateMedicalRecord(medicalrecord);
            logger.info(MessageFormat.format("The medical record of {0} {1} has been successfully updated", medicalrecord.getFirstName(), medicalrecord.getLastName()));
            return new ResponseEntity<>("Medicalrecord updated", HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating medicalrecord : " + e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @DeleteMapping
    @Operation(summary = "Delete a medical record by first and last name")
    public ResponseEntity<String> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) throws InvalidAttributeValueException {
        try {
            medicalRecordService.deleteMedicalRecord(firstName, lastName);
            logger.info(MessageFormat.format("The medical record of {0} {1} has been successfully updated", firstName, lastName));
            return new ResponseEntity<>("Medicalrecord deleted", HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting medicalrecord", HttpStatusCode.valueOf(404));
        }

    }
}
