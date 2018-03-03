//package in.goflo.laberintov.Helper;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.arch.lifecycle.Lifecycle;
//import android.arch.lifecycle.LifecycleObserver;
//import android.arch.lifecycle.LifecycleOwner;
//import android.arch.lifecycle.OnLifecycleEvent;
//import android.content.Context;
//import android.location.Location;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//
///**
// * Created by amisha on 2/3/18.
// */
//
//public class Manager implements LifecycleObserver, LocationListener {
//
//    public static final String TAG = "LocationManager";
//    private Context mContext;
//    private LocationManager locationManager;
//    private LocationListener locationListener;
//    private Location mLastLocation;
//
//    // Google client to interact with Google API
//    private static GoogleApiClient mGoogleApiClient = null;
//
//    // boolean flag to toggle periodic latlng updates
//    private boolean mRequestingLocationUpdates = false;
//
//    private LocationRequest mLocationRequest;
//
//    // Location updates intervals in sec
//    private static int UPDATE_INTERVAL = 4000; // 4 sec
//    private static int FASTEST_INTERVAL = 1000; // 1 sec
//    private static float DISPLACEMENT = (float) 0.1; // 1 meters
//
//    public Manager(LifecycleOwner lifecycleOwner, Context context, LocationListener locationListener) {
//        lifecycleOwner.getLifecycle().addObserver(this);
//        mContext = context;
//        this.locationListener = locationListener;
//        // First we need to check availability of play services
//        if (checkPlayServices()) {
//            mGoogleApiClient = GoogleApiClientManager.getInstance(mContext);
//            createLocationRequest();
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    public void connectApiClient() {
//        if (mGoogleApiClient != null) {
//            mGoogleApiClient.connect();
//        }
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    public void addLocationListener() {
//
//    }
//
//
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    public void removeLocationListener() {
//        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
//            fusedLocationProviderClient.removeLocationUpdates(mGoogleApiClient, this);
//            fusedLocationProviderClient.removeLocationUpdates();
//        }
//    }
//
//    /**
//     * Method to verify google play services on the device
//     * */
//    private boolean checkPlayServices() {
//        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mContext);
//        if(status == ConnectionResult.SUCCESS)
//            return true;
//        return false;
//    }
//
//
//    /**
//     * Creating location request object
//     * */
//    private void createLocationRequest() {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(UPDATE_INTERVAL);
//        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
//    }
//
//
//    @Override
//    public void onLocationChanged(Location location) {
//        mLastLocation = location;
//        Toast.makeText(activity, "Location changed!", Toast.LENGTH_SHORT).show();
//        getLocation();
//    }
//
//}
