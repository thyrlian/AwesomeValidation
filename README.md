[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AwesomeValidation-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1605)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![Build Status](https://travis-ci.org/thyrlian/AwesomeValidation.svg?branch=master)](https://travis-ci.org/thyrlian/AwesomeValidation)

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
// support regex string or Guava#Range
// you can pass resource or string
mAwesomeValidation.addValidation(activity, R.id.edt_name, "[a-zA-Z\\s]+", R.string.err_name);
mAwesomeValidation.addValidation(activity, R.id.edt_tel, RegexTemplate.TELEPHONE, R.string.err_tel);
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

##Screenshots
<a href="https://cloud.githubusercontent.com/assets/352956/6310455/4d3c90c6-b957-11e4-8075-8fb7b63ea669.png" target="_blank"><img src="https://cloud.githubusercontent.com/assets/352956/6310455/4d3c90c6-b957-11e4-8075-8fb7b63ea669.png" height="600"></a>
<a href="https://cloud.githubusercontent.com/assets/352956/6310452/4d38f40c-b957-11e4-820e-b3b5cfd54837.png" target="_blank"><img src="https://cloud.githubusercontent.com/assets/352956/6310452/4d38f40c-b957-11e4-820e-b3b5cfd54837.png" height="600"></a>
<a href="https://cloud.githubusercontent.com/assets/352956/6310454/4d3a83d0-b957-11e4-911a-a62e76b8024c.png" target="_blank"><img src="https://cloud.githubusercontent.com/assets/352956/6310454/4d3a83d0-b957-11e4-911a-a62e76b8024c.png" height="600"></a>
<a href="https://cloud.githubusercontent.com/assets/352956/6311996/999f2fcc-b96c-11e4-948a-1e142f9bc04a.png" target="_blank"><img src="https://cloud.githubusercontent.com/assets/352956/6311996/999f2fcc-b96c-11e4-948a-1e142f9bc04a.png" height="600"></a>

##License
Copyright (c) 2015 Jing Li. See the LICENSE file for license rights and limitations (MIT).