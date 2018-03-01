package com.example.amisha.laberintov.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amisha.laberintov.Model.RoomDetails;
import com.example.amisha.laberintov.R;

import java.util.ArrayList;

/**
 * Created by amisha on 27/2/18.
 */

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder> {

    private ArrayList<RoomDetails> list;
    private Context context;

    public RoomListAdapter(Context context, ArrayList<RoomDetails> list) {
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
        String roomName =  list.get(position).getRoomName();
        holder.name.setText(roomName);

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
            Intent intent = new Intent(context, TrainingActivity.class);
            intent.putExtra(context.getString(R.string.roomName), list.get(position).getRoomName());
            intent.putExtra(context.getString(R.string.roomID), list.get(position).getRoomID());
            context.startActivity(intent);
        }
    }
}
