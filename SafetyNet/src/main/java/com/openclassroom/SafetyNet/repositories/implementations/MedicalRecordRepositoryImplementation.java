package com.openclassroom.SafetyNet.repositories.implementations;

import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Medicalrecord;
import com.openclassroom.SafetyNet.repositories.models.MedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;

@Repository
public class MedicalRecordRepositoryImplementation implements MedicalRecordRepository {
    private static final Logger logger = LogManager.getLogger(MedicalRecordRepository.class);
    @Autowired
    public Datas datas;
    @Override
    public void saveMedicalRecord(Medicalrecord medicalRecord) {
        datas.getMedicalRecords().add(medicalRecord);
        logger.info(MessageFormat.format("Medicalrecord of {0} {1} has been added", medicalRecord.getFirstName(), medicalRecord.getLastName()));
    }

    @Override
    public void updateMedicalRecord(int index, Medicalrecord newMedicalRecord) {
        datas.getMedicalRecords().set(index, newMedicalRecord);
        logger.info(MessageFormat.format("Medicalrecord of {0} {1} has been updated", newMedicalRecord.getFirstName(), newMedicalRecord.getLastName()));
    }

    @Override
    public void deleteMedicalRecord(int index) {
        datas.getMedicalRecords().remove(index);
        logger.info("Medicalrecord deleted");
    }
}
