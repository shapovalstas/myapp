package com.example.myapp.app.login;

/**
 * Created by sshapoval on 8/5/2014.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.myapp.app.JSONParser;
import com.example.myapp.app.R;
import com.example.myapp.app.Register;
import com.example.myapp.app.Search;
import com.example.myapp.app.validation.Validation;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends Activity implements OnClickListener {

    private EditText email, pass;
    private Button mSubmit;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();


//    private static final String LOGIN_URL = "http://172.31.35.100:1234/webservices/login.php";
    private static final String LOGIN_URL = "http://192.168.0.100:1234/webservices/login.php";

    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //setup input fields
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);

        mSubmit = (Button) findViewById(R.id.login_button);
        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean noError = true;
        // TODO Auto-generated method stub

//        switch (v.getId()) {
//            case R.id.login_button:
                if (email.getText().length() == 0) {
                    noError = false;
                    showError(email, "Please, enter the name");
                }
                if (pass.getText().length() == 0) {
                    noError = false;
                    showError(pass, "Please, enter the password");
                }
                if (noError) {
                    new AttemptLogin().execute();
                }
//                break;
//            case R.id.register:
//                Intent i = new Intent(this, Register.class);
//                startActivity(i);
//                break;
//
//            default:
//                break;
//        }

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, Main.class);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);

    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String username = email.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    Intent i = new Intent(Login.this, Search.class);
                    finish();
                    startActivity(i);
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
                Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

    public void showError(EditText editText, String message) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(shake);
        editText.setError(message);
    }


}
