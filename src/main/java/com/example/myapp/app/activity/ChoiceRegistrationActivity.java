package com.example.myapp.app.activity;

/**
 * Created by sshapoval on 8/5/2014.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.myapp.app.R;
import com.facebook.widget.LoginButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ChoiceRegistrationActivity extends Activity implements OnClickListener {

    private EditText firstName, lastName;
    private RadioButton male, female;
    private Button btnGooglePlus,btnTwitter,btnEmail;
    private LoginButton  btnFacebook;
    // Progress Dialog
    private ProgressDialog pDialog;
    //ids


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_social);
        btnEmail = (Button) findViewById(R.id.btnEmail);
        btnFacebook = (LoginButton) findViewById(R.id.btnFb);
        btnFacebook.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
//        firstName = (EditText) findViewById(R.id.first_name);
//        lastName = (EditText) findViewById(R.id.last_name);
//        male = (RadioButton) findViewById(R.id.male);
//        female = (RadioButton) findViewById(R.id.famale);
//        next = (Button) findViewById(R.id.nextUser);
//        next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEmail:
                Intent registerWithEmail = new Intent(this, RegisterWithEmail.class);
                registerWithEmail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(registerWithEmail);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                finish();
                break;
            case R.id.btnFb:
                btnFacebook.setReadPermissions(Arrays.asList("basic_info","email"));
                Intent facebook = new Intent(this, RegisterWithFacebook.class);
//                facebook.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(facebook);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                finish();
                break;
            default:
                break;
        }
    }

//    class CreateUser extends AsyncTask<String, String, String> {
//        /**
//         * Before starting background thread Show Progress Dialog
//         */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(Register.this);
//            pDialog.setMessage("Creating User...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... args) {
//            // TODO Auto-generated method stub
//            // Check for success tag
//            int success;
////            String username = user.getText().toString();
////            String password = pass.getText().toString();
////            String emailadr = email.getText().toString();
////            String carnumb = carnumber.getText().toString();
////            String mphone = phone.getText().toString();
//            try {
//                // Building Parameters
////                List<NameValuePair> params = new ArrayList<NameValuePair>();
////                params.add(new BasicNameValuePair("username", username));
////                params.add(new BasicNameValuePair("password", password));
////                params.add(new BasicNameValuePair("email", emailadr));
////                params.add(new BasicNameValuePair("car_number", carnumb));
////                params.add(new BasicNameValuePair("phone", mphone));
//
//                Log.d("request!", "starting");
//
//                //Posting user data to script
////                JSONObject json = jsonParser.makeHttpRequest(
////                        LOGIN_URL, "POST", params);
//
//                // full json response
//                Log.d("Login attempt", json.toString());
//
//                // json success element
//                success = json.getInt(TAG_SUCCESS);
//                if (success == 1) {
//                    Log.d("User Created!", json.toString());
//                    finish();
//                    return json.getString(TAG_MESSAGE);
//                } else {
//                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
//                    return json.getString(TAG_MESSAGE);
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//
//        }



//    }
    public void showError(EditText editText, String message) {
        android.view.animation.Animation shake = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(shake);
        editText.setError(message);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        android.content.Intent intent = new android.content.Intent(this, Main.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        finish();
    }



}
