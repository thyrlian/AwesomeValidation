#AwesomeValidation

##Introduction
Implement validation for Android within only 3 steps.  Developers should focus on their awesome code, and let the library do the boilerplate.

##Steps
1. Declare validation style;
2. Add validations;
3. Set a point when to trigger validation.

##Sample code
```
//Step 1
AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);

//Step 2
mAwesomeValidation.addValidation(activity, R.id.edt_name, "[a-zA-Z\\s]+", R.string.err_name);
mAwesomeValidation.addValidation(activity, R.id.edt_tel, RegexTemplate.TELEPHONE, R.string.err_tel);

//Step 3
findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mAwesomeValidation.validate();
    }
});
```
