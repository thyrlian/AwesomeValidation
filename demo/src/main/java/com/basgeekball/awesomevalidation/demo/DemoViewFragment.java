package com.basgeekball.awesomevalidation.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

/**
 * Created by elsennovraditya on 12/12/15.
 */
public class DemoViewFragment extends Fragment {

    private AwesomeValidation mAwesomeValidation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_demo_view, container, false);
        initAwesomeValidation();
        initValidationForEmail(rootView);
        return rootView;
    }

    private void initAwesomeValidation() {
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        mAwesomeValidation.setContext(getActivity());
    }

    /**
     * Used to demo how to use awesome validation within the view (not depend on activity)
     */
    private void initValidationForEmail(View view) {
        // Here we pass view instead of edittext or activity
        mAwesomeValidation.addValidation(view, R.id.edt_name_fragment, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(view, R.id.edt_email_fragment, Patterns.EMAIL_ADDRESS, R.string.err_email);

        Button emailDoneButton = (Button) view.findViewById(R.id.btn_email_done);
        emailDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation.clear();
                mAwesomeValidation.validate();
            }
        });
    }

}
