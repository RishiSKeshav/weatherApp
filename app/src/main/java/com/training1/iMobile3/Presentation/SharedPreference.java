package com.training1.iMobile3.Presentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by RishiS on 5/13/2016.
 */


/*This class stores the state,city names which is marked as favorites by user... */
public class SharedPreference {

    private final String SHARED_PREFS ="city_list";
    private final String CITY_LIST ="weather_details";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<String> cities) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(SHARED_PREFS,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        JSONArray array = new JSONArray(cities);
        String jsonString = array.toString();

        editor.putString(CITY_LIST, jsonString);

        editor.commit();
    }

    public void addCity(Context context, String city) {
        List<String> cities = getFavorites(context);
        if (cities == null)
            cities = new ArrayList<String>();
        if(!cities.contains(city)) {
            cities.add(city);
            saveFavorites(context, cities);
        }
    }

    public ArrayList<String> getFavorites(Context context) {
        SharedPreferences settings;
        List<String> cities=new ArrayList<String>();

        settings = context.getSharedPreferences(SHARED_PREFS,
                Context.MODE_PRIVATE);


            String jsonString = settings.getString(CITY_LIST, null);

            JSONArray jsonArray;
            try {

                if(jsonString!=null) {
                    jsonArray = new JSONArray(jsonString);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        cities.add(jsonArray.getString(i));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return (ArrayList<String>) cities;
    }

    public int getCityCount(Context context){
        return getFavorites(context).size();
    }
}