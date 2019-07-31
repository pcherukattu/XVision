package com.example.ceaser007.jo;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by Ceaser007 on 6/18/2016.
 */
public class GPSTracker extends Service implements LocationListener {
    private final Context mContext;
    boolean isGPSEnabled = false;
    private String TAG = "com.android.joe2";
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Location location;
    double longitude;
    double latitude;
    private static final long MIN_DIST_CHANGE_UPDATE = 10;
    private static final long MIN_TIME_BTW_UPDATES = 100 * 60 * 1;
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();

    }

    private Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                //  Log.d(TAG, "error in gpstracker ...... not activated");
            } else {
                canGetLocation = true;
                if (isGPSEnabled) {
                    // 8.559212 76.8613415
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BTW_UPDATES, MIN_DIST_CHANGE_UPDATE, this);

                    if(locationManager!=null){
                        Log.d(TAG, "got  in 1st nd is network ...... not activated");
                           location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                         latitude=location.getLatitude();
                        longitude=location.getLongitude();
                    }


                }

            }
            if(isNetworkEnabled){
                if(location==null){
                    Log.d(TAG, "got  in 2 nd is network ...... not activated");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BTW_UPDATES,MIN_DIST_CHANGE_UPDATE,this);
                    if(locationManager!=null){
                        location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        latitude=location.getLatitude();
                        longitude=location.getLongitude();
                    }
                }
            }

        }catch (Exception e){
            Log.d(TAG,"THE ERROR IN GPSTracker.java outer "+e);
        }
        Log.d(TAG,"THE VALUES ARE "+latitude+" "+longitude);
        return location;
    }
    @Override
    public void onLocationChanged(Location location) {

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public double getLongitude(){
        if(location!=null){
            longitude=location.getLongitude();
        }
        return longitude;
    }
    public double getLatitude(){
        if(location!=null){
            latitude=location.getLatitude();
        }
        return latitude;
    }
    public boolean canGetLocation(){
        return canGetLocation;
    }
    public void showAlertDialog(){
        final android.support.v7.app.AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
        alertDialog.setTitle("GPS Settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
