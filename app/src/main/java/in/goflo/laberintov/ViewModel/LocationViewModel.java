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
import in.goflo.laberintov.Model.LocationDetails;

/**
 * Created by amisha on 1/3/18.
 */

public class LocationViewModel extends ViewModel {

    private static final String TAG = "LocationViewModel";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOCATIONS = "locations";
    private static String userID;

    private static Query locationQuery;
    private FirestoreQueryLiveData liveData;
    private LiveData<List<LocationDetails>> locationLiveData;

    private class Deserializer implements Function<QuerySnapshot, List<LocationDetails>> {
        @Override
        public List<LocationDetails> apply(QuerySnapshot querySnapshot) {
            List<DocumentSnapshot> locations = querySnapshot.getDocuments();
            List<LocationDetails> locationList = new ArrayList<>();
            for(DocumentSnapshot location : locations){
                LocationDetails locationDetails = new LocationDetails(location.get(KEY_NAME).toString(), location.getId());
                locationList.add(locationDetails);
                Log.d(TAG, "location " + locationDetails.getLocationID() + " " + locationDetails.getLocationName());
            }
            return locationList;
        }
    }

    @NonNull
    public LiveData<List<LocationDetails>> getLocationLiveData() {
        return locationLiveData;
    }


    public void setUserid(String ID) {
        userID = ID;
        locationQuery = FirebaseFirestore.getInstance().collection(KEY_LOCATIONS).whereEqualTo(KEY_USER_ID, userID);
        liveData = new FirestoreQueryLiveData(locationQuery);
        locationLiveData = Transformations.map(liveData, new Deserializer());
    }

}
