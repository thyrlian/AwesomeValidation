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

##Screenshots
<a href="url"><img src="https://cloud.githubusercontent.com/assets/352956/5984428/7ee94cc2-a8d6-11e4-8533-a6dbbd6b7499.png" height="400"></a>
<a href="url"><img src="https://cloud.githubusercontent.com/assets/352956/5984429/82d3d4ba-a8d6-11e4-8634-f955ed2e55ac.png" height="400"></a>
<a href="url"><img src="https://cloud.githubusercontent.com/assets/352956/5984430/8611aa9e-a8d6-11e4-86cf-59ffe7dc18a3.png" height="400"></a>
