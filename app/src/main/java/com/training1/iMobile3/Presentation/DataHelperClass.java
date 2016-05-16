package com.training1.iMobile3.Presentation;

import android.util.Log;

import com.training1.iMobile3.Model.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RishiS on 5/13/2016.
 */


/*This class is needs a background thread as it is mainly used in making network calls */
public class DataHelperClass {

    final String API_KEY = "f5277aaefe60dff1";
    private ArrayList<City> mCities = new ArrayList<City>();
    private final String SOMETHING_WENT_WRONG="SOMETHING_WENT_WRONG";

    public String getStateName(String s){

        if(s.contains(" "))
            s=s.replace(" ", "_");

        return s;
    }

    public String getCityName(String s){
        if(s.contains(" "))
            s=s.replace(" ","_");

        return s;
    }

    public String makeAPICall(String urlString){
        String response="";
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
            Log.d("IOEX", ex.getMessage());

            response=SOMETHING_WENT_WRONG;

        }
        return response;
    }

    public void prepareWeatherData(String response){

        mCities.add(parseResponse(response));
    }

    public City parseResponse(String response){
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
            city.setImageUrl(outerObj.getString("icon_url"));

        } catch (JSONException e) {
            e.printStackTrace();

            /*Catching JSONExcetpion and making city=null. So that a message can be shown to user
            that something went wrong*/
            city=null;
        }

        return city;
    }

    public List<String> parseAutocompleteResponse(String response){

        List<String> list = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONObject(response).getJSONArray("RESULTS");

            for(int i=0;i<jsonArray.length();i++){
                JSONObject obj =jsonArray.getJSONObject(i);
                list.add(obj.getString("name"));
            }
        }
        catch (JSONException ex){

            /*Catching JSONExcetpion and making list=null. So that a message can be shown to user
            that something went wrong*/
            list=null;
        }
        return list;
    }

    public ArrayList<City> getCities(){
        return mCities;
    }
}
