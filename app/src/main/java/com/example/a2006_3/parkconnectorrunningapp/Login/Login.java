package com.example.a2006_3.parkconnectorrunningapp.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.*;
import android.widget.*;
import android.app.AlertDialog;

import com.example.a2006_3.parkconnectorrunningapp.R;
import com.example.a2006_3.parkconnectorrunningapp.RoutePlanning.RoutePlanning;

public class Login extends AppCompatActivity {

    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);
    }

    public void redirect(View view){
        String username = user.getText().toString();
        String password = pass.getText().toString();

        //handle empty username
        if(user.getText().toString().trim().isEmpty()){
            user.setError("Please enter your name.");
            user.requestFocus();
        }
        //handle empty password
        else if(pass.getText().toString().trim().isEmpty()){
            pass.setError("Please enter your password.");
            pass.requestFocus();
        }
        else{
            if(username.equals("admin") && password.equals("admin")) {
                Intent intent_redirect = new Intent(Login.this, RoutePlanning.class);
                startActivity(intent_redirect);
            }
            else{
                //handle incorrect credentials
                AlertDialog.Builder wrongCredentials  = new AlertDialog.Builder(this);
                wrongCredentials.setMessage("Incorrect Username or Password.");
                wrongCredentials.setTitle("Error");
                wrongCredentials.setPositiveButton("OK", null);
                wrongCredentials.setCancelable(true);
                wrongCredentials.create().show();

                wrongCredentials.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {}
                });

            }
        }
    }

    public void new_user(View view){
        Intent intent_new_user = new Intent(Login.this, Register.class);
        startActivity(intent_new_user);
    }
}
