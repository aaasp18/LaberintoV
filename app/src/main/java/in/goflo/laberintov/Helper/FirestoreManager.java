package in.goflo.laberintov.Helper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import in.goflo.laberintov.Model.Data;
import in.goflo.laberintov.Model.FinalData;

/**
 * Created by Anisha Mascarenhas on 03-03-2018.
 */

public class FirestoreManager {

    private static final String TAG = "firestore";
    private static final String FINGERPRINT_COLLECTION = "fingerprints";
    private static final String KEY_ROOMID = "roomId";

    public static void updateFirestore(FinalData data) {

        //TODO: @anisham197 assign roomID and write to server

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(FINGERPRINT_COLLECTION).document();
        docRef.set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}
