package in.goflo.laberintov.View.Activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import in.goflo.laberintov.Model.RoomDetails;
import in.goflo.laberintov.R;
import in.goflo.laberintov.View.Adapter.RoomListAdapter;
import in.goflo.laberintov.ViewModel.RoomViewModel;

/**
 * Created by Anisha Mascarenhas on 02-03-2018.
 */

public class SelectRoomActivity extends AppCompatActivity {

    static final String TAG = "SelectRoomActivity";
    RecyclerView recyclerView;
    RoomListAdapter adapter;
    String buildingID, buildingName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_room);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        buildingID = getIntent().getStringExtra(this.getString(R.string.buildingID));
        buildingName = getIntent().getStringExtra(this.getString(R.string.buildingName));

        setTitle(buildingName);

        RoomViewModel roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);

        roomViewModel.setBuildingId(buildingID);

        LiveData<List<RoomDetails>> roomLiveData = roomViewModel.getRoomLiveData();
        roomLiveData.observe(this, new Observer<List<RoomDetails>>() {
            @Override
            public void onChanged(@Nullable List<RoomDetails> roomDetails) {
                if(roomDetails != null) {
                    adapter = new RoomListAdapter(getApplicationContext(), roomDetails);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }
        });
    }
}
