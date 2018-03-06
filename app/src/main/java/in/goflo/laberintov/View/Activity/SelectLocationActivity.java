package in.goflo.laberintov.View.Activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import in.goflo.laberintov.Helper.AuthManager;
import in.goflo.laberintov.Model.LocationDetails;
import in.goflo.laberintov.R;
import in.goflo.laberintov.View.Adapter.LocationListAdapter;
import in.goflo.laberintov.ViewModel.LocationViewModel;

/**
 * Created by amisha on 1/3/18.
 */

public class SelectLocationActivity extends AppCompatActivity{

    static final private String TAG = "SelectLocationActivity";
    RecyclerView recyclerView;
    LocationListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        LocationViewModel locationViewModel =
                ViewModelProviders.of(this).get(LocationViewModel.class);

        locationViewModel.setUserid(getUserID());

        LiveData<List<LocationDetails>> locationLiveData = locationViewModel.getLocationLiveData();
        locationLiveData.observe(this, new Observer<List<LocationDetails>>() {
            @Override
            public void onChanged(@Nullable List<LocationDetails> locationDetails) {
                if(locationDetails != null) {
                    adapter = new LocationListAdapter(getApplicationContext(), locationDetails);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }
        });
    }

    private String getUserID() {
        Log.d(TAG, "userid " + AuthManager.getUid(this));
        return AuthManager.getUid(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Logout user and delete credentials
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        AuthManager.deleteData(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
