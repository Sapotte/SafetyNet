package com.openclassroom.SafetyNet.repositories.models;

import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.model.Persons;

public interface PersonRepository {
    public abstract void savePerson(Person person);
    public abstract void updatePerson(Person person);
    public abstract void deletePerson(Person person);
}
