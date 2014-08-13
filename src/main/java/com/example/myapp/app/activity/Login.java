package com.example.myapp.app.activity;

/**
 * Created by sshapoval on 8/5/2014.
 */

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.example.myapp.app.*;
import com.example.myapp.app.utils.DatabaseHandler;
import com.example.myapp.app.utils.UserFunctions;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class Login extends Activity implements OnClickListener {

    private EditText pass;
    private AutoCompleteTextView email;
    private Button mSubmit;

    // Progress Dialog
    private ProgressDialog pDialog;

    //JSON element ids from repsonse of php script:
    private static final String KEY_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //setup input fields
        pass = (EditText) findViewById(R.id.password);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        email.setAdapter(getEmailAddressAdapter(this));
        mSubmit = (Button) findViewById(R.id.login_button);
        mSubmit.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        boolean noError = true;
        // TODO Auto-generated method stub

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
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.loginUser(username,password);
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

    private ArrayAdapter<String> getEmailAddressAdapter(Context context) {
        Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
        String[] addresses = new String[accounts.length];
        for (int i = 0; i < accounts.length; i++) {
            addresses[i] = accounts[i].name;
        }
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, addresses);
    }


}
