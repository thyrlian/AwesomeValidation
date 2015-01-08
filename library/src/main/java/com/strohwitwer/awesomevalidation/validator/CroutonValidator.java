package com.strohwitwer.awesomevalidation.validator;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.strohwitwer.awesomevalidation.ValidationHolder;
import com.strohwitwer.awesomevalidation.utils.ValidationCallback;

import java.util.regex.Matcher;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CroutonValidator extends Validator {

    private Context mContext;
    private Activity mActivity;

    public void setContext(Context context) {
        mContext = context;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    public boolean trigger() {
        return checkFields(new ValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder, Matcher matcher) {
                EditText editText = validationHolder.getEditText();
                ViewGroup parent = (ViewGroup) editText.getParent();
                int index = parent.indexOfChild(editText);
                LinearLayout dummyLayout = new LinearLayout(mContext);
                dummyLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                parent.addView(dummyLayout, index + 1);
                Crouton.makeText(mActivity, validationHolder.getErrMsg(), Style.ALERT, dummyLayout).show();
            }
        });
    }

//    Must be called in Activity.onDestroy()
    public void destroy() {
        Crouton.cancelAllCroutons();
    }

}