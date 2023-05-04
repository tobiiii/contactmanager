package com.ps.contactmanager.validation;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ContactmanagerValidation {
    private static final String EMAIL_PATTERN
        = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]"
        + "+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String ALPHA_NUM_PATTERN
            = "^[a-zA-Z0-9_]*$";
    private static final String NUMERIC_PATTERN
            = "^[0-9]+$";
    private static final String PASSWORD_PATTERN
            = "^[ !-~]{8,100}$";

    public static boolean validateEmail(final String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }

    public static boolean validateRequired(final String champ) {
        return (champ == null || champ.length() == 0 || champ.isEmpty());
    }


    public static boolean validateSize(final int size, final int min, final int max) {
        return min <= size && size <= max;
    }

    public static boolean validateLength(final int size, final int length) {
        return length == size;
    }

    public static boolean validateNbr(final String nbr) {
        Pattern pattern = Pattern.compile(NUMERIC_PATTERN);
        return pattern.matcher(nbr).matches();
    }

    public static boolean validatePassword(final String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }

    public static boolean validateAlphaNum(final String champ) {
        Pattern pattern = Pattern.compile(ALPHA_NUM_PATTERN);
        return pattern.matcher(champ).matches();
    }

    public static boolean isNumeric(String str) {
        int digits = 0;
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            } else {
                digits++;
            }
        }

        return digits <= 9;
    }

    public static boolean checkMandatory(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean checkListMandatory(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean checkMAPMandatory(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean validateNumberLength(final String number, int length) {
        Pattern pattern = Pattern.compile("^[0-9]{" + length + "}$");
        return !checkMandatory(number) && pattern.matcher(number).matches();
    }

    public static boolean validateAlphaNumericLength(final String number, int length) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]{" + length + "}$");
        return !checkMandatory(number) && pattern.matcher(number).matches();
    }

}
