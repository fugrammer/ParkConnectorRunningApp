package com.example.a2006_3.parkconnectorrunningapp.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.a2006_3.parkconnectorrunningapp.R;
import com.example.a2006_3.parkconnectorrunningapp.RoutePlanning.RoutePlanning;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Register extends AppCompatActivity implements RegisterAPI.RequestListener {
    EditText new_user, new_pass, new_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        new_user = (EditText)findViewById(R.id.new_username);
        new_pass = (EditText)findViewById(R.id.new_password);
        new_email = (EditText)findViewById(R.id.new_emailaddress);
    }

    public void cancel(View view){
        Intent intent_cancel = new Intent(Register.this, Login.class);
        startActivity(intent_cancel);
    }


    public void register(View view){
        String username = new_user.getText().toString();
        String password = new_pass.getText().toString();
        //handle empty username
        if(new_user.getText().toString().trim().isEmpty()){
            new_user.setError("Please enter your name.");
            new_user.requestFocus();
        }
        //handle empty password
        else if(new_pass.getText().toString().trim().isEmpty()){
            new_pass.setError("Please enter your password.");
            new_pass.requestFocus();
        }
        //handle empty email
        else if(!isValidEmail(new_email.getText().toString())){
            new_email.setError("Please enter your email address.");
            new_email.requestFocus();
        }
        else {
            AsyncTask<Void, Void, String> l =  new RegisterAPI(username, password, this).execute();
        }
    }

    @Override
    public void onFinished(String registerJSON) {
        if(registerJSON.contains("Success")){
            Intent intent_redirect = new Intent(Register.this, RoutePlanning.class);
            startActivity(intent_redirect);
        }
        else{
            Toast.makeText(getApplicationContext(),"Username has been taken. Try again!", Toast.LENGTH_LONG).show();
            new_user.setText("");
            new_pass.setText("");
            new_email.setText("");
        }

    }

    private boolean isValidEmail(String Email){
        String Email_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(Email_PATTERN);
        Matcher matcher = pattern.matcher(Email);
        return matcher.matches();
    }
}
