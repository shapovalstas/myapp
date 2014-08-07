package com.example.myapp.app;

/**
 * Created by sshapoval on 8/5/2014.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapp.app.validation.Validation;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Register extends Activity implements OnClickListener {

    private EditText user, pass, email, carnumber, phone;
    private Button mRegister;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //testing on Emulator:
//    private static final String LOGIN_URL = "http://172.31.35.100:1234/webservices/register.php";
    private static final String LOGIN_URL = "http://192.168.0.100:1234/webservices/register.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        carnumber = (EditText) findViewById(R.id.carnumber);
        phone = (EditText) findViewById(R.id.phone);

        mRegister = (Button) findViewById(R.id.register);
        mRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Validation validation = new Validation();
        CreateUser createUser = new CreateUser();
        boolean noError = true;
        // TODO Auto-generated method stub
        if (!validation.isValidUsername(user.getText().toString())) {
            noError = false;
            showError(user, "Please, enter the correct name");
        }
        if (!validation.isValidPassword(pass.getText().toString())) {
            noError = false;
           showError(pass, "Please, enter the correct password (Minimum 6 characters at least 1 Alphabet and 1 Number)");

        }
        if (!validation.isValidEmail(email.getText().toString())) {
            noError = false;
            showError(email, "Please, enter the correct email");

        }
        if (!validation.isValidPhone(phone.getText().toString())) {
            noError = false;
            showError(phone, "Please, enter the correct phone number");
        }
        if (carnumber.getText().length() == 0) {
            noError = false;
            showError(carnumber, "Please, enter the car number");
        }
        if (noError) {
            createUser.execute();
        }

    }

    class CreateUser extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            String emailadr = email.getText().toString();
            String carnumb = carnumber.getText().toString();
            String mphone = phone.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("email", emailadr));
                params.add(new BasicNameValuePair("car_number", carnumb));
                params.add(new BasicNameValuePair("phone", mphone));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // full json response
                Log.d("Login attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }
    public void showError(EditText editText, String message) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(shake);
        editText.setError(message);
    }



}
