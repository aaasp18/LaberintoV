package com.example.amisha.laberintov.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amisha.laberintov.Model.BuildingDetails;
import com.example.amisha.laberintov.R;

import java.util.ArrayList;

/**
 * Created by Anisha Mascarenhas on 28-01-2018.
 */

public class BuildingListAdapter extends RecyclerView.Adapter<BuildingListAdapter.ViewHolder> {

    private ArrayList<BuildingDetails> list;
    private Context context;

    public BuildingListAdapter(Context context, ArrayList<BuildingDetails> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String buildingName =  list.get(position).getBuildingName();
        holder.name.setText(buildingName);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;

        public ViewHolder(View view){
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int position = getLayoutPosition();
            Intent intent = new Intent(context, SelectRoomActivity.class);
            intent.putExtra(context.getString(R.string.buildingID), list.get(position).getBuildingID());
            intent.putExtra(context.getString(R.string.buildingName), list.get(position).getBuildingName());
            context.startActivity(intent);
        }
    }
}
