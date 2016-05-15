package com.training1.myapplication;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private final String PERMISSION_NOT_GRANTED = "permission_not_granted";
    private final String NO_PROVIDER = "NO_PROVIDER";
    private final String SOMETHING_WENT_WRONG="SOMETHING_WENT_WRONG";

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_location, container, false);

        location = new LocationClass();
        receiver = new ResultBroadcastReceiver();
        resultFilter = new IntentFilter("locationCompleteAction");

        cityName = (TextView) view.findViewById(R.id.city);
        degree = (TextView) view.findViewById(R.id.degree);
        weather = (TextView) view.findViewById(R.id.weather);
        feelsLike = (TextView) view.findViewById(R.id.feelsLike);
        humidity = (TextView) view.findViewById(R.id.humidity);
        windDirection = (TextView) view.findViewById(R.id.windDirection);
        windSpeed = (TextView) view.findViewById(R.id.windspeed);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Downloading please wait...");
        pd.setCanceledOnTouchOutside(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getContext().registerReceiver(receiver, resultFilter);

        String flag = location.currentLocation(getActivity());
        if (flag != null) {
            if (flag.equals(PERMISSION_NOT_GRANTED)) {
                Toast.makeText(getContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
            } else if (flag.equals(NO_PROVIDER)) {
                showSettingsAlert();
            }else if(flag.equals(SOMETHING_WENT_WRONG)) {
                Toast.makeText(getContext(), "OOPS!! Something went wrong", Toast.LENGTH_SHORT).show();
            }else{
                new WeatherAsyncTaskLocation(getActivity(), pd).execute(location.currentLocation(getActivity()));
            }
        }
        else
            Log.d("flag","null");
    }

    @Override
    public void onPause() {
        super.onPause();

        getContext().unregisterReceiver(receiver);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // Setting Dialog Title
        alertDialog.setTitle("Location setting");

        // Setting Dialog Message
        alertDialog.setMessage("Location is not allowed. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public class ResultBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("locationFragment", "onRecieve");
            if (pd.isShowing())
                pd.dismiss();

            city = (City) intent.getExtras().get("cityByLocation");

            cityName.setText(city.getCityName());
            degree.setText(city.getDegree());
            weather.setText(city.getWeather());
            feelsLike.setText(city.getFeelsLike());
            humidity.setText(city.getHumidity());
            windDirection.setText(city.getWindDirection());
            windSpeed.setText(city.getWindSpeed());
        }
    }
}
