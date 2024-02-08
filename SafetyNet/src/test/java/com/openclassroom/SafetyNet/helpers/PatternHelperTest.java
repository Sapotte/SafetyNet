package com.openclassroom.SafetyNet.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.SafetyNet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.InvalidAttributeValueException;
import java.io.File;
import java.io.IOException;

import static com.openclassroom.SafetyNet.utils.Constants.MAIL_ADDRESS;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PatternHelperTest {

    @InjectMocks
    PatternHelper patternHelper;

    private final ObjectMapper mapper = new ObjectMapper();
    Person person;

    @BeforeEach
    void setUp() throws IOException {
        person = mapper.readValue(new File("src/test/java/com/openclassroom/SafetyNet/utils/datas/NewPerson.json"), Person.class);
    }
    @Test
    void checkIsValidEmailOk() {
        assertDoesNotThrow(() -> patternHelper.checkIsValidEmail(MAIL_ADDRESS));
    }
    @Test
    void checkIsValidEmailKo() {
        assertThrows(InvalidAttributeValueException.class, () -> patternHelper.checkIsValidEmail("notAnEMail"));
    }
    @Test
    void checkIsValidNameOk() {
        assertDoesNotThrow(() -> patternHelper.checkIsValidName("Name"));
    }
    @Test
    void checkIsValidNameKo() {
        assertThrows(InvalidAttributeValueException.class, () -> patternHelper.checkIsValidName("not4aName"));
    }
    @Test
    void checkIsValidZipCodeOk() {
        assertDoesNotThrow(() -> patternHelper.checkIsValidZipCode("12345"));
    }
    @Test
    void checkIsValidZipCodeKo() {
        assertThrows(InvalidAttributeValueException.class, () -> patternHelper.checkIsValidZipCode("onlyDigits"));
    }
    @Test
    void checkIsValidPhoneNumberOk() {
        assertDoesNotThrow(() -> patternHelper.checkIsValidPhoneNumber("123-456-7891"));
    }
    @Test
    void checkIsValidPhoneNumberKo() {
        assertThrows(InvalidAttributeValueException.class, () -> patternHelper.checkIsValidPhoneNumber("45588898851-5555"));
    }
    @Test
    void checkIsValidNumberOk() {
        assertDoesNotThrow(() -> patternHelper.checkIsValidNumber("12"));
    }
    @Test
    void checkIsValidNumberKo() {
        assertThrows(InvalidAttributeValueException.class, () -> patternHelper.checkIsValidNumber("justNumbers21"));
    }
    @Test
    void checkIsValidPersonOk() {
        assertDoesNotThrow(() -> patternHelper.checkisValidPerson(person));
    }
    @Test
    void checkIsValidPersonKo() {
        Person invalidPerson = new Person();
        invalidPerson.setEmail("invalid");
        invalidPerson.setPhone("invalid");
        assertThrows(InvalidAttributeValueException.class, () -> patternHelper.checkisValidPerson(invalidPerson));
    }

}
