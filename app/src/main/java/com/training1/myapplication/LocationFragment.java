package com.training1.myapplication;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.training1.myapplication.Model.City;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {

    private LocationClass location;
    private View view;
    private ProgressDialog pd;
    private ResultBroadcastReceiver receiver;
    private IntentFilter resultFilter;
    private TextView cityName;
    private TextView degree;
    private TextView weather;
    private TextView feelsLike;
    private TextView humidity;
    private TextView windDirection;
    private TextView windSpeed;
    private City city;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_location,container,false);

        location = new LocationClass();
        receiver = new ResultBroadcastReceiver();
        resultFilter = new IntentFilter("locationCompleteAction");

        cityName =(TextView) view.findViewById(R.id.city);
        degree = (TextView) view.findViewById(R.id.degree);
        weather =(TextView) view.findViewById(R.id.weather);
        feelsLike =(TextView) view.findViewById(R.id.feelsLike);
        humidity =(TextView) view.findViewById(R.id.humidity);
        windDirection =(TextView) view.findViewById(R.id.windDirection);
        windSpeed =(TextView) view.findViewById(R.id.windspeed);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Downloading please wait...");
        pd.setCanceledOnTouchOutside(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getContext().registerReceiver(receiver,resultFilter);
        new WeatherAsyncTaskLocation(getActivity(),pd).execute(location.currentLocation(getActivity()));
    }

    @Override
    public void onPause() {
        super.onPause();

        getContext().unregisterReceiver(receiver);
    }

    public class ResultBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("locationFragment", "onRecieve");
            if(pd.isShowing())
                pd.dismiss();

            city =(City) intent.getExtras().get("cityByLocation");

            cityName.setText(city.getCityName());
            degree.setText(city.getDegree());
            weather.setText(city.getWeather());
            feelsLike.setText("Feels Like "+city.getFeelsLike());
            humidity.setText("Humidity "+city.getHumidity());
            windDirection.setText("Wind "+ city.getWindDirection());
            windSpeed.setText(" "+ city.getWindSpeed());
        }
    }
}
