package com.openclassroom.SafetyNet.repositories.models;

import com.openclassroom.SafetyNet.model.Person;

public interface PersonRepository {
    void savePerson(Person person);
    void updatePerson(Person person);
    void deletePerson(Person person);
}
