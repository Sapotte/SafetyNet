package com.openclassroom.SafetyNet.helpers;

import com.openclassroom.SafetyNet.model.Person;
import org.springframework.stereotype.Component;

import javax.management.InvalidAttributeValueException;

@Component
public class PatternHelper {
    public void checkIsValidEmail(String emailAddress) throws InvalidAttributeValueException {
        String mailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(!emailAddress.matches(mailRegex)) {
            throw new InvalidAttributeValueException("Invalid email address");
        }
    }

    public void checkIsValidName(String name) throws InvalidAttributeValueException {
        String nameRegex = "^[A-Za-zÀ-ÖØ-öø-ÿ-]+$";
        if(!name.matches(nameRegex)) {
            throw new InvalidAttributeValueException("Invalid name");
        }
    }

    public void checkIsValidZipCode(String zipCode) throws InvalidAttributeValueException {
        String zipCodeRegex = "^\\d{5}(-\\d{4})?$";
        if(!zipCode.matches(zipCodeRegex)) {
            throw new InvalidAttributeValueException("Invalid zip code");
        }
    }

    public void checkIsValidPhoneNumber(String phoneNumber) throws InvalidAttributeValueException {
        String phoneRegex = "^\\d{3}-\\d{3}-\\d{4}$";
        if(!phoneNumber.matches(phoneRegex)) {
            throw new InvalidAttributeValueException("Invalid phone number");
        }
    }

    public void checkIsValidNumber(String number) throws InvalidAttributeValueException {
        String numberRegex = "^[0-9]*$";
        if(!number.matches(numberRegex)) {
            throw new InvalidAttributeValueException("Invalid number");
        }
    }

    public void checkIsValidPerson(Person person) throws InvalidAttributeValueException {
        checkIsValidEmail(person.getEmail());
        checkIsValidName(person.getFirstName());
        checkIsValidName(person.getLastName());
        checkIsValidPhoneNumber(person.getPhone());
        checkIsValidZipCode(person.getZip());
    }
}
