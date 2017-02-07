package com.example.shujareshi.ieeeworkshop;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SecondAcitivity extends AppCompatActivity {

    TextView tvlat, tvlong, tvdistance, tvtime;
    Location loc;
    float dist = 0.0f;

    long time = 0, lasttime = 0;

    // public static final  String TAG = "location";...used to give info of location of user to developer ..

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_acitivity);


        tvlat = (TextView) findViewById(R.id.tvlat);
        tvlong = (TextView) findViewById(R.id.tvlong);
        tvdistance = (TextView) findViewById(R.id.tvdistance);
        tvtime = (TextView) findViewById(R.id.tvtime);


        LocationManager locMan = (LocationManager) getSystemService(LOCATION_SERVICE);

        locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                tvlat.setText(String.valueOf(location.getLatitude()));
                tvlong.setText(String.valueOf(location.getLongitude()));
                //Log.d(TAG , "longitude = " +  String.valueOf(location.getLongitude()));
                //Log.d(TAG , "latitude = " +  String.valueOf(location.getLatitude()));

                // Location l2;

                //location.distanceTo(l2);

                if (loc == null) {
                    loc = location;
                } else {
                    dist = dist + location.distanceTo(loc);
                    loc = location;
                }

                if (lasttime == 0) {
                    lasttime = System.currentTimeMillis();
                } else {
                    long currtime = System.currentTimeMillis();//system.currnet time automatically gives current time to the system.
                    time = time + (currtime - lasttime);
                    lasttime = currtime;
                }

                tvdistance.setText(String.valueOf(dist));
                tvtime.setText(String.valueOf(time));

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
        });
    }
}

