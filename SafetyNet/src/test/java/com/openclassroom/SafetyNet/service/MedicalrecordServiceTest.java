package com.openclassroom.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Medicalrecord;
import com.openclassroom.SafetyNet.repositories.models.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.InvalidAttributeValueException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalrecordServiceTest {
    @InjectMocks
    MedicalrecordService medicalRecordService;
    @Mock
    MedicalRecordRepository medicalRecordRepository;
    @Mock
    Datas datas;
    private final ObjectMapper mapper =new ObjectMapper();
    Medicalrecord medicalrecord;

    @BeforeEach
    void setUp() throws IOException {
        datas = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/DatasTest.json"), Datas.class);
        medicalRecordService.datas = datas;
        medicalrecord = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/MedicalRecord.json"), Medicalrecord.class);
    }

    @Test
    void addMedicalrecordOk() throws InvalidAttributeValueException {

        medicalRecordService.addMedicalRecord(medicalrecord);

        verify(medicalRecordRepository, times(1)).saveMedicalRecord(any(Medicalrecord.class));
    }

    @Test
    void addMedicalrecordAlreadyExistsKo() {

        assertThrows(InvalidAttributeValueException.class, () -> medicalRecordService.addMedicalRecord(datas.getMedicalRecords().getFirst()));

        verify(medicalRecordRepository, times(0)).saveMedicalRecord(any(Medicalrecord.class));
    }
    @Test
    void updateMedicalRecordOk() throws InvalidAttributeValueException {
        Medicalrecord newMedicalRecord = datas.getMedicalRecords().get(0);
        newMedicalRecord.setBirthdate("01/01/1990");
        newMedicalRecord.setAllergies(List.of("allergy1", "allergy2"));

        medicalRecordService.updateMedicalRecord(newMedicalRecord);

        verify(medicalRecordRepository, times(1)).updateMedicalRecord(anyInt(), any(Medicalrecord.class));
        assertEquals(datas.getMedicalRecords().getFirst(), newMedicalRecord);
    }

    @Test
    void updateMedicalRecordNotFoundKo() {
        assertThrows(InvalidAttributeValueException.class, () -> medicalRecordService.updateMedicalRecord(medicalrecord));
        verify(medicalRecordRepository, times(0)).updateMedicalRecord(anyInt(), any(Medicalrecord.class));
    }
    @Test
    void deleteMedicalRecordOk() throws InvalidAttributeValueException {
        Medicalrecord medicalrecordToDelete = datas.getMedicalRecords().getFirst();
        medicalRecordService.deleteMedicalRecord(medicalrecordToDelete.getFirstName(), medicalrecordToDelete.getLastName());

        verify(medicalRecordRepository, times(1)).deleteMedicalRecord(0);
    }

    @Test
    void deleteMedicalRecordNotFoundKo() {
        assertThrows(InvalidAttributeValueException.class, () -> medicalRecordService.deleteMedicalRecord(medicalrecord.getFirstName(), medicalrecord.getLastName()));
        verify(medicalRecordRepository, times(0)).deleteMedicalRecord(anyInt());
    }
}
