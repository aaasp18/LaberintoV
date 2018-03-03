package in.goflo.laberintov.View.Activity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.reactivewifi.AccessRequester;
import com.github.pwittchen.reactivewifi.ReactiveWifi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.goflo.laberintov.Helper.FirestoreManager;
import in.goflo.laberintov.Helper.LocationManager;
import in.goflo.laberintov.Model.AccessPoint;
import in.goflo.laberintov.Model.Data;
import in.goflo.laberintov.Model.Fingerprint;
import in.goflo.laberintov.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by Anisha Mascarenhas on 03-03-2018.
 */

public class TrainingActivity extends AppCompatActivity{

    private static final int REQUEST_MULTIPLE_PERMISSIONS = 1;
    private static final String PERMISSION_MSG = "Location Services Permission required for this app";
    private static final String TAG = "train";

    private LocationManager locationManager;
    private Button startButton, stopButton;
    private TextView fingerprintsTextView, savedSamplesTextView;
    private Disposable wifiSubscription;
    private ListView apsListView;
    int count;

    HashMap<String, String> accessPoints;
    ArrayAdapter<String> adapter;
    List<String> accessPointList;

    String roomID, roomName;

    ArrayList<List<ScanResult>> scanResults;
    ArrayList<AccessPoint> fingerprint;
    ArrayList<Fingerprint> dataFingerprint;
    public static double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        roomID = getIntent().getStringExtra(getString(R.string.roomID));
        roomName = getIntent().getStringExtra(getString(R.string.roomName));

        setTitle("Training " + roomName);

        apsListView = (ListView) findViewById(R.id.aps_listview);
        accessPoints = new HashMap<>();
        accessPointList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.view_aps, R.id.name, accessPointList);


        fingerprintsTextView = (TextView) findViewById(R.id.textview_fingerprints);
        savedSamplesTextView = (TextView) findViewById(R.id.textView_saved_samples);
        startButton = (Button) findViewById(R.id.button_start);

        stopButton = (Button) findViewById(R.id.button_stop);
        stopButton.setVisibility(View.INVISIBLE);

        dataFingerprint = new ArrayList<>();
        locationManager = new LocationManager();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, " [aret" + getParent());
                startTraining();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTraining();
            }
        });
    }

    private void startTraining() {
        if(LocationManager.checkAndRequestPermissions(this)) {
            startButton.setVisibility(View.INVISIBLE);
            stopButton.setVisibility(View.VISIBLE);
            readFingerprints();
        }
    }

    private void readFingerprints(){
        boolean fineLocationPermissionNotGranted =
                ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean coarseLocationPermissionNotGranted =
                ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;

        if (fineLocationPermissionNotGranted && coarseLocationPermissionNotGranted) {
            return;
        }

        if (!AccessRequester.isLocationEnabled(this)) {
            AccessRequester.requestLocationAccess(this);
            return;
        }
        scanResults = new ArrayList<>();
        count = 0;
        locationManager.getLatLng(this);
        try {
            wifiSubscription = ReactiveWifi.observeWifiAccessPoints(this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<ScanResult>>() {
                        @Override
                        public void accept(List<ScanResult> results) throws Exception {
                            displayAccessPoints(results);
                            scanResults.add(results);
                            count++;
                            fingerprintsTextView.setText( getString(R.string.fingerprints_collected) + count);

                        }
                    });
        }catch (SecurityException e){
            Log.d(TAG, e.getMessage());
        }
    }

    public void displayAccessPoints(List<ScanResult> results){
        for (ScanResult ap : results) {
            accessPoints.put(ap.BSSID, ap.SSID);
        }
        logAccessPoints(accessPoints);
        accessPointList.clear();
        accessPointList.addAll(accessPoints.values());
        adapter.notifyDataSetChanged();
        apsListView.setAdapter(adapter);
    }

    public void logAccessPoints(HashMap<String, String> accessPoints){
        Log.d(TAG + " APS",  accessPoints.keySet().toString());
        Log.d(TAG  + " APS", accessPoints.values().toString());
    }

    public void storeResults() {
        count = 0;
        for (List<ScanResult> results : scanResults) {
            fingerprint = new ArrayList<>();
            for (ScanResult ap : results) {
                fingerprint.add(new AccessPoint(ap.SSID,ap.BSSID,ap.level));
            }
            count++;
            logFingerprints(fingerprint.toString(), count);
            dataFingerprint.add(new Fingerprint(latitude, longitude, fingerprint));
        }
        ArrayList<String> listAPs = getAccessPoints(accessPoints);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Log.d(TAG, dateFormat.format(new Date()) + " " + System.currentTimeMillis() + "\n" + listAPs.toString() );
        Data data = new Data(roomID, dataFingerprint, dateFormat.format(new Date()), System.currentTimeMillis(), listAPs);
        FirestoreManager.updateFirestore(data);
        Toast.makeText(this, "Data Uploaded", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<String> getAccessPoints(HashMap<String, String> accessPoints){
        ArrayList<String> listAPs = new ArrayList<>();
        for (String key : accessPoints.keySet()){
            listAPs.add(accessPoints.get(key) + " - "  + key);
        }
        return listAPs;
    }

    public void stopTraining(){
        Log.d(TAG, "stop");
//        stopButton.setEnabled(false);
        stopButton.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
        startButton.setText("ADD DATA");
        safelyUnsubscribe(wifiSubscription);
        locationManager.stopLocationUpdates();
        storeResults();
        fingerprintsTextView.setText( getString(R.string.fingerprints_collected) + count);
    }

    private void safelyUnsubscribe(Disposable... subscriptions) {
        for (Disposable subscription : subscriptions) {
            if (subscription != null && ! subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (locationManager.mGoogleApiClient != null) {
            locationManager.mGoogleApiClient.connect();
        }
    }

    @Override protected void onPause() {
        super.onPause();
        safelyUnsubscribe(wifiSubscription);
        locationManager.stopLocationUpdates();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        if ( requestCode == REQUEST_MULTIPLE_PERMISSIONS && grantResults.length > 0 ) {
            boolean allGranted = true;
            for (int i = 0; i < permissions.length; i++){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
                    allGranted = false;
            }
            if (allGranted) {
                Log.d(TAG, "Location services permission granted");
                readFingerprints();
            }
            else {
                Log.d(TAG, "Some permissions are not granted ask again ");
                /* shouldShowRequestPermissionRationale(). This method returns true if
                the app has requested this permission previously and the user denied the request.
                If the user turned down the permission request in the past and chose the Don't ask again option
                in the permission request system dialog, this method returns false. The method also returns
                false if a device policy prohibits the app from having that permission*/

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION) ) {
                    showDialog(PERMISSION_MSG, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int choice) {
                            switch (choice) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    LocationManager.checkAndRequestPermissions(TrainingActivity.this);
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    String msg = "Fingerprints cannot be obtained till Location Permissions are enabled";
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                    finish();
                            }
                        }
                    });
                }
                else {
                    stopButton.setEnabled(false);
                    Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                    // TODO: Navigate to correct activity
                }
            }
        }
    }

    private void showDialog(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", listener)
                .create()
                .show();
    }

    private void logFingerprints(String fingerprint, int count) {
        String msg = count + ") " + fingerprint;
        Log.d(TAG, msg);
    }
}
