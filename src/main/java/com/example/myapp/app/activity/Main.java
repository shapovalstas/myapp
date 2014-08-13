package com.example.myapp.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.example.myapp.app.R;

import java.lang.reflect.Field;

import static android.view.View.OnClickListener;

public class Main extends Activity implements OnClickListener {
    private Button mLogin, mRegister;
    FragmentManager manager;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getOverflowMenu();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mRegister = (Button) findViewById(R.id.btnSignUp);
        mLogin = (Button) findViewById(R.id.btnLogin);

        //register listeners
        mRegister.setOnClickListener(this);
        mLogin.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin:
                Intent ilogin = new Intent(this, Login.class);
                ilogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ilogin);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                finish();
                break;
            case R.id.btnSignUp:
                Intent iregister = new Intent(this, ChoiceRegistrationActivity.class);
                iregister.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iregister);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                finish();
                break;
            default:
                break;
        }
    }

    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();

        else

            Toast.makeText(getBaseContext(), "Нажмите еще раз чтобы выйти",
                    Toast.LENGTH_SHORT).show();

        back_pressed = System.currentTimeMillis();

    }
}



