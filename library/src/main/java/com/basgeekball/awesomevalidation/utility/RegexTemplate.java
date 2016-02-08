package com.basgeekball.awesomevalidation.utility;

public class RegexTemplate {

    public static final String NOT_EMPTY = "(?m)^\\s*\\S+[\\s\\S]*$";
    public static final String TELEPHONE = "(^\\+\\d+)?[0-9\\s()-]*";

    private RegexTemplate() {
        throw new UnsupportedOperationException();
    }
}