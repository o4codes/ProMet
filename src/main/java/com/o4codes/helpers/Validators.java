/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.o4codes.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author shadrach PC
 */
public class Validators {
    
    private Pattern regexPattern;
    private Matcher regMatcher;

    public  boolean validateEmailAddress(String emailAddress) {

        regexPattern = Pattern.compile("^[(a-zA-Z-0-9-_+.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        regMatcher   = regexPattern.matcher(emailAddress);
        return regMatcher.matches();
        
    }

    public boolean validateMobileNumber(String mobileNumber) {
        regexPattern = Pattern.compile("^[0-9]{11}$");
        regMatcher   = regexPattern.matcher(mobileNumber);
       return regMatcher.matches();
        
    }
}
