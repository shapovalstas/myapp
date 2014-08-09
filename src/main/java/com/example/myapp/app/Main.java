package com.example.myapp.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.myapp.app.login.Login;

public class Main extends Activity implements View.OnClickListener {
    private Button mLogin, mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mRegister = (Button) findViewById(R.id.btnSignUp);
        mLogin = (Button) findViewById(R.id.btnLogin);

        //register listeners
        mRegister.setOnClickListener(this);
        mLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin:
                Intent ilogin = new Intent(this, Login.class);
                startActivity(ilogin);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
            case R.id.btnSignUp:
                Intent iregister = new Intent(this,Register.class);
                startActivity(iregister);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
            default:
                break;
        }
    }

}
