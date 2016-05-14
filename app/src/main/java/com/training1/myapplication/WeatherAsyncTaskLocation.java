package com.training1.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.training1.myapplication.Model.City;

/**
 * Created by RishiS on 5/12/2016.
 */
public class WeatherAsyncTaskLocation extends AsyncTask<String,Void,City> {


    private DataHelperClass helper;
    private Context context;
    private ProgressDialog pd;

    public WeatherAsyncTaskLocation(Context context, ProgressDialog pd){
        helper = new DataHelperClass();
        this.context=context;
        this.pd=pd;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.show();
    }

    @Override
    protected City doInBackground(String... params) {

        String url ="http://api.wunderground.com/api/" + helper.API_KEY + "/conditions/q/" +params[0]+".json";

        String response = helper.makeAPICall(url);
        return helper.parseResponse(response);
    }

    @Override
    protected void onPostExecute(City city) {
        super.onPostExecute(city);

        Intent broadcastResults = new Intent();
        broadcastResults.putExtra("cityByLocation", city);
        broadcastResults.setAction("locationCompleteAction");
        context.sendBroadcast(broadcastResults);
    }
}
