package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class WorkoutsummaryAPI extends AsyncTask<Void, Void, String> {

    private Exception exception;
    private String username = "", min ="", sec = "", dist ="", cal="", date="";

    public interface RequestListener {
        void onFinished(String result);
    }

    private final RequestListener requestListener;

    public WorkoutsummaryAPI(String username, String min, String sec, String dist, String cal, String date, RequestListener requestListener) {
        super();
        this.username = username;
        this.min = min;
        this.sec = sec;
        this.dist = dist;
        this.cal = cal;
        this.date = date;
        this.requestListener = requestListener;
    }

    protected void onPreExecute() {
    }

    protected String doInBackground(Void... params) {
        try {
            URL url = new URL("https://pr2006.herokuapp.com/addroute?name="+username+"&minutes="+min+"&seconds="+sec+"&distance="+dist+"&calorie="+cal+"&date="+date);
            Log.e("URL: ", "https://pr2006.herokuapp.com/addroute?name="+username+"&minutes="+min+"&seconds="+sec+"&distance="+dist+"&calorie="+cal+"&date="+date);
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
            response = "Error with Route API response";
        }

        this.requestListener.onFinished(response);
    }
}