package com.openclassroom.SafetyNet.repositories.models;

public interface MedicalRecordRepository {
    void saveMedicalRecord(MedicalRecordRepository medicalRecord);
    void updateMedicalRecord(MedicalRecordRepository medicalRecord);
    void deleteMedicalRecord(MedicalRecordRepository medicalRecord);
}
