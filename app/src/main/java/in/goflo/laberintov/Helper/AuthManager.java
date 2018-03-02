package in.goflo.laberintov.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Anisha Mascarenhas on 02-02-2018.
 */

public class AuthManager {

    private static final String KEY_PREFERENCES = "auth";
    private final static String KEY_EMAIL = "email";
    private final static String KEY_UID = "uid";
    private final static String KEY_DISPLAY_NAME = "name";

    // Saves credentials to shared preferences
    public static void saveData(Context context, FirebaseUser user) {
        SharedPreferences sp = context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);

        sp.edit()
                .putString(KEY_EMAIL, user.getEmail())
                .putString(KEY_UID, user.getUid())
                .putString(KEY_DISPLAY_NAME, user.getDisplayName())
                .apply();
    }

    // Retrieves credentials from shared preferences
    public static String getEmail(Context context) {
        SharedPreferences sp = context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(KEY_EMAIL, null);
    }

    public static String getDisplayName(Context context){
        SharedPreferences sp = context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(KEY_DISPLAY_NAME, null);
    }

    public static String getUid (Context context){
        SharedPreferences sp = context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(KEY_UID, null);
    }

    // Deletes credentials from shared preferences
    public static void deleteData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);

        sp.edit()
                .putString(KEY_EMAIL, null)
                .putString(KEY_UID, null)
                .putString(KEY_DISPLAY_NAME, null)
                .apply();
    }
}
