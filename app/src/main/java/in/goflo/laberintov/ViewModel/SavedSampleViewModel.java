package in.goflo.laberintov.ViewModel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import in.goflo.laberintov.LiveData.FirestoreQueryLiveData;
import in.goflo.laberintov.Model.Data;
import in.goflo.laberintov.Model.FinalData;
import in.goflo.laberintov.Model.FinalFingerprint;

/**
 * Created by Anisha Mascarenhas on 06-03-2018.
 */

public class SavedSampleViewModel extends ViewModel {

    private static final String TAG = "SavedSampleViewModel";
    private static final String KEY_NAME = "name";
    private static final String KEY_FINGERPRINTS = "fingerprints";
    private static final String KEY_ROOM_ID = "roomId";
    private static String roomID;

    private static Query savedSamplesQuery;
    private FirestoreQueryLiveData liveData;
    private LiveData<Integer> savedSamplesLiveData;

    private class Deserializer implements Function<QuerySnapshot, Integer> {
        @Override
        public Integer apply(QuerySnapshot querySnapshot) {
            Integer count = 0;
            List<DocumentSnapshot> fingerprints = querySnapshot.getDocuments();
            for(DocumentSnapshot doc : fingerprints){
                count += ((ArrayList<FinalFingerprint>)doc.get("data")).size();
            }
            return count;
        }
    }

    @NonNull
    public LiveData<Integer> getSavedSamplesLiveData() {
        return savedSamplesLiveData;
    }


    public void setRoomID(String ID) {
        roomID = ID;
        savedSamplesQuery = FirebaseFirestore.getInstance().collection(KEY_FINGERPRINTS).whereEqualTo(KEY_ROOM_ID, roomID);
        liveData = new FirestoreQueryLiveData(savedSamplesQuery);
        savedSamplesLiveData = Transformations.map(liveData, new Deserializer());
    }
}
