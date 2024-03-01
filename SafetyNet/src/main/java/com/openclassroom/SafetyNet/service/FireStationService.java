package com.openclassroom.SafetyNet.service;

import com.openclassroom.SafetyNet.dto.PersonInfoExtendsDto;
import com.openclassroom.SafetyNet.dto.PersonsByAddressDto;
import com.openclassroom.SafetyNet.dto.PersonsCoveredByFirestationDto;
import com.openclassroom.SafetyNet.model.Datas;
import com.openclassroom.SafetyNet.model.FireStation;
import com.openclassroom.SafetyNet.model.Medicalrecord;
import com.openclassroom.SafetyNet.model.Person;
import com.openclassroom.SafetyNet.repositories.models.FireStationRepository;
import com.openclassroom.SafetyNet.utils.helper.AgeHelper;
import com.openclassroom.SafetyNet.utils.mapper.PersonsInfoExtendsMapper;
import com.openclassroom.SafetyNet.utils.mapper.PersonsInfoExtendsMapperImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FireStationService {
    private static final Logger logger = LogManager.getLogger(FireStationService.class);
    @Autowired
    FireStationRepository fireStationRepository;
    @Autowired
    Datas datas;
    PersonsInfoExtendsMapper personsInfoExtendsMapper = new PersonsInfoExtendsMapperImpl();

    public String addFireStation(String address, String station) {
        FireStation newFirestation = new FireStation();
        newFirestation.setAddress(address);
        newFirestation.setStation(station);
        logger.debug("New object FireStation created");
        return fireStationRepository.saveFirestation(newFirestation);
    }

    public void updateFireStation(String address, String station) {
        var firstationToUpdate = IntStream.range(0, datas.getFireStations().size()).filter(i -> datas.getFireStations().get(i).getAddress().equals(address)).findFirst();
        if (firstationToUpdate.isPresent()) {
            logger.debug(MessageFormat.format("Firestation at {0} to be updated", firstationToUpdate.getAsInt()));
            fireStationRepository.updateFirestation(firstationToUpdate.getAsInt(), station);
        } else {
            logger.error("No station found");
            throw new NullPointerException(MessageFormat.format("No station found at the address {0}", address));
        }
    }

    public void deleteFirestationByAddress(String address) throws ClassNotFoundException {
        var optionalIndex = IntStream.range(0, datas.getFireStations().size()).filter(i -> datas.getFireStations().get(i).getAddress().equals(address)).findFirst();

        if (optionalIndex.isPresent()) {
            int index = optionalIndex.getAsInt();
            logger.debug(MessageFormat.format("Firestation at index {0} to be deleted", optionalIndex));
            fireStationRepository.deleteFirestation(index);
        } else {
            logger.error("No firestation found at this address");
            throw new ClassNotFoundException(MessageFormat.format("There is no station for the address \" {0} \"", address));
        }
    }

    public PersonsCoveredByFirestationDto getPersonCoveredByFirestation(Integer stationNumber) {
        List<String> addressesCoveredByStation = getAddressesCoveredByFirestation(stationNumber.toString());

        if (!addressesCoveredByStation.isEmpty()) {
            List<Person> personCoveredByFirestation = datas.getPersons()
                    .stream()
                    .filter(person -> addressesCoveredByStation.contains(person.getAddress()))
                    .collect(Collectors.toList());

            List<String> birthDates = new ArrayList<>();
            for (Person person : personCoveredByFirestation) {
                birthDates.addAll(datas.getMedicalRecords()
                        .stream()
                        .filter(record -> record.getFirstName().equals(person.getFirstName()) && record.getLastName().equals(person.getLastName()))
                        .map(Medicalrecord::getBirthdate)
                        .toList());
            }

            Map<String, Integer> adultChildCount = AgeHelper.INSTANCE.adultChildCount(birthDates);
            logger.info("Persons found with success");
            return null;
        } else {
            throw new NoSuchElementException(MessageFormat.format("No address covered by stationNumber {0}", stationNumber));
        }
    }

    public List<String> getPhoneNumbersByFirestation(String firestation) {
        List<String> addressesCoveredByStation = getAddressesCoveredByFirestation(firestation);
        return datas.getPersons().stream().filter(person -> addressesCoveredByStation.contains(person.getAddress())).map(Person::getPhone).toList();
    }

    /**
     * Get all the addresses covered by a firestation
     *
     * @param stationNumber
     * @return list of addresses covered by the firestation
     */
    private List<String> getAddressesCoveredByFirestation(String stationNumber) {
        return datas.getFireStations()
                .stream()
                .filter(station -> station.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());
    }

    public PersonsByAddressDto getPersonsByAddress(String address) {
        PersonsByAddressDto personsByAddressDto = new PersonsByAddressDto();
        List<PersonInfoExtendsDto> extendsInfoResidents = getPersonsInfoExtendsByAddress(address);
        personsByAddressDto.setStation(datas.getFireStations().stream().filter(station -> address.equals(station.getAddress())).findAny().get().getStation());
        personsByAddressDto.setPersonInfoExtendsDtoList(extendsInfoResidents);

        return personsByAddressDto;
    }

    private List<PersonInfoExtendsDto> getPersonsInfoExtendsByAddress(String address) {
        List<PersonInfoExtendsDto> extendsInfoResidents = new ArrayList<>();
        List<Person> personsAtAddress = datas.getPersons().stream().filter(person -> address.equals(person.getAddress())).collect(Collectors.toList());
        for (Person resident : personsAtAddress) {
            Optional<Medicalrecord> medicalRecord = datas.getMedicalRecords()
                    .stream()
                    .filter(record -> resident.getLastName().equals(record.getLastName()) && resident.getFirstName()
                            .equals(record.getFirstName()))
                    .findFirst();
            if (medicalRecord.isPresent()) {
                extendsInfoResidents.add(personsInfoExtendsMapper.mapPersonAndMedicalRecordToPersonsInfoExtends(resident, medicalRecord.get(), AgeHelper.INSTANCE.getAge(medicalRecord.get().getBirthdate())));
            }
        }
        return extendsInfoResidents;
    }

    public Map<String, Map<String, List<PersonInfoExtendsDto>>> getAddressesAndTheirResidentsCoveredByStations(List<String> stations) {
        Map<String, Map<String, List<PersonInfoExtendsDto>>> addressesAndTheirResidentsCoveredByStation = new HashMap<>();
        for (String station : stations) {
            Map<String, List<PersonInfoExtendsDto>> personsByAddress= new HashMap<>();
            List<String> addressesCoveredByStation = datas.getFireStations()
                    .stream()
                    .filter(stationItem -> stationItem.getStation().equals(station))
                    .map(FireStation::getAddress)
                    .collect(Collectors.toList());

            for(String address: addressesCoveredByStation) {
                personsByAddress.put(address, getPersonsInfoExtendsByAddress(address));
            }
           addressesAndTheirResidentsCoveredByStation.put(station, personsByAddress);
        }
        return addressesAndTheirResidentsCoveredByStation;
    }
}
