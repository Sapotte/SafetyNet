package com.openclassroom.SafetyNet.helpers;

import com.openclassroom.SafetyNet.model.Person;
import org.springframework.stereotype.Component;

import javax.management.InvalidAttributeValueException;

@Component
public class PatternHelper {

    private final String mailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String nameRegex = "^[A-Za-zÀ-ÖØ-öø-ÿ-]+$";
    private final String zipCodeRegex = "^\\d{5}(-\\d{4})?$";
    private final String phoneRegex = "^\\d{3}-\\d{3}-\\d{4}$";
    private final String numberRegex = "^[0-9]*$";


    public void checkIsValidEmail(String emailAddress) throws InvalidAttributeValueException {
        if(!emailAddress.matches(mailRegex)) {
            throw new InvalidAttributeValueException("Invalid email address");
        }
    }

    public void checkIsValidName(String name) throws InvalidAttributeValueException {
        if(!name.matches(nameRegex)) {
            throw new InvalidAttributeValueException("Invalid name");
        }
    }

    public void checkIsValidZipCode(String zipCode) throws InvalidAttributeValueException {
        if(!zipCode.matches(zipCodeRegex)) {
            throw new InvalidAttributeValueException("Invalid zip code");
        }
    }

    public void checkIsValidPhoneNumber(String phoneNumber) throws InvalidAttributeValueException {
        if(!phoneNumber.matches(phoneRegex)) {
            throw new InvalidAttributeValueException("Invalid phone number");
        }
    }

    public void checkIsValidNumber(String number) throws InvalidAttributeValueException {
        if(!number.matches(numberRegex)) {
            throw new InvalidAttributeValueException("Invalid number");
        }
    }

    public void checkisValidPerson(Person person) throws InvalidAttributeValueException {
        checkIsValidEmail(person.getEmail());
        checkIsValidName(person.getFirstName());
        checkIsValidName(person.getLastName());
        checkIsValidPhoneNumber(person.getPhone());
        checkIsValidZipCode(person.getZip());
    }
}
