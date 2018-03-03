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

/**
 * Created by Anisha Mascarenhas on 03-03-2018.
 */

public class FirestoreManager {

    private static final String TAG = "firestore";
    private static final String FINGERPRINT_COLLECTION = "fingerprints";
    private static final String KEY_ROOMID = "roomid";
    static int count;


    public static void updateFirestore(Data data) {

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

    public static int queryFirestore(String roomid) {
        count = 0;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection(FINGERPRINT_COLLECTION).whereEqualTo(KEY_ROOMID, roomid);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot qs) {
                Log.d(TAG, "Query Size: " + qs.size());
                List<DocumentSnapshot> docs = qs.getDocuments();
                for(DocumentSnapshot doc : docs){
                    ArrayList<Data> data = (ArrayList<Data>)doc.get("data");
                    Log.d(TAG, "Doc ID: " + doc.getId() + "Data Size: " + data.size());
                    count += data.size();
                }
                Log.d(TAG, "Count_in: " + count);
            }});
        return count;
    }
}
