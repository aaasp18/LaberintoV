package in.goflo.laberintov.Helper;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by amisha on 2/3/18.
 */

public class PermissionManager {

    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1;

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
