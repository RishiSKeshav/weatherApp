package com.training1.iMobile3.Presentation;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.training1.iMobile3.Model.City;
import com.training1.iMobile3.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener {

    private View mView;
    private ProgressDialog pd;
    private ResultBroadcastReceiver mReceiver;
    private IntentFilter mResultFilter;

    private TextView mCityName;
    private TextView mDegree;
    private TextView mWeather;
    private TextView mFeelsLike;
    private TextView mHumidity;
    private TextView mWindDirection;
    private TextView mWindSpeed;
    private City mCity;

    private CardView mCardView;
    private TextView mWeatherH;
    private TextView mFeelsLikeH;
    private TextView mHumidityH;
    private TextView mWindDirectionH;
    private TextView mWindSpeedH;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 0;
    private GoogleApiClient mGoogleApiClient;
    private double latitude = 0;
    private double longitude = 0;

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_location, container, false);

        mReceiver = new ResultBroadcastReceiver();
        mResultFilter = new IntentFilter("locationCompleteAction");

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Downloading please wait...");
        pd.setCanceledOnTouchOutside(false);

        initilisation(mView);

        return mView;
    }

    private void initilisation(View view){

        mCityName =(TextView) view.findViewById(R.id.city);
        mDegree = (TextView) view.findViewById(R.id.degree);
        mWeather =(TextView) view.findViewById(R.id.weather);
        mFeelsLike =(TextView) view.findViewById(R.id.feelsLike);
        mHumidity =(TextView) view.findViewById(R.id.humidity);
        mWindDirection =(TextView) view.findViewById(R.id.windDirection);
        mWindSpeed =(TextView) view.findViewById(R.id.windspeed);

        mCardView = (CardView) view.findViewById(R.id.card_view);
        mWeatherH =(TextView) view.findViewById(R.id.weatherH);
        mFeelsLikeH =(TextView) view.findViewById(R.id.feelsLikeH);
        mHumidityH =(TextView) view.findViewById(R.id.humidityH);
        mWindDirectionH =(TextView) view.findViewById(R.id.windDirectionH);
        mWindSpeedH =(TextView) view.findViewById(R.id.windspeedH);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        getContext().registerReceiver(mReceiver, mResultFilter);

        checkPermission();

    }

    @Override
    public void onPause() {
        super.onPause();

        getContext().unregisterReceiver(mReceiver);
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();

            }
            return false;
        }
        return true;
    }

    private void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mGoogleApiClient.connect();
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            //   if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            // }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    //if (checkPlayServices()) {
                    // Building the GoogleApi client
                    buildGoogleApiClient();
                    //}

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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

    @Override
    public void onConnected(Bundle bundle) {
        getLocation();
    }

    private void currentLocation() {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            new WeatherAsyncTaskLocation(getActivity(), pd).execute(String.valueOf(latitude) + "," + String.valueOf(longitude));
        } else
            showAlert();
    }

    private void getLocation() {
        LocationManager locationManager = null;

        if (locationManager == null) {
            locationManager = (LocationManager) getContext()
                    .getSystemService(Context.LOCATION_SERVICE);
        }

        if (isGpsEnabled(locationManager)) {
            currentLocation();
        } else {
            showSettingsAlert();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private boolean isGpsEnabled(LocationManager locationManager) {
        return locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void showAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // Setting Dialog Title
        alertDialog.setTitle("OOPS!!!");

        // Setting Dialog Message
        alertDialog.setMessage("Something Went Wrong. ");

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

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

            String flag = intent.getExtras().get("flag").toString();

            if (flag.equals("SUCCESS")) {
                mCity = (City) intent.getExtras().get("cityByLocation");

                if (mCity != null) {
                    mCardView.setVisibility(View.VISIBLE);
                    mWeatherH.setVisibility(View.VISIBLE);
                    mFeelsLikeH.setVisibility(View.VISIBLE);
                    mHumidityH.setVisibility(View.VISIBLE);
                    mWindDirectionH.setVisibility(View.VISIBLE);
                    mWindSpeedH.setVisibility(View.VISIBLE);

                    mCityName.setText(mCity.getCityName());
                    mDegree.setText(mCity.getDegree());
                    mWeather.setText(mCity.getWeather());
                    mFeelsLike.setText(mCity.getFeelsLike());
                    mHumidity.setText(mCity.getHumidity());
                    mWindDirection.setText(mCity.getWindDirection());
                    mWindSpeed.setText(mCity.getWindSpeed());
                }
            }
            else
                showAlert();
        }
    }
}
