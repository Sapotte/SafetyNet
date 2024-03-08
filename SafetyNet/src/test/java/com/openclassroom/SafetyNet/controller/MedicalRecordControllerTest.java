package com.openclassroom.SafetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.model.Medicalrecord;
import com.openclassroom.SafetyNet.service.MedicalrecordService;
import com.openclassroom.SafetyNet.utils.errors.InvalidNumberOfMatches;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import javax.management.InvalidAttributeValueException;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {

    @InjectMocks
    MedicalRecordController medicalRecordController;

    @Mock
    MedicalrecordService medicalRecordService;
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
    void updateMedicalrecordOk() throws InvalidNumberOfMatches {
        medicalRecordController.updateMedicalRecord(medicalrecord);

        verify(medicalRecordService, times(1)).updateMedicalRecord(medicalrecord);
    }

    @Test
    void updateMedicalRecordError() throws InvalidNumberOfMatches {
        doThrow(InvalidNumberOfMatches.class).when(medicalRecordService).updateMedicalRecord(any(Medicalrecord.class));
                var response = medicalRecordController.updateMedicalRecord(medicalrecord);

        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(404));
    }

    @Test
    void deleteMedicalrecordOk() throws Exception {
        medicalRecordController.deleteMedicalRecord(medicalrecord.getFirstName(), medicalrecord.getLastName());

        verify(medicalRecordService, times(1)).deleteMedicalRecord(medicalrecord.getFirstName(), medicalrecord.getLastName());
    }
}
