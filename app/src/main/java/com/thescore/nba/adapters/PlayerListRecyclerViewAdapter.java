package com.thescore.nba.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thescore.nba.R;
import com.thescore.nba.models.Player;
import com.thescore.nba.models.Team;

import java.util.ArrayList;

public class PlayerListRecyclerViewAdapter extends RecyclerView.Adapter<PlayerListRecyclerViewAdapter.PlayerViewHolder>{

    private ArrayList<Player> list = new ArrayList<>();

    public PlayerListRecyclerViewAdapter(ArrayList<Player> list) {
        this.list = list;

    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = list.get(position);
        holder.nameView.setText(player.getFullName());
        String number = "Number: " + String.valueOf(player.getNumber());
        String postion = "Position: " + String.valueOf(player.getPosition());
        holder.numView.setText(number);
        holder.positionView.setText(postion);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView,numView,positionView;

        public PlayerViewHolder(View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.item_player_name);
            numView = itemView.findViewById(R.id.item_player_number);
            positionView = itemView.findViewById(R.id.item_player_position);
        }
    }
}
