[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![JCenter](https://api.bintray.com/packages/thyrlian/android-libraries/com.basgeekball.awesomevalidation/images/download.svg) ](https://bintray.com/thyrlian/android-libraries/com.basgeekball.awesomevalidation/_latestVersion)
[![Travis CI Status](https://travis-ci.org/thyrlian/AwesomeValidation.svg?branch=master)](https://travis-ci.org/thyrlian/AwesomeValidation)
[![CircleCI Status](https://circleci.com/gh/thyrlian/AwesomeValidation/tree/master.svg?style=shield&circle-token=78719835860892697c8c54e1f22c872e086a0b09)](https://circleci.com/gh/thyrlian/AwesomeValidation/tree/master)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/dfea4a7a16c34cab9b810829fc221e19)](https://www.codacy.com/app/thyrlian/AwesomeValidation)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AwesomeValidation-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1605)
[![Android Weekly](https://img.shields.io/badge/Android%20Weekly-%23142-brightgreen.svg)](http://androidweekly.net/issues/issue-142)

# AwesomeValidation

## Introduction
Implement validation for Android within only 3 steps.  Developers should focus on their awesome code, and let the library do the boilerplate.  And what's more, this could help keep your layout file clean.

## Steps
1. Declare validation style;
2. Add validations;
3. Set a point when to trigger validation.

## Sample code
```java
// Step 1: designate a style
AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
// or
AwesomeValidation mAwesomeValidation = new AwesomeValidation(COLORATION);
mAwesomeValidation.setColor(Color.YELLOW);  // optional, default color is RED if not set
// or
AwesomeValidation mAwesomeValidation = new AwesomeValidation(UNDERLABEL);
mAwesomeValidation.setContext(this);  // mandatory for UNDERLABEL style
// setUnderlabelColor is optional for UNDERLABEL style, by default it's holo_red_light
mAwesomeValidation.setUnderlabelColorByResource(android.R.color.holo_orange_light); // optional for UNDERLABEL style
mAwesomeValidation.setUnderlabelColor(ContextCompat.getColor(this, android.R.color.holo_orange_dark)); // optional for UNDERLABEL style
// or
AwesomeValidation mAwesomeValidation = new AwesomeValidation(TEXT_INPUT_LAYOUT);

// Step 2: add validations
// support regex string, java.util.regex.Pattern and Guava#Range
// you can pass resource or string
mAwesomeValidation.addValidation(activity, R.id.edt_name, "[a-zA-Z\\s]+", R.string.err_name);
mAwesomeValidation.addValidation(activity, R.id.edt_tel, RegexTemplate.TELEPHONE, R.string.err_tel);
mAwesomeValidation.addValidation(activity, R.id.edt_email, android.util.Patterns.EMAIL_ADDRESS, R.string.err_email);
mAwesomeValidation.addValidation(activity, R.id.edt_year, Range.closed(1900, Calendar.getInstance().get(Calendar.YEAR)), R.string.err_year);
mAwesomeValidation.addValidation(activity, R.id.edt_height, Range.closed(0.0f, 2.72f), R.string.err_height);
// or
mAwesomeValidation.addValidation(editText, "regex", "Error info");

// to validate TextInputLayout, pass the TextInputLayout, not the embedded EditText
AwesomeValidation mAwesomeValidation = new AwesomeValidation(TEXT_INPUT_LAYOUT);
mAwesomeValidation.addValidation(activity, R.id.til_email, Patterns.EMAIL_ADDRESS, R.string.err_email);

// to validate the confirmation of another field
String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
mAwesomeValidation.addValidation(activity, R.id.edt_password, regexPassword, R.string.err_password);
// to validate a confirmation field (don't validate any rule other than confirmation on confirmation field)
mAwesomeValidation.addValidation(activity, R.id.edt_password_confirmation, R.id.edt_password, R.string.err_password_confirmation);

// to validate with your own custom validator function
mAwesomeValidation.addValidation(activity, R.id.edt_birth, new CustomValidation() {
    @Override
    public boolean compare(String input) {
        // check if the age is >= 18
        try {
            Calendar calendarBirthday = Calendar.getInstance();
            Calendar calendarToday = Calendar.getInstance();
            calendarBirthday.setTime(new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(input));
            int yearOfToday = calendarToday.get(Calendar.YEAR);
            int yearOfBirthday = calendarBirthday.get(Calendar.YEAR);
            if (yearOfToday - yearOfBirthday > 18) {
                return true;
            } else if (yearOfToday - yearOfBirthday == 18) {
                int monthOfToday = calendarToday.get(Calendar.MONTH);
                int monthOfBirthday = calendarBirthday.get(Calendar.MONTH);
                if (monthOfToday > monthOfBirthday) {
                    return true;
                } else if (monthOfToday == monthOfBirthday) {
                    if (calendarToday.get(Calendar.DAY_OF_MONTH) >= calendarBirthday.get(Calendar.DAY_OF_MONTH)) {
                        return true;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}, R.string.err_birth);

// Step 3: set a trigger
findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mAwesomeValidation.validate();
    }
});

// Optional: remove validation failure information
findViewById(R.id.btn_clr).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mAwesomeValidation.clear();
    }
});
```

## Attention

* It works perfectly with **Fragment**, but please pay attention to Fragment's lifecycle.  You should set the `validate()` inside Fragment's `onActivityCreated` instead of `onCreateView` or any other early stage.

* `UNDERLABEL` validation style doesn't support `ConstraintLayout` at the moment, please use other validation styles.  There is an open issue [here](https://github.com/thyrlian/AwesomeValidation/issues/33).

## Import as dependency
For Gradle it's so easy: just add below compile line to your module's build.gradle (it's available on JCenter).
```java
dependencies {
    compile 'com.basgeekball:awesome-validation:3.0'
}
```

## Screenshots
<a href="https://github.com/thyrlian/AwesomeValidation/blob/master/resource/images/screenshot_0.png" target="_blank"><img src="https://github.com/thyrlian/AwesomeValidation/blob/master/resource/images/screenshot_0.png" height="600"></a>

<a href="https://github.com/thyrlian/AwesomeValidation/blob/master/resource/images/screenshot_1.png" target="_blank"><img src="https://github.com/thyrlian/AwesomeValidation/blob/master/resource/images/screenshot_1.png" height="600"></a>
<a href="https://github.com/thyrlian/AwesomeValidation/blob/master/resource/images/screenshot_2.png" target="_blank"><img src="https://github.com/thyrlian/AwesomeValidation/blob/master/resource/images/screenshot_2.png" height="600"></a>
<a href="https://github.com/thyrlian/AwesomeValidation/blob/master/resource/images/screenshot_3.png" target="_blank"><img src="https://github.com/thyrlian/AwesomeValidation/blob/master/resource/images/screenshot_3.png" height="600"></a>
<a href="https://github.com/thyrlian/AwesomeValidation/blob/master/resource/images/screenshot_4.png" target="_blank"><img src="https://github.com/thyrlian/AwesomeValidation/blob/master/resource/images/screenshot_4.png" height="600"></a>

## Release guide
* Update version number in ***build.gradle***, ***gradle.properties*** and ***README***
* Create new git tag: `v*.*`
* Run `./gradlew clean build generateRelease` to generate release zip file
* Run `./gradlew bintrayUpload` to create a new version in bintray
* Upload release zip file manually to bintray, make sure to check '**Explode this archive**'

## License
Copyright (c) 2014-2016 Jing Li. See the [LICENSE](https://github.com/thyrlian/AwesomeValidation/blob/master/LICENSE) file for license rights and limitations (MIT).
