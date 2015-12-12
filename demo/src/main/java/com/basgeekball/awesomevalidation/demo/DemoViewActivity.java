package com.basgeekball.awesomevalidation.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by elsennovraditya on 12/12/15.
 */
public class DemoViewActivity extends AppCompatActivity {

    private static final String TAG = DemoViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_view);
        
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new DemoViewFragment(), TAG)
                .commit();
    }
}
