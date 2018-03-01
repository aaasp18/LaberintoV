package com.example.amisha.laberintov.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Anisha Mascarenhas on 02-02-2018.
 */

public class AuthManager {

    private static final String PREFERENCES_NAME = "auth";
    private final static String EMAIL = "email";
    private final static String UID = "uid";
    private final static String DISPLAY_NAME = "name";

    // Saves credentials to shared preferences
    public static void saveData(Context context, FirebaseUser user) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        sp.edit()
                .putString(EMAIL, user.getEmail())
                .putString(UID, user.getUid())
                .putString(DISPLAY_NAME, user.getDisplayName())
                .apply();
    }

    // Retrieves credentials from shared preferences
    public static String getEmail(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getString(EMAIL, null);
    }

    public static String getDisplayName(Context context){
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getString(DISPLAY_NAME, null);
    }

    public static String getUid (Context context){
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getString(UID, null);
    }

    // Deletes credentials from shared preferences
    public static void deleteData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        sp.edit()
                .putString(EMAIL, null)
                .putString(UID, null)
                .putString(DISPLAY_NAME, null)
                .apply();
    }
}
