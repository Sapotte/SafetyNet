package com.openclassroom.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Medicalrecord;
import com.openclassroom.SafetyNet.repositories.models.MedicalRecordRepository;
import com.openclassroom.SafetyNet.utils.errors.InvalidNumberOfMatches;
import com.openclassroom.SafetyNet.utils.errors.NotFoundException;
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

import static com.openclassroom.SafetyNet.utils.Constants.*;
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
    void updateMedicalRecordOk() throws InvalidNumberOfMatches {
        Medicalrecord newMedicalRecord = datas.getMedicalRecords().get(0);
        newMedicalRecord.setBirthdate("01/01/1990");
        newMedicalRecord.setAllergies(List.of("allergy1", "allergy2"));

        medicalRecordService.updateMedicalRecord(newMedicalRecord);

        verify(medicalRecordRepository, times(1)).updateMedicalRecord(anyInt(), any(Medicalrecord.class));
        assertEquals(datas.getMedicalRecords().getFirst(), newMedicalRecord);
    }

    @Test
    void updateMedicalRecordNotFoundKo() {
        assertThrows(InvalidNumberOfMatches.class, () -> medicalRecordService.updateMedicalRecord(medicalrecord));
        verify(medicalRecordRepository, times(0)).updateMedicalRecord(anyInt(), any(Medicalrecord.class));
    }
    @Test
    void deleteMedicalRecordOk() throws InvalidNumberOfMatches {
        Medicalrecord medicalrecordToDelete = datas.getMedicalRecords().getFirst();
        medicalRecordService.deleteMedicalRecord(medicalrecordToDelete.getFirstName(), medicalrecordToDelete.getLastName());

        verify(medicalRecordRepository, times(1)).deleteMedicalRecord(0);
    }

    @Test
    void deleteMedicalRecordNotFoundKo() {
        assertThrows(InvalidNumberOfMatches.class, () -> medicalRecordService.deleteMedicalRecord(medicalrecord.getFirstName(), medicalrecord.getLastName()));
        verify(medicalRecordRepository, times(0)).deleteMedicalRecord(anyInt());
    }
    @Test
    void getMedicalrecordsByNameOk() throws NotFoundException {
        var response = medicalRecordService.getMedicalrecordsByName(FIRST_NAME, LAST_NAME);

        assertEquals(1, response.size());
        assertEquals(MEDICATIONS, response.getFirst().getMedications());
        assertEquals(ALLERGIES, response.getFirst().getAllergies());
        assertEquals(BIRTHDATE, response.getFirst().getBirthdate());
    }

    @Test
    void getMedicalrecordsByNameNotFoundKo() throws NotFoundException {
        assertThrows(NotFoundException.class, () -> medicalRecordService.getMedicalrecordsByName(WRONG_FIRST_NAME, WRONG_LAST_NAME));
    }
}
