package com.strohwitwer.awesomevalidation.utils;

import com.strohwitwer.awesomevalidation.ValidationHolder;

import java.util.regex.Matcher;

public interface ValidationCallback {

    void execute(ValidationHolder validationHolder, Matcher matcher);

}