package com.example.a2006_3.parkconnectorrunningapp.Login;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.*;
import android.widget.*;
import android.app.AlertDialog;
import android.os.AsyncTask;



import com.example.a2006_3.parkconnectorrunningapp.R;
import com.example.a2006_3.parkconnectorrunningapp.RoutePlanning.RoutePlanning;

public class Login extends AppCompatActivity implements LoginAPI.RequestListener {
    String[] perms = {"android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.INTERNET"};
    EditText user, pass;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, 200);
        }
    }

    public void redirect(View view) {
        String username = user.getText().toString();
        String password = pass.getText().toString();

        //handle empty username
        if (user.getText().toString().trim().isEmpty()) {
            user.setError("Please enter your name.");
            user.requestFocus();
        }
        //handle empty password
        else if (pass.getText().toString().trim().isEmpty()) {
            pass.setError("Please enter your password.");
            pass.requestFocus();
        }
        else {
            AsyncTask<Void, Void, String> l =  new LoginAPI(username, password, this).execute();
        }
    }

    @Override
    public void onFinished(String loginJSON) {
        if(loginJSON.contains("Success") ){
            Intent intent_redirect = new Intent(Login.this, RoutePlanning.class);
            startActivity(intent_redirect);
        }
        else{
            Toast.makeText(getApplicationContext(),"Invalid Username/Password!", Toast.LENGTH_LONG).show();
            user.setText("");
            pass.setText("");
        }

    }


    public void new_user(View view){
        Intent intent_new_user = new Intent(Login.this, Register.class);
        startActivity(intent_new_user);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 200: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

}
