package com.strohwitwer.awesomevalidation.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.strohwitwer.awesomevalidation.AwesomeValidation;
import com.strohwitwer.awesomevalidation.ValidationStyle;

public class DemoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        Button btnDone = (Button) findViewById(R.id.btn_done);
        final AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        awesomeValidation.setColor(Color.YELLOW);
        awesomeValidation.addValidation(this, R.id.edt_userid, "[a-zA-Z0-9_-]+", R.string.err_userid);
        awesomeValidation.addValidation(this, R.id.edt_name, "[a-zA-Z\\s]+", R.string.err_name);
        awesomeValidation.addValidation(this, R.id.edt_tel, "\\d+", R.string.err_tel);
        awesomeValidation.addValidation(this, R.id.edt_zipcode, "\\d+", R.string.err_zipcode);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awesomeValidation.validate();
            }
        });
    }

}