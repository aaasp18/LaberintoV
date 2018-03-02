package in.goflo.laberintov.ViewModel;

import android.app.DownloadManager;
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
    private static final String USERID = "userID";
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
                LocationDetails locationDetails = new LocationDetails(location.get("name").toString(), location.getId());
                locationList.add(locationDetails);
                Log.d(TAG, "loc class " + locationDetails.getLocationID() + " " + locationDetails.getLocationName());
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
        locationQuery = FirebaseFirestore.getInstance().collection("locations").whereEqualTo(USERID, userID);
        liveData = new FirestoreQueryLiveData(locationQuery);
        locationLiveData = Transformations.map(liveData, new Deserializer());
    }

}
