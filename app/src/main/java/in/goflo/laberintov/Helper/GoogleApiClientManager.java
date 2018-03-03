//package in.goflo.laberintov.Helper;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationServices;
//
///**
// * Created by amisha on 2/3/18.
// */
//
//public class GoogleApiClientManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
//
//    private static final String TAG = "GoogleApiClientManager";
//    private static GoogleApiClient googleApiClient;
//    private static Context context;
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        togglePeriodicLocationUpdates();
//        startLocationUpdates();
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        googleApiClient.connect();
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.d(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
//                + connectionResult.getErrorCode());
//    }
//
//    private GoogleApiClientManager() {
//        googleApiClient = new GoogleApiClient.Builder(context)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API).build();
//    }
//
//    private static class ApiClientCreator {
//        private static final GoogleApiClientManager INSTANCE = new GoogleApiClientManager();
//    }
//
//    public static GoogleApiClient getInstance(Context mContext) {
//        context = mContext;
//        return ApiClientCreator.INSTANCE.googleApiClient;
//    }
//}
