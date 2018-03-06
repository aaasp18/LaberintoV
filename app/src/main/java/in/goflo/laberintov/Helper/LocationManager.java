package in.goflo.laberintov.Helper;

import android.app.Activity;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import in.goflo.laberintov.View.Activity.TrainingActivity;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;


/**
 * Created by goflo on 5/2/18.
 */

public class LocationManager implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = "LocationManager";
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1;
    Activity activity;
    private Location mLastLocation;
    private double latitude, longitude;
    // Google client to interact with Google API
    public GoogleApiClient mGoogleApiClient = null;

    // boolean flag to toggle periodic latlng updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 4000; // 4 sec
    private static int FASTEST_INTERVAL = 1000; // 1 sec
    private static float DISPLACEMENT = (float) 0.1; // 1 meters


    public void getLatLng (Activity activity) {
        Log.d(TAG, "get latlng");
        this.activity = activity;
        // First we need to check availability of play services
        if (checkPlayServices()) {
            buildGoogleApiClient();
            createLocationRequest();
        }
    }

    /**
     * Method to verify google play services on the device
     * */
    //TODO : check if this method is required
    private boolean checkPlayServices() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if(status == ConnectionResult.SUCCESS)
            return true;
        return false;
    }

    /**
     * Creating google api client object
     * */
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Creating location request object
     * */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }


    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;
        Toast.makeText(activity, "Location changed!", Toast.LENGTH_SHORT).show();
        getLocation();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        togglePeriodicLocationUpdates();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    private void getLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        }catch (SecurityException e){
            Log.d(TAG, e.getMessage());
        }

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            Log.d(TAG, "latitude " + latitude + " long " + longitude);
            TrainingActivity.latitude = latitude;
            TrainingActivity.longitude = longitude;

        } else {
            Toast.makeText(activity, "(Couldn't obtain location. Make sure location services is enabled on the device)", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to toggle periodic location updates
     * */
    // TODO: check toggle function
    private void togglePeriodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            // Starting the location updates
            startLocationUpdates();
            Log.d(TAG, "Periodic location updates started!");
        } else {
            mRequestingLocationUpdates = false;
            // Stopping the location updates
            stopLocationUpdates();
            Log.d(TAG, "Periodic location updates stopped!");
        }
    }
    /**
     * Starting the location updates
     * */
    private void startLocationUpdates() {
        Log.d(TAG, "start");
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates( mGoogleApiClient, mLocationRequest, this);
        }catch (SecurityException e){
            Log.d(TAG, e.getMessage());
        }
    }

    /**
     * Stopping location updates
     */
    public void stopLocationUpdates() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }
    }

    public static boolean checkAndRequestPermissions(Activity activity) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION_CODE);
            return false;
        }
        else {
            return true;
        }
    }
}
