package com.training1.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by RishiS on 5/12/2016.
 */

public class LocationClass implements LocationListener{

    private final String PERMISSION_NOT_GRANTED="permission_not_granted";
    private final String NO_PROVIDER="NO_PROVIDER";
    private final String SOMETHING_WENT_WRONG="SOMETHING_WENT_WRONG";
    private double latitude;
    private double longitude;

    public LocationClass(){
        this.latitude=0;
        this.longitude=0;
    }

    public String currentLocation(Context context){

        LocationManager locationManager=null;
        Location location=null;
        String returnValue=null;

        if (locationManager == null) {
            locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);
        }

        if (isGpsEnabled(locationManager)) {
            returnValue=getLocation(location,locationManager,context);
        }else
            returnValue= NO_PROVIDER;

        Log.d("LocationClass",returnValue);

        return returnValue;
    }

    private String getLocation(Location location,LocationManager locationManager,Context context){

        String returnValue=null;
        // The minimum distance to change Updates in meters
        final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meters
        // The minimum time between updates in milliseconds
        final long MIN_TIME_BW_UPDATES = 15000;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return PERMISSION_NOT_GRANTED;
        }

        if (location == null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    returnValue= location.getLatitude()+","+location.getLongitude();
                }
                else{
                    returnValue=SOMETHING_WENT_WRONG;
                }
            }
        }else {
            returnValue = location.getLatitude() + "," + location.getLongitude();
        }

        return returnValue;
    }

    private boolean isGpsEnabled(LocationManager locationManager){
            return locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LocationClass","onLocationChanged called");
        latitude=location.getLatitude();
        longitude=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
