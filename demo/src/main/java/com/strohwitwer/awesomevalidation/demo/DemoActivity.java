package com.strohwitwer.awesomevalidation.demo;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.strohwitwer.awesomevalidation.AwesomeValidation;
import com.strohwitwer.awesomevalidation.ValidationStyle;
import com.strohwitwer.awesomevalidation.utility.RegexTemplate;

import static com.strohwitwer.awesomevalidation.ValidationStyle.BASIC;
import static com.strohwitwer.awesomevalidation.ValidationStyle.COLORATION;
import static com.strohwitwer.awesomevalidation.ValidationStyle.UNDERLABEL;

public class DemoActivity extends ActionBarActivity {

    private String[] mStyles;
    private String mStyle;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private DrawerItemClickListener mDrawerItemClickListener = new DrawerItemClickListener();
    private int mPosition = 0;
    private AwesomeValidation mAwesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mStyles = getResources().getStringArray(R.array.styles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mStyles));
        mDrawerList.setOnItemClickListener(mDrawerItemClickListener);
        mDrawerItemClickListener.selectItem(mPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
            mDrawerLayout.closeDrawers();
            return;
        }
        if (mPosition > 0) {
            mDrawerItemClickListener.selectItem(0);
            return;
        } else {
            super.onBackPressed();
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

        private void selectItem(int position) {
            mDrawerList.setItemChecked(position, true);
            mPosition = position;
            mStyle = mStyles[mPosition];
            setTitle(mStyle);
            mDrawerLayout.closeDrawer(mDrawerList);

            clearValidation();
            initValidation(mStyle);
            addValidation(DemoActivity.this);
        }
    }

    private void clearValidation() {
        if (mAwesomeValidation != null) {
            mAwesomeValidation.clear();
        }
    }

    private void initValidation(String style) {
        switch (ValidationStyle.valueOf(style)) {
            case BASIC:
                mAwesomeValidation = new AwesomeValidation(BASIC);
                break;
            case COLORATION:
                mAwesomeValidation = new AwesomeValidation(COLORATION);
                mAwesomeValidation.setColor(Color.YELLOW);
                break;
            case UNDERLABEL:
                mAwesomeValidation = new AwesomeValidation(UNDERLABEL);
                mAwesomeValidation.setContext(this);
                break;
        }
    }

    private void addValidation(final Activity activity) {
        mAwesomeValidation.addValidation(activity, R.id.edt_userid, "[a-zA-Z0-9_-]+", R.string.err_userid);
        mAwesomeValidation.addValidation(activity, R.id.edt_name, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(activity, R.id.edt_tel, RegexTemplate.TELEPHONE, R.string.err_tel);
        mAwesomeValidation.addValidation(activity, R.id.edt_zipcode, "\\d+", R.string.err_zipcode);

        Button btnDone = (Button) findViewById(R.id.btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation.validate();
            }
        });

        Button btnClr = (Button) findViewById(R.id.btn_clr);
        btnClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation.clear();
            }
        });
    }

}