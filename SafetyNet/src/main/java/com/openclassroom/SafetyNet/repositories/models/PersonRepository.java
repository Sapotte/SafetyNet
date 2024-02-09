package com.openclassroom.SafetyNet.repositories.models;

import com.openclassroom.SafetyNet.model.Person;

public interface PersonRepository {
    void savePerson(Person person);
    void updatePerson(int index, Person person);
    void deletePerson(int index);
}
