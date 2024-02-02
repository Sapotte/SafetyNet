package com.openclassroom.SafetyNet.repositories.models;

public interface MedicalRecordRepository {
    public abstract void saveMedicalRecord(MedicalRecordRepository medicalRecord);
    public abstract void updateMedicalRecord(MedicalRecordRepository medicalRecord);
    public abstract void deleteMedicalRecord(MedicalRecordRepository medicalRecord);
}
