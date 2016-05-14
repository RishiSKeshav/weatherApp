package com.training1.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RishiS on 5/13/2016.
 */
public class AutocompleteAsyncTask extends AsyncTask<String,Void,List<String>> {

    private DataHelperClass helper;
    private Context context;
    private ProgressDialog pd;

    public AutocompleteAsyncTask(Context context,ProgressDialog pd){
        helper = new DataHelperClass();
        this.context=context;
        this.pd=pd;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(String... params) {

        /*Log.d("text",replaceSpace(params[0]));*/

        String response = helper.makeAPICall("http://autocomplete.wunderground.com/aq?query="+replaceSpace(params[0]));
        List<String> list = helper.parseAutocompleteResponse(response);
        return list;
    }

    @Override
    protected void onPostExecute(List<String> list) {
        super.onPostExecute(list);

        Intent intent = new Intent();
        intent.setAction("autocompleteAction");
        intent.putStringArrayListExtra("suggestion",(ArrayList)list);
        context.sendBroadcast(intent);
    }

    private String replaceSpace(String text){
        return text.replace(" ","%20");
    }
}
