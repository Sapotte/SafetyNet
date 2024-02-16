package com.openclassroom.SafetyNet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Medicalrecord;
import com.openclassroom.SafetyNet.repositories.implementations.MedicalRecordRepositoryImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static com.openclassroom.SafetyNet.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MedicalrecordRepositoryImplTest {
    @InjectMocks
    MedicalRecordRepositoryImplementation medicalRecordRepository;
    @Mock
    Datas datas;
    private final ObjectMapper mapper = new ObjectMapper();
    Medicalrecord medicalrecord;
    @BeforeEach
    void setUp() throws IOException {
        datas = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/DatasTest.json"), Datas.class);

        medicalRecordRepository.datas = datas;
        medicalrecord = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/MedicalRecord.json"), Medicalrecord.class);
    }

    @Test
    void saveMedicalrecordOk() {
        medicalRecordRepository.saveMedicalRecord(medicalrecord);

        assertTrue(datas.getMedicalRecords().contains(medicalrecord));
    }

    @Test
    void updateFirestationOk() {
        Medicalrecord medicalrecordUpdated = datas.getMedicalRecords().getFirst();
        medicalrecordUpdated.setBirthdate(BIRTHDATE);
        medicalrecordUpdated.setAllergies(ALLERGIES);
        medicalrecordUpdated.setMedications(MEDICATIONS);

        medicalRecordRepository.updateMedicalRecord(0, medicalrecordUpdated);

        assertEquals(BIRTHDATE, datas.getMedicalRecords().get(0).getBirthdate());
    assertEquals(ALLERGIES, datas.getMedicalRecords().get(0).getAllergies());
        assertEquals(MEDICATIONS, datas.getMedicalRecords().get(0).getMedications());
    }
    @Test
    void deleteFirestationOk() {
        Medicalrecord medicalrecordToDelete = datas.getMedicalRecords().getFirst();

        medicalRecordRepository.deleteMedicalRecord(0);

        assertFalse(datas.getMedicalRecords().contains(medicalrecordToDelete));
    }
}
