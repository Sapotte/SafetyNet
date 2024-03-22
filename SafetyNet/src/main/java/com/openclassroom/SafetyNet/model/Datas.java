package com.openclassroom.SafetyNet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class Datas {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ObjectMapper mapper = new ObjectMapper();
    private final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    private final File datasFile = new File("src/main/resources/Datas.json");
    @JsonProperty("firestations")
    private List<FireStation> fireStations;

    @JsonProperty("persons")
    private List<Person> persons;
    @JsonProperty("medicalrecords")
    private List<Medicalrecord> medicalRecords;

    public List<FireStation> getFireStations() {
        return fireStations;
    }

    public void setFireStations(List<FireStation> fireStations) {
        this.fireStations = fireStations;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Medicalrecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<Medicalrecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    @Bean
    public void setDatas() throws IOException {
        Datas datas = mapper.readValue(datasFile, Datas.class);
        setFireStations(datas.getFireStations());
        setMedicalRecords(datas.getMedicalRecords());
        setPersons(datas.getPersons());
        LOGGER.info("Datas deserialized");
    }

    @Bean
    public void saveDatas() throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Datas newDatas = new Datas();
                newDatas.setPersons(getPersons());
                newDatas.setFireStations(getFireStations());
                newDatas.setMedicalRecords(getMedicalRecords());
                writer.writeValue(datasFile, newDatas);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

}
