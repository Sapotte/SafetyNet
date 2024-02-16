package com.openclassroom.SafetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.model.Medicalrecord;
import com.openclassroom.SafetyNet.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.InvalidAttributeValueException;
import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {

    @InjectMocks
    MedicalRecordController medicalRecordController;

    @Mock
    MedicalRecordService medicalRecordService;
    Medicalrecord medicalrecord;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        medicalrecord = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/MedicalRecord.json"), Medicalrecord.class);
    }

    @Test
    void saveMedicalrecordOk() throws InvalidAttributeValueException {
        medicalRecordController.addMedicalRecord(medicalrecord);

        verify(medicalRecordService, times(1)).addMedicalRecord(medicalrecord);
    }

    @Test
    void updateMedicalrecordOk() throws InvalidAttributeValueException {
        medicalRecordController.updateMedicalRecord(medicalrecord);

        verify(medicalRecordService, times(1)).updateMedicalRecord(medicalrecord);
    }

    @Test
    void deleteMedicalrecordOk() throws Exception {
        medicalRecordController.deleteMedicalRecord(medicalrecord.getFirstName(), medicalrecord.getLastName());

        verify(medicalRecordService, times(1)).deleteMedicalRecord(medicalrecord.getFirstName(), medicalrecord.getLastName());
    }
}
