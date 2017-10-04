package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class RouteAPI extends AsyncTask<Void, Void, String> {

    private Exception exception;
    private String distance="", lat="", lng="", dlat="", dlng="";

    public interface RequestListener {
        void onFinished(String result);
    }

    private final RequestListener requestListener;

    public RouteAPI(String distance, Location start, String destination, RequestListener requestListener) {
        super();
        this.distance = distance;
        lat = String.valueOf(start.getLatitude());
        lng = String.valueOf(start.getLongitude());
        if (destination.compareTo("")!=0) {
            dlat = destination.split(",")[0];
            dlng = destination.split(",")[1];
        }
        this.requestListener = requestListener;
    }
    protected void onPreExecute() {
    }

    protected String doInBackground(Void... params) {
        try {
            URL url = new URL("https://parkconnector.herokuapp.com/parkconnector/?distance="
                    +distance+"&lat="+lat+"&lng="+lng+"&dlat="+dlat+"&dlng="+dlng);
            Log.e("URL: ", "https://parkconnector.herokuapp.com/parkconnector/?distance="
                    +distance+"&lat="+lat+"&lng="+lng+"&dlat="+dlat+"&dlng="+dlng);
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