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
import in.goflo.laberintov.Model.RoomDetails;

/**
 * Created by Anisha Mascarenhas on 02-03-2018.
 */

public class RoomViewModel extends ViewModel {


    private static final String TAG = "RoomViewModel";
    private static final String BUILDING_ID = "buildingID";
    private static String buildingID;

    private static Query roomQuery;
    private FirestoreQueryLiveData liveData;
    private LiveData<List<RoomDetails>> roomLiveData;

    private class Deserializer implements Function<QuerySnapshot, List<RoomDetails>> {
        @Override
        public List<RoomDetails> apply(QuerySnapshot querySnapshot) {
            List<DocumentSnapshot> rooms = querySnapshot.getDocuments();
            List<RoomDetails> roomList = new ArrayList<>();
            for(DocumentSnapshot room : rooms){
                RoomDetails roomDetails = new RoomDetails(room.get("name").toString(), room.getId());
                roomList.add(roomDetails);
                Log.d(TAG, "Room: " + roomDetails.getRoomID() + " " + roomDetails.getRoomName());
            }
            return roomList;
        }
    }

    @NonNull
    public LiveData<List<RoomDetails>> getRoomLiveData() {
        return roomLiveData;
    }


    public void setBuildingId(String ID) {
        buildingID = ID;
        roomQuery = FirebaseFirestore.getInstance().collection("rooms").whereEqualTo(BUILDING_ID, buildingID);
        liveData = new FirestoreQueryLiveData(roomQuery);
        roomLiveData = Transformations.map(liveData, new RoomViewModel.Deserializer());
    }
}
