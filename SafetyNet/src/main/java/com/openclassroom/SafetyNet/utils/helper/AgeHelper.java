package com.openclassroom.SafetyNet.utils.helper;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class AgeHelper {
    public static final AgeHelper INSTANCE = new AgeHelper();
    public Integer getAge(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return Period.between(LocalDate.parse(birthDate, formatter), LocalDate.now()).getYears();
    }

    public boolean isAdult(String birthDate) {
        Integer age = getAge(birthDate);

        if(age >= 18) {
            return true;
        } else {
            return false;
        }
    }

    public Map<String, Integer> adultChildCount(List<String> birthDates) {
        Integer adultsCount = 0;
        Integer childsCount = 0;

        for(String birthdate : birthDates) {
            if(isAdult(birthdate)) {
                adultsCount++;
            } else {
                childsCount++;
            }
        }
        return Map.of("adultsCount", adultsCount, "childsCount", childsCount);
    }
}
