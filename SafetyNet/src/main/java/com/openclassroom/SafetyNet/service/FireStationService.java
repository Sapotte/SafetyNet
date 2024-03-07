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
import com.openclassroom.SafetyNet.utils.mapper.PersonsCoveredByFirestationMapper;
import com.openclassroom.SafetyNet.utils.mapper.PersonsCoveredByFirestationMapperImpl;
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
    MedicalrecordService medicalrecordService;
    @Autowired
    PersonService personService;
    @Autowired
    Datas datas;
    PersonsInfoExtendsMapper personsInfoExtendsMapper = new PersonsInfoExtendsMapperImpl();
    PersonsCoveredByFirestationMapper personsCoveredByFirestationMapper = new PersonsCoveredByFirestationMapperImpl();

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
        logger.debug(MessageFormat.format("{0} addresses found for station {1}", addressesCoveredByStation.size(), stationNumber));
        if (!addressesCoveredByStation.isEmpty()) {
            List<Person> personCoveredByFirestation = personService.getPersonsByAddresses(addressesCoveredByStation);
            logger.debug(MessageFormat.format("{0} persons covered by station {1}", personCoveredByFirestation.size(), stationNumber));
            List<String> birthDates = new ArrayList<>();
            for (Person person : personCoveredByFirestation) {
                birthDates.addAll(medicalrecordService.getMedicalrecordsByName(person.getFirstName(), person.getLastName()).stream().map(Medicalrecord::getBirthdate).toList());
            }
            Map<String, Integer> adultChildCount = AgeHelper.INSTANCE.adultChildCount(birthDates);
            return personsCoveredByFirestationMapper.mapPersonAndAdultChildCountToPersonCoveredByFirestation(personCoveredByFirestation, adultChildCount);
        } else {
            throw new NoSuchElementException(MessageFormat.format("No address covered by stationNumber {0}", stationNumber));
        }
    }

    public List<String> getPhoneNumbersByFirestation(String firestation) {
        List<String> addressesCoveredByStation = getAddressesCoveredByFirestation(firestation);
        return personService.getPersonsByAddresses(addressesCoveredByStation).stream().map(Person::getPhone).toList();
    }

    /**
     * Get all the addresses covered by a firestation
     *
     * @param stationNumber the fireStation number
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
        List<Person> personsAtAddress = personService.getPersonsByAddresses(List.of(address));
        for (Person resident : personsAtAddress) {
            medicalrecordService.getMedicalrecordsByName(resident.getFirstName(), resident.getLastName()).forEach(medicalrecord ->
                extendsInfoResidents.add(personsInfoExtendsMapper.mapPersonAndMedicalRecordToPersonsInfoExtends(resident, medicalrecord, AgeHelper.INSTANCE.getAge(medicalrecord.getBirthdate())))
            );
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
                    .toList();

            for(String address: addressesCoveredByStation) {
                personsByAddress.put(address, getPersonsInfoExtendsByAddress(address));
            }
           addressesAndTheirResidentsCoveredByStation.put(station, personsByAddress);
        }
        return addressesAndTheirResidentsCoveredByStation;
    }
}
