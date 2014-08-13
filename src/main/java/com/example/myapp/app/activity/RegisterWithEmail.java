package com.example.myapp.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapp.app.R;
import com.example.myapp.app.utils.DatabaseHandler;
import com.example.myapp.app.utils.UserFunctions;
import org.json.JSONObject;

/**
 * Created by sshapoval on 8/10/2014.
 */
public class RegisterWithEmail extends Activity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword, editTextFirstName, editTextLastName;
    private Button btnSignUp;

    private ProgressDialog pDialog;
    private static final String KEY_SUCCESS = "success";
    // Login Table Columns names
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextFirstName = (EditText) findViewById(R.id.editFirstName);
        editTextLastName = (EditText) findViewById(R.id.editLasNameName);

    }


    @Override
    public void onClick(View v) {
        new AttemptRegister().execute();
    }

    class AttemptRegister extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterWithEmail.this);
            pDialog.setMessage("Attempting register...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String firstName = editTextFirstName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.registerUser(firstName, lastName, email, password);
            try {
                if(json.getString(KEY_SUCCESS) !=null){
                    String res = json.getString(KEY_SUCCESS);
                    if(Integer.parseInt(res) == 1){
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");

                        userFunction.logoutUser(getApplicationContext());
                        db.addUser(json_user.getString(KEY_FIRST_NAME), json_user.getString(KEY_LAST_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));

                        Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);

                        // Close all views before launching Dashboard
                        dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(dashboard);

                        // Close Login Screen
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }
}

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, ChoiceRegistrationActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        finish();
    }


}
