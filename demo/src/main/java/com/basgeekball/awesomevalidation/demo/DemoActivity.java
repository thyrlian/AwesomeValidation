package com.basgeekball.awesomevalidation.demo;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;

import java.util.Calendar;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;
import static com.basgeekball.awesomevalidation.ValidationStyle.TEXT_INPUT_LAYOUT;
import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

public class DemoActivity extends AppCompatActivity {

    private String[] mStyles;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private DrawerItemClickListener mDrawerItemClickListener = new DrawerItemClickListener();
    private int mPosition = 0;
    private AwesomeValidation mAwesomeValidation;
    private LinearLayout mViewSuccess;
    private ScrollView mScrollView;
    private View mViewContainerEditText;
    private View mViewContainerTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mViewSuccess = (LinearLayout) findViewById(R.id.container_success);
        mScrollView = (ScrollView) findViewById(R.id.scroll_view);
        mViewContainerEditText = findViewById(R.id.container_edit_text);
        mViewContainerTextInputLayout = findViewById(R.id.container_text_input_layout);
        mStyles = getResources().getStringArray(R.array.styles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
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
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
            return;
        }
        if (mPosition > 0) {
            mDrawerItemClickListener.selectItem(0);
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
            String style = mStyles[mPosition];
            setTitle(style);
            mDrawerLayout.closeDrawer(mDrawerList);
            mViewContainerEditText.setVisibility(position < TEXT_INPUT_LAYOUT.value() ? View.VISIBLE : View.GONE);
            mViewContainerTextInputLayout.setVisibility(position < TEXT_INPUT_LAYOUT.value() ? View.GONE : View.VISIBLE);
            clearValidation();
            initValidation(style);
            if (position < TEXT_INPUT_LAYOUT.value()) {
                addValidationForEditText(DemoActivity.this);
            } else if (position == TEXT_INPUT_LAYOUT.value()) {
                addValidationForTextInputLayout(DemoActivity.this);
            }
        }
    }

    private void clearValidation() {
        if (mAwesomeValidation != null) {
            mAwesomeValidation.clear();
            mViewSuccess.setVisibility(View.GONE);
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
            case TEXT_INPUT_LAYOUT:
                mAwesomeValidation = new AwesomeValidation(TEXT_INPUT_LAYOUT);
                break;
        }
    }

    private void addValidationForEditText(Activity activity) {
        mAwesomeValidation.addValidation(activity, R.id.edt_userid, "[a-zA-Z0-9_-]+", R.string.err_userid);
        mAwesomeValidation.addValidation(activity, R.id.edt_password, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}", R.string.err_password);
        mAwesomeValidation.addValidation(activity, R.id.edt_password_confirmation, R.id.edt_password, R.string.err_password_confirmation);
        mAwesomeValidation.addValidation(activity, R.id.edt_firstname, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(activity, R.id.edt_lastname, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(activity, R.id.edt_email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        mAwesomeValidation.addValidation(activity, R.id.edt_ip, Patterns.IP_ADDRESS, R.string.err_ip);
        mAwesomeValidation.addValidation(activity, R.id.edt_tel, RegexTemplate.TELEPHONE, R.string.err_tel);
        mAwesomeValidation.addValidation(activity, R.id.edt_zipcode, "\\d+", R.string.err_zipcode);
        mAwesomeValidation.addValidation(activity, R.id.edt_year, Range.closed(1900, Calendar.getInstance().get(Calendar.YEAR)), R.string.err_year);
        mAwesomeValidation.addValidation(activity, R.id.edt_height, Range.closed(0.0f, 2.72f), R.string.err_height);
        setValidationButtons();
    }

    private void addValidationForTextInputLayout(Activity activity) {
        mAwesomeValidation.addValidation(activity, R.id.til_email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        mAwesomeValidation.addValidation(activity, R.id.til_password, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}", R.string.err_password);
        mAwesomeValidation.addValidation(activity, R.id.til_password_confirmation, R.id.til_password, R.string.err_password_confirmation);
        mAwesomeValidation.addValidation(activity, R.id.til_year, Range.closed(1900, Calendar.getInstance().get(Calendar.YEAR)), R.string.err_year);
        setValidationButtons();
    }

    private void setValidationButtons() {
        Button btnDone = (Button) findViewById(R.id.btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAwesomeValidation.validate()) {
                    mScrollView.fullScroll(View.FOCUS_DOWN);
                    mViewSuccess.setVisibility(View.VISIBLE);
                } else {
                    mViewSuccess.setVisibility(View.GONE);
                }
            }
        });

        Button btnClr = (Button) findViewById(R.id.btn_clr);
        btnClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation.clear();
                mViewSuccess.setVisibility(View.GONE);
            }
        });
    }

}
