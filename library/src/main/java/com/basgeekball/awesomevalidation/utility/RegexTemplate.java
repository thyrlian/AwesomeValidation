package com.basgeekball.awesomevalidation.utility;

public class RegexTemplate {

    public static final String NOT_EMPTY = "^(?=\\s*\\S).*$";
    public static final String TELEPHONE = "(^\\+)?[0-9()-]*";

    private RegexTemplate() {
        throw new AssertionError();
    }
}