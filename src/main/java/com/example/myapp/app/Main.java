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
        setContentView(R.layout.login_registration);
        mRegister = (Button) findViewById(R.id.sign_up);
        mLogin = (Button) findViewById(R.id.log_in);

        //register listeners
        mRegister.setOnClickListener(this);
        mLogin.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.log_in:
                Intent ilogin = new Intent(this, Login.class);
                startActivity(ilogin);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
            case R.id.sign_up:
                Intent iregister = new Intent(this,Register.class);
                startActivity(iregister);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
            default:
                break;
        }
    }

}
