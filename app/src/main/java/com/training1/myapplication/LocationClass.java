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
import android.widget.Toast;

/**
 * Created by RishiS on 5/12/2016.
 */
public class LocationClass implements LocationListener {

    private double latitude;
    private double longitude;

    public LocationClass(){
        this.latitude=0;
        this.longitude=0;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

        this.latitude=location.getLatitude();
        this.longitude=location.getLongitude();
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

    public String currentLocation(Context context){

        String mprovider;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return "permission not granted";
            }

            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if (location != null) {
                onLocationChanged(location);
            }

            else
                Toast.makeText(context, "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }

        return this.latitude+","+this.longitude;
    }
}
