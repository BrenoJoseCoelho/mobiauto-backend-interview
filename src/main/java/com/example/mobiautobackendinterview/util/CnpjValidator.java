package com.example.mobiautobackendinterview.util;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

public class CnpjValidator implements ConstraintValidator<CnpjValido, String> {

    @Override
    public void initialize(CnpjValido constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null || !cnpj.matches("\\d{14}")) {
            return false;
        }

        // Remove caracteres especiais do CNPJ
        cnpj = cnpj.replaceAll("[\\-\\/]", "");

        // Verifying first digit
        int sum = 0;
        int[] weights = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights[i];
        }
        int remainder = sum % 11;
        int digit1 = remainder < 2 ? 0 : 11 - remainder;
        if (Character.getNumericValue(cnpj.charAt(12)) != digit1) {
            return false;
        }

        // Verifying second digit
        sum = 0;
        weights = new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights[i];
        }
        remainder = sum % 11;
        int digit2 = remainder < 2 ? 0 : 11 - remainder;
        return Character.getNumericValue(cnpj.charAt(13)) == digit2;
    }
}