package com.example.myapp.app.activity;

import android.app.Activity;

/**
 * Created by sshapoval on 8/12/2014.
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.myapp.app.R;
import com.example.myapp.app.utils.DatabaseHandler;
import com.example.myapp.app.utils.UserFunctions;
import com.facebook.*;
import com.facebook.model.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class RegisterWithFacebook extends Activity {
    private static final List<String> PERMISSIONS = Arrays.asList(
            "email","user_location");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Session.openActiveSession(this, true, new Session.StatusCallback() {

            // callback when session changes state
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {
                    List<String> permissions = session.getPermissions();
                    if (!isSubsetOf(PERMISSIONS, permissions)) {
                        Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
                                RegisterWithFacebook.this, PERMISSIONS);
                        session.requestNewReadPermissions(newPermissionsRequest);
                        return;
                    }
                    // make request to the /me API
                    Request.newMeRequest(session, new Request.GraphUserCallback() {

                        // callback after Graph API response with user object
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                UserFunctions userFunction = new UserFunctions();
                                userFunction.logoutUser(getApplicationContext());
                                db.addUser(user.getFirstName(), user.getLastName(),user.getProperty("email").toString(),user.getId(),"data");

                                Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);

                                // Close all views before launching Dashboard
                                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(dashboard);
//                                TextView welcome = (TextView) findViewById(R.id.firstandlast);
//                                welcome.setText("Hello " + user.getName() + "!");

                                // Close Login Screen
                                finish();

                            }
                        }
                    }).executeAsync();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Activity result", "  " + requestCode + "   " + resultCode + "    " + data.toString());
        Session.getActiveSession().onActivityResult(this, requestCode,
                resultCode, data);
    }

    private boolean isSubsetOf(Collection<String> subset,
                               Collection<String> superset) {
        for (String string : subset) {
            if (!superset.contains(string)) {
                return false;
            }
        }
        return true;
    }

}
