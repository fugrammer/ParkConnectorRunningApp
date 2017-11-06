package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class ProfileAPI extends AsyncTask<Void, Void, String>  {

    private Exception exception;
    private String username;

    public interface RequestListener {
        void onFinished(String result);
    }

    private final RequestListener requestListener;

    public ProfileAPI(String username, RequestListener requestListener) {
        super();
        this.requestListener = requestListener;
        this.username = username;
    }

    protected void onPreExecute() {
    }

    protected String doInBackground(Void... params) {
        try {
            URL url = new URL("https://pr2006.herokuapp.com/getroutes?name="+username);
            Log.e("URL: ", "https://pr2006.herokuapp.com/getroutes?name="+username);
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
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "Error with Profile API response";
        }
        this.requestListener.onFinished(response);
    }
}