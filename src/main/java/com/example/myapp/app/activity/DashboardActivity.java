package com.example.myapp.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapp.app.R;
import com.example.myapp.app.utils.DatabaseHandler;
import com.example.myapp.app.utils.UserFunctions;
import com.facebook.Session;

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
                    callFacebookLogout(getApplicationContext());
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
    /**
     * Logout From Facebook
     */
    public static void callFacebookLogout(Context context) {
        Session session = Session.getActiveSession();
        if (session != null) {

            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
                //clear your preferences if saved
            }
        } else {

            session = new Session(context);
            Session.setActiveSession(session);

            session.closeAndClearTokenInformation();
            //clear your preferences if saved

        }

    }
}
