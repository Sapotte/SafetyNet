package com.openclassroom.SafetyNet.service;

import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Medicalrecord;
import com.openclassroom.SafetyNet.repositories.models.MedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.text.MessageFormat;
import java.util.stream.IntStream;


@Service
public class MedicalRecordService {
    private final Logger logger = LogManager.getLogger(MedicalRecordService.class);
    @Autowired
    Datas datas;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;
    public void addMedicalRecord(Medicalrecord medicalrecord) throws InvalidAttributeValueException {
        // Check if the person is not already in the list
        if (datas.getMedicalRecords().stream().anyMatch(medicalrecordItem -> medicalrecordItem.getFirstName().equals(medicalrecord.getFirstName()) && medicalrecordItem.getLastName().equals(medicalrecord.getLastName()))) {
            logger.error("Medical record already in the list");
            throw new InvalidAttributeValueException(MessageFormat.format("Medical record for {0} {1} is already in the list", medicalrecord.getFirstName(), medicalrecord.getLastName()));
        }
        logger.debug("Save person");
        medicalRecordRepository.saveMedicalRecord(medicalrecord);
    }

    public void updateMedicalRecord(Medicalrecord medicalrecord) throws InvalidAttributeValueException {
        // Find the index of the person to be updated
        var indexList = IntStream.range(0, datas.getMedicalRecords().size())
                .filter(i -> datas.getMedicalRecords().get(i).getFirstName().equals(medicalrecord.getFirstName()) && datas.getMedicalRecords().get(i).getLastName().equals(medicalrecord.getLastName()))
                .boxed()
                .toList();
        if(indexList.size() == 1) {
            logger.debug(MessageFormat.format("Medicalrecord at index {0} to be updated", indexList.getFirst()));
            medicalRecordRepository.updateMedicalRecord(indexList.getFirst(), medicalrecord);
        } else {
            logger.error("Invalid number of matches in the list for this medicalrecord");
            throw new InvalidAttributeValueException(MessageFormat.format("Their is {0} matches for {1} {2}", indexList.size(), medicalrecord.getFirstName(), medicalrecord.getLastName()));
        }
    }

    public void deleteMedicalRecord(String firstName, String lastName) throws InvalidAttributeValueException {
        // Find the index of the person to be deleted
        var indexList = IntStream.range(0, datas.getPersons().size())
                .filter(i -> datas.getMedicalRecords().get(i).getFirstName().equals(firstName) && datas.getMedicalRecords().get(i).getLastName().equals(lastName))
                .boxed()
                .toList();
        if(indexList.size() == 1) {
            logger.debug(MessageFormat.format("Medicalrecord at index {0} to be deleted", indexList.getFirst()));
            medicalRecordRepository.deleteMedicalRecord(indexList.getFirst());
        } else {
            logger.error("Invalid number of matches in the list for this person");
            throw new InvalidAttributeValueException(MessageFormat.format("Their is {0} matches for {1} {2}", indexList.size(), firstName, lastName));
        }
    }
}
