package com.example.myapp.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapp.app.login.Login;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by sshapoval on 8/9/2014.
 */
public class DashboardActivity extends Activity {

    UserFunctions userFunctions;
    Button btnLogout;
    TextView firstandlast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Dashboard Screen for the application
         * */
        // Check login status in database
        userFunctions = new UserFunctions();
        if (userFunctions.isUserLoggedIn(getApplicationContext())) {
            setContentView(R.layout.dashboard);
            firstandlast = (TextView) findViewById(R.id.firstandlast);
            btnLogout = (Button) findViewById(R.id.btnLogout);
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            HashMap<String,String> user;
            user = db.getUserDetails();
            firstandlast.setText(user.get("first_name")+" " + user.get("last_name"));

            btnLogout.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    userFunctions.logoutUser(getApplicationContext());
                    Intent login = new Intent(getApplicationContext(), Login.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    // Closing dashboard screen
                    finish();
                }
            });
        } else {
            // user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), Login.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            // Closing dashboard screen
            finish();
        }
    }
}
