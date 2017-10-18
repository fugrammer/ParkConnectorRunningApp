package com.example.a2006_3.parkconnectorrunningapp.Login;

/**
 * Created by szkoh on 18/10/17.
 */

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginAPI extends AsyncTask<Void, Void, String> {
    private String username="", password="";

    public interface RequestListener {
        void onFinished(String result);
    }
    private RequestListener requestListener = null;

    public LoginAPI(String username, String password, RequestListener requestListener){
        super();
        this.username = username;
        this.password = password;
        this.requestListener = requestListener;
    }

    protected void onPreExecute() {
    }

    protected String doInBackground(Void... params) {
        try {
            URL url = new URL("https://pr2006.herokuapp.com/login?name=" + username + "&password=" + password);
            Log.e("URL: ", "https://pr2006.herokuapp.com/login?name=" + username + "&password=" + password);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "Error with Login API response";
        }
        this.requestListener.onFinished(response);
    }
}
