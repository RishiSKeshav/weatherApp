package com.training1.iMobile3.Presentation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.training1.iMobile3.Model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RishiS on 5/13/2016.
 */
class WeatherAsyncTask extends AsyncTask<List<String>,Void,ArrayList<City>> {

    private ProgressDialog pd;
    private Context mContext;
    private DataHelperClass mHelper;
    private final String SUCCESS="SUCCESS";
    private final String SOMETHING_WENT_WRONG="SOMETHING_WENT_WRONG";

    public WeatherAsyncTask(ProgressDialog pd,Context context){
        this.pd=pd;
        this.mContext=context;
        mHelper = new DataHelperClass();
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
                String url ="http://api.wunderground.com/api/" + mHelper.API_KEY + "/conditions/q/"
                        + mHelper.getStateName(temp[0]) +
                        "/"+mHelper.getCityName(temp[1])+".json";
                String response = mHelper.makeAPICall(url);

                mHelper.prepareWeatherData(response);
        }
        return mHelper.getCities();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<City> cities) {
        super.onPostExecute(cities);

        Intent broadcastResults = new Intent();
        if(cities!=null) {
            MySingleton.getInstance().setCities(cities);
            broadcastResults.putExtra("flag", SUCCESS);
        }
        else{
            broadcastResults.putExtra("flag", SOMETHING_WENT_WRONG);
        }
        //broadcastResults.putParcelableArrayListExtra("cities",cities);
        broadcastResults.setAction("completeAction");
        mContext.sendBroadcast(broadcastResults);
    }
}