package com.training1.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RishiS on 5/8/2016.
 */
class WeatherAsyncTask extends AsyncTask<List<String>,Void,ArrayList<City>> {

    private ProgressDialog pd;
    private Context context;

    final String API_KEY = "f5277aaefe60dff1";
    private ArrayList<City> cities = new ArrayList<City>();

    public WeatherAsyncTask(ProgressDialog pd,Context context){
        this.pd=pd;
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.show();
    }

    @Override
    protected ArrayList<City> doInBackground(List<String>... params) {

            for (int i = 0; i < params[0].size(); i++) {
                String[] temp = params[0].get(i).split(",");
                String url ="http://api.wunderground.com/api/" + API_KEY + "/conditions/q/" + getStateName(temp[0]) +"/"+getCityName(temp[1])+".json";
                String response = makeAPICall(url);

                prepareWeatherData(response);
        }
        return cities;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<City> cities) {
        super.onPostExecute(cities);

        MySingleton.getInstance().setCities(cities);

        Intent broadcastResults = new Intent();
        //broadcastResults.putParcelableArrayListExtra("cities",cities);
        broadcastResults.setAction("completeAction");
        context.sendBroadcast(broadcastResults);
    }

    private String getStateName(String s){

        if(s.contains(" "))
            s=s.replace(" ", "_");

        return s;
    }

    private String getCityName(String s){
        if(s.contains(" "))
            s=s.replace(" ","_");

        return s;
    }

    private String makeAPICall(String urlString){
        String response=null;
        int code;
        String message;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

           /* code = conn.getResponseCode();
            message = conn.getResponseMessage();*/

            InputStream in1 = conn.getInputStream();
            StringBuilder sb = new StringBuilder();

            int chr;
            while ((chr = in1.read()) != -1) {
                sb.append((char) chr);
            }

            response = sb.toString();
        }
        catch(IOException ex){
            Log.d("IOEX",ex.getMessage());

        }
    return response;
    }

    private void prepareWeatherData(String response){
        City city = new City();

        try {
            JSONObject outerObj = new JSONObject(response).getJSONObject("current_observation");
            JSONObject displayLocation = outerObj.getJSONObject("display_location");

            city.setCityName(displayLocation.getString("city"));
            city.setTime(outerObj.getString("observation_time"));
            city.setDegree(outerObj.getString("temp_f") + " F");
            city.setFeelsLike(outerObj.getString("feelslike_f") + " F");
            city.setHumidity(outerObj.getString("relative_humidity"));
            city.setWeather(outerObj.getString("weather"));
            city.setWindDirection(outerObj.getString("wind_dir"));
            city.setWindSpeed(outerObj.getString("wind_mph"));

            cities.add(city);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}