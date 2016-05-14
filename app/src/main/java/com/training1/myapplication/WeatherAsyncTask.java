package com.training1.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.training1.myapplication.Model.City;

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
    private DataHelperClass helper;

    public WeatherAsyncTask(ProgressDialog pd,Context context){
        this.pd=pd;
        this.context=context;
        helper = new DataHelperClass();
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
                String url ="http://api.wunderground.com/api/" + helper.API_KEY + "/conditions/q/" + helper.getStateName(temp[0]) +
                        "/"+helper.getCityName(temp[1])+".json";
                String response = helper.makeAPICall(url);

                helper.prepareWeatherData(response);
        }
        return helper.getCities();
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
}