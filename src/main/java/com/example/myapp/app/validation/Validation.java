package com.example.myapp.app.validation;

import android.app.Activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sshapoval on 8/6/2014.
 */
public class Validation extends Activity {

    //validating email
    public boolean isValidEmail (String email){
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
