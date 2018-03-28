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
import in.goflo.laberintov.Model.BuildingDetails;

/**
 * Created by amisha on 2/3/18.
 */

public class BuildingViewModel extends ViewModel {

    private static final String TAG = "BuildingViewModel";
    private static final String KEY_NAME = "name";
    private static final String KEY_BUILDINGS = "buildings";
    private static final String KEY_LOCATION_ID = "locationId";
    private static String locationID;

    private static Query buildingQuery;
    private FirestoreQueryLiveData liveData;
    private LiveData<List<BuildingDetails>> buildingLiveData;

    private class Deserializer implements Function<QuerySnapshot, List<BuildingDetails>> {
        @Override
        public List<BuildingDetails> apply(QuerySnapshot querySnapshot) {
            List<DocumentSnapshot> buildings = querySnapshot.getDocuments();
            List<BuildingDetails> buildingList = new ArrayList<>();
            for(DocumentSnapshot building : buildings){
                BuildingDetails buildingDetails = new BuildingDetails(building.get(KEY_NAME).toString(), building.getId(), locationID);
                buildingList.add(buildingDetails);
                Log.d(TAG, "building " + buildingDetails.getBuildingID() + " " + buildingDetails.getBuildingName());
            }
            return buildingList;
        }
    }

    @NonNull
    public LiveData<List<BuildingDetails>> getBuildingLiveData() {
        return buildingLiveData;
    }


    public void setLocationID(String ID) {
        locationID = ID;
        buildingQuery = FirebaseFirestore.getInstance().collection(KEY_BUILDINGS).whereEqualTo(KEY_LOCATION_ID, locationID);
        liveData = new FirestoreQueryLiveData(buildingQuery);
        buildingLiveData = Transformations.map(liveData, new Deserializer());
    }
}
