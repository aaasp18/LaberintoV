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
    private static final String KEY_BUILDING_ID = "buildingID";
    private static final String KEY_NAME = "name";
    private static final String KEY_ROOM = "rooms";

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
                RoomDetails roomDetails = new RoomDetails(room.get(KEY_NAME).toString(), room.getId(), buildingID);
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
        roomQuery = FirebaseFirestore.getInstance().collection(KEY_ROOM).whereEqualTo(KEY_BUILDING_ID, buildingID);
        liveData = new FirestoreQueryLiveData(roomQuery);
        roomLiveData = Transformations.map(liveData, new RoomViewModel.Deserializer());
    }
}
