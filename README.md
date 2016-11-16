[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![JCenter](https://api.bintray.com/packages/thyrlian/android-libraries/com.basgeekball.awesomevalidation/images/download.svg) ](https://bintray.com/thyrlian/android-libraries/com.basgeekball.awesomevalidation/_latestVersion)
[![Build Status](https://travis-ci.org/thyrlian/AwesomeValidation.svg?branch=master)](https://travis-ci.org/thyrlian/AwesomeValidation)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/dfea4a7a16c34cab9b810829fc221e19)](https://www.codacy.com/app/thyrlian/AwesomeValidation)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AwesomeValidation-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1605)
[![Android Weekly](https://img.shields.io/badge/Android%20Weekly-%23142-brightgreen.svg)](http://androidweekly.net/issues/issue-142)
[![Stories in Ready](https://badge.waffle.io/thyrlian/AwesomeValidation.png?label=ready&title=Ready)](https://waffle.io/thyrlian/AwesomeValidation)

#AwesomeValidation

##Introduction
Implement validation for Android within only 3 steps.  Developers should focus on their awesome code, and let the library do the boilerplate.  And what's more, this could help keep your layout file clean.

##Steps
1. Declare validation style;
2. Add validations;
3. Set a point when to trigger validation.

##Sample code
```java
// Step 1: designate a style
AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
// or
AwesomeValidation mAwesomeValidation = new AwesomeValidation(COLORATION);
mAwesomeValidation.setColor(Color.YELLOW);  // optional, default color is RED if not set
// or
AwesomeValidation mAwesomeValidation = new AwesomeValidation(UNDERLABEL);
mAwesomeValidation.setContext(this);  // mandatory for UNDERLABEL style

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

##Import as dependency
For Gradle it's so easy: just add below compile line to your module's build.gradle (it's available on JCenter).
```java
dependencies {
    compile 'com.basgeekball:awesome-validation:1.3'
}
```

##Screenshots
<a href="https://cloud.githubusercontent.com/assets/352956/6310455/4d3c90c6-b957-11e4-8075-8fb7b63ea669.png" target="_blank"><img src="https://cloud.githubusercontent.com/assets/352956/6310455/4d3c90c6-b957-11e4-8075-8fb7b63ea669.png" height="600"></a>
<a href="https://cloud.githubusercontent.com/assets/352956/6310452/4d38f40c-b957-11e4-820e-b3b5cfd54837.png" target="_blank"><img src="https://cloud.githubusercontent.com/assets/352956/6310452/4d38f40c-b957-11e4-820e-b3b5cfd54837.png" height="600"></a>
<a href="https://cloud.githubusercontent.com/assets/352956/6310454/4d3a83d0-b957-11e4-911a-a62e76b8024c.png" target="_blank"><img src="https://cloud.githubusercontent.com/assets/352956/6310454/4d3a83d0-b957-11e4-911a-a62e76b8024c.png" height="600"></a>
<a href="https://cloud.githubusercontent.com/assets/352956/6311996/999f2fcc-b96c-11e4-948a-1e142f9bc04a.png" target="_blank"><img src="https://cloud.githubusercontent.com/assets/352956/6311996/999f2fcc-b96c-11e4-948a-1e142f9bc04a.png" height="600"></a>

##Release guide
* Update version number in *build.gradle*, *gradle.properties* and *README*
* Run `gradle clean build generateRelease` to generate release zip file
* Run `gradle bintrayUpload` to create a new version in bintray
* Upload release zip file manually to bintray, make sure to check '**Explode this archive**'

##License
Copyright (c) 2014-2016 Jing Li. See the [LICENSE](https://github.com/thyrlian/AwesomeValidation/blob/master/LICENSE) file for license rights and limitations (MIT).
