package com.openclassroom.SafetyNet.service;

import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.Medicalrecord;
import com.openclassroom.SafetyNet.repositories.models.MedicalRecordRepository;
import com.openclassroom.SafetyNet.utils.errors.InvalidNumberOfMatches;
import com.openclassroom.SafetyNet.utils.errors.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.IntStream;


@Service
public class MedicalrecordService {
    private final Logger logger = LogManager.getLogger(MedicalrecordService.class);
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

    public void updateMedicalRecord(Medicalrecord medicalrecord) throws InvalidNumberOfMatches {
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
            throw new InvalidNumberOfMatches(MessageFormat.format("Their is {0} matches for {1} {2}", indexList.size(), medicalrecord.getFirstName(), medicalrecord.getLastName()));
        }
    }

    public void deleteMedicalRecord(String firstName, String lastName) throws InvalidNumberOfMatches {
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
            throw new InvalidNumberOfMatches(MessageFormat.format("Their is {0} matches for {1} {2}", indexList.size(), firstName, lastName));
        }
    }

    public List<Medicalrecord> getMedicalrecordsByName(String firstName, String lastName) {
        List<Medicalrecord> medicalrecordList = datas.getMedicalRecords().stream().filter(medicalrecord -> firstName.equals(medicalrecord.getFirstName()) && lastName.equals(medicalrecord.getLastName())).toList();
        try {
            if(medicalrecordList.isEmpty()) {
                throw new NotFoundException(MessageFormat.format("No medicalrecord found for {0} {1}", firstName, lastName));
            } else {
                return medicalrecordList;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
