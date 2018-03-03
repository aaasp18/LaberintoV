//package in.goflo.laberintov.View.Activity;
//
//import android.content.DialogInterface;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import in.goflo.laberintov.Helper.LocationManager;
//import in.goflo.laberintov.Helper.PermissionManager;
//import in.goflo.laberintov.R;
//
//import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
//import static android.Manifest.permission.ACCESS_FINE_LOCATION;
//
///**
// * Created by amisha on 2/3/18.
// */
//
//public class TrainingActivity extends AppCompatActivity {
//
//    private static final int REQUEST_MULTIPLE_PERMISSIONS = 1;
//    private static final String TAG = "TrainingActivity";
//    private String roomID, roomName;
//    private Button startButton, stopButton;
//    private TextView fingerprintsTextView, savedSamplesTextView;
//    private ListView apsListView;
//    ArrayAdapter<String> adapter;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_training);
//
//        roomID = getIntent().getStringExtra(getString(R.string.roomID));
//        roomName = getIntent().getStringExtra(getString(R.string.roomName));
//
//        setTitle("Training " + roomName);
//
//        adapter = new ArrayAdapter<String>(this, R.layout.view_aps, R.id.name, accessPointList);
//
//        fingerprintsTextView = (TextView) findViewById(R.id.textview_fingerprints);
//        savedSamplesTextView = (TextView) findViewById(R.id.textView_saved_samples);
//        startButton = (Button) findViewById(R.id.button_start);
//
//        stopButton = (Button) findViewById(R.id.button_stop);
//        stopButton.setVisibility(View.INVISIBLE);
//
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, " [aret" + getParent());
//                startTraining();
//            }
//        });
//
//        stopButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stopTraining();
//            }
//        });
//    }
//
//    private void startTraining() {
//        if(PermissionManager.checkAndRequestPermissions(this)) {
//            startButton.setVisibility(View.INVISIBLE);
//            stopButton.setVisibility(View.VISIBLE);
//            readFingerprints();
//        } else {
//            bindLocationListener();
//        }
//    }
//
//    public void stopTraining(){
//        Log.d(TAG, "stop");
////        stopButton.setEnabled(false);
//        stopButton.setVisibility(View.INVISIBLE);
//        startButton.setVisibility(View.VISIBLE);
//        startButton.setText("ADD DATA");
//        safelyUnsubscribe(wifiSubscription);
//        locationManager.stopLocationUpdates();
//        storeResults();
//        fingerprintsTextView.setText( getString(R.string.fingerprints_collected) + count);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        Log.d(TAG, "Permission callback called-------");
//        if ( requestCode == REQUEST_MULTIPLE_PERMISSIONS && grantResults.length > 0 ) {
//            boolean allGranted = true;
//            for (int i = 0; i < permissions.length; i++){
//                if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
//                    allGranted = false;
//            }
//            if (allGranted) {
//                Log.d(TAG, "Location services permission granted");
//                readFingerprints();
//            }
//            else {
//                Log.d(TAG, "Some permissions are not granted ask again ");
//                /* shouldShowRequestPermissionRationale(). This method returns true if
//                the app has requested this permission previously and the user denied the request.
//                If the user turned down the permission request in the past and chose the Don't ask again option
//                in the permission request system dialog, this method returns false. The method also returns
//                false if a device policy prohibits the app from having that permission*/
//
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION) ||
//                        ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION) ) {
//                    showDialog(getString(R.string.permission_msg), listener);
//                }
//                else {
//                    stopButton.setEnabled(false);
//                    Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
//                    // TODO: Navigate to correct activity
//                }
//            }
//        }
//    }
//
//    private DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int choice) {
//            switch (choice) {
//                case DialogInterface.BUTTON_POSITIVE:
//                    LocationManager.checkAndRequestPermissions(TrainingActivity.this);
//                    break;
//                case DialogInterface.BUTTON_NEGATIVE:
//                    String msg = "Fingerprints cannot be obtained till Location Permissions are enabled";
//                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//                    finish();
//            }
//        }
//    };
//
//    private void showDialog(String message, DialogInterface.OnClickListener listener) {
//        new AlertDialog.Builder(this)
//                .setMessage(message)
//                .setPositiveButton("OK", listener)
//                .setNegativeButton("Cancel", listener)
//                .create()
//                .show();
//    }
//}
