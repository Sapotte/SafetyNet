package com.openclassroom.SafetyNet.repositories.models;

import com.openclassroom.SafetyNet.model.Medicalrecord;

public interface MedicalRecordRepository {
    void saveMedicalRecord(Medicalrecord medicalRecord);
    void updateMedicalRecord(int index, Medicalrecord medicalRecord);
    void deleteMedicalRecord(int index);
}
