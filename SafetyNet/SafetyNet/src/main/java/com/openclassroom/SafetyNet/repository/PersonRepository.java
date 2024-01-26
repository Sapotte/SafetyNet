package com.openclassroom.SafetyNet.repository;

import com.openclassroom.SafetyNet.model.Person;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAll();

    @NotNull
    Person save(Person person);

    Long findByFirstNameAndLastName(String firstName, String lastName);
    void deleteById(Long id);

}
