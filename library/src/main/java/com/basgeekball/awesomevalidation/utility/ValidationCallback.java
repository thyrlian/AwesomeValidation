package com.basgeekball.awesomevalidation.utility;

import com.basgeekball.awesomevalidation.ValidationHolder;

import java.util.regex.Matcher;

public interface ValidationCallback {

    void execute(ValidationHolder validationHolder, Matcher matcher);

}