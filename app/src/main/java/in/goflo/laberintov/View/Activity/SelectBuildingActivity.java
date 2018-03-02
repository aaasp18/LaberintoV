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

import in.goflo.laberintov.Model.BuildingDetails;
import in.goflo.laberintov.R;
import in.goflo.laberintov.View.Adapter.BuildingListAdapter;
import in.goflo.laberintov.ViewModel.BuildingViewModel;

/**
 * Created by amisha on 2/3/18.
 */

public class SelectBuildingActivity extends AppCompatActivity {

    static final private String TAG = "SelectBuildingActivity";
    RecyclerView recyclerView;
    BuildingListAdapter adapter;
    String locationID, locationName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_building);

        locationID = getIntent().getStringExtra(this.getString(R.string.locationID));
        locationName = getIntent().getStringExtra(this.getString(R.string.locationName));

        setTitle(locationName);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        BuildingViewModel buildingViewModel =
                ViewModelProviders.of(this).get(BuildingViewModel.class);

        buildingViewModel.setLocationID(locationID);

        LiveData<List<BuildingDetails>> buildingLiveData = buildingViewModel.getBuildingLiveData();
        buildingLiveData.observe(this, new Observer<List<BuildingDetails>>() {
            @Override
            public void onChanged(@Nullable List<BuildingDetails> buildingDetails) {
                if(buildingDetails != null) {
                    adapter = new BuildingListAdapter(getApplicationContext(), buildingDetails);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }
        });
    }
}
