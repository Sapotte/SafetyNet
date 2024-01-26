package com.openclassroom.SafetyNet.repository;

import com.openclassroom.SafetyNet.model.Firestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirestationRepository extends JpaRepository<Firestation, Long> {
    List<Firestation> findByStation(String station);

}
