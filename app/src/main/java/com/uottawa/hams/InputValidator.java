package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

public class InputValidator {
    //this class validates registration inputs
    public static boolean isInputValid(String... inputs) {
        for (String input : inputs) {
            if (TextUtils.isEmpty(input)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPatientInputValid(String p_firstname, String p_lastname,
                                              String p_emailaddress, String p_password,
                                              String p_phonenumber, String p_address,
                                              String p_healthcardnum) {
        return isInputValid(p_firstname, p_lastname, p_emailaddress,
                p_password, p_phonenumber, p_address, p_healthcardnum)
                && isValidEmail(p_emailaddress)
                && isInteger(p_phonenumber)
                && isInteger(p_healthcardnum);
    }

    public static boolean isDoctorInputValid(String d_firstname, String d_lastname,
                                             String d_emailaddress, String d_password,
                                             String d_phonenumber, String d_address,
                                             String d_employeenum, String d_specialties) {
        return isInputValid(d_firstname, d_lastname, d_password, d_phonenumber, d_address, d_employeenum, d_specialties)
                && isValidEmail(d_emailaddress)
                && isInteger(d_phonenumber)
                && isInteger(d_employeenum);
    }





}