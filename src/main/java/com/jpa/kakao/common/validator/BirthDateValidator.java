package com.jpa.kakao.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;

public class BirthDateValidator implements ConstraintValidator<BirthDate,LocalDate> {

    @Override
    public void initialize(BirthDate constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        return birthDate !=null && birthDate.isAfter(of(1900,1,1))
                && birthDate.isBefore(now());
    }

}
