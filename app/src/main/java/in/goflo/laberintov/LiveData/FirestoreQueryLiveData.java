package in.goflo.laberintov.LiveData;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Created by amisha on 1/3/18.
 */

public class FirestoreQueryLiveData extends LiveData<QuerySnapshot> {

    private static final String TAG = "FirestoreQueryLiveData";
    private final Query query;
    private final MyQuerySnapshotListener listener = new MyQuerySnapshotListener();
    private ListenerRegistration registration;

    public FirestoreQueryLiveData(Query query) {
        this.query = query;
    }

    @Override
    protected void onActive() {
        super.onActive();
        registration = query.addSnapshotListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if(registration != null) {
            registration.remove();
            registration = null;
        }
    }

    private class MyQuerySnapshotListener implements EventListener<QuerySnapshot> {

        @Override
        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
            if(e != null) {
                Log.d(TAG, "Can't listen to query " + e.getMessage());
                return;
            }
            setValue(documentSnapshots);
        }
    }
}
