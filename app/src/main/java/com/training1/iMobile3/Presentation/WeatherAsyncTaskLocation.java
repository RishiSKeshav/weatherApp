package com.training1.iMobile3.Presentation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.training1.iMobile3.Model.City;

/**
 * Created by RishiS on 5/13/2016.
 */
public class WeatherAsyncTaskLocation extends AsyncTask<String,Void,City> {


    private DataHelperClass mHelper;
    private Context context;
    private ProgressDialog pd;
    private final String SOMETHING_WENT_WRONG="SOMETHING_WENT_WRONG";
    private final String SUCCESS="SUCCESS";

    public WeatherAsyncTaskLocation(Context context, ProgressDialog pd){
        mHelper = new DataHelperClass();
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

        String url ="http://api.wunderground.com/api/" + mHelper.API_KEY + "/conditions/q/"
                +params[0]+".json";

        String response = mHelper.makeAPICall(url);
        return mHelper.parseResponse(response);
    }

    @Override
    protected void onPostExecute(City city) {
        super.onPostExecute(city);

        Intent broadcastResults = new Intent();

        if(city!=null) {
            broadcastResults.putExtra("cityByLocation", city);
            broadcastResults.putExtra("flag", SUCCESS);
        } else {
            broadcastResults.putExtra("flag", SOMETHING_WENT_WRONG);}

        broadcastResults.setAction("locationCompleteAction");
        context.sendBroadcast(broadcastResults);
    }
}
