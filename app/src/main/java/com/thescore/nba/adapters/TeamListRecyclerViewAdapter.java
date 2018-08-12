package com.thescore.nba.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thescore.nba.R;
import com.thescore.nba.models.Team;

import java.util.ArrayList;

public class TeamListRecyclerViewAdapter extends RecyclerView.Adapter<TeamListRecyclerViewAdapter.TeamViewHolder>{

    private ArrayList<Team> list = new ArrayList<>();
    private TeamListAdapterInterface mListener;

    public TeamListRecyclerViewAdapter(ArrayList<Team> list, TeamListAdapterInterface mListener) {
        this.list = list;
        this.mListener = mListener;
    }

    public void updateList (ArrayList<Team> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = list.get(position);
        holder.nameView.setText(team.getFullName());
        String wins = "Wins: " + String.valueOf(team.getWins());
        String losses = "Losses: " + String.valueOf(team.getLosses());
        holder.winsView.setText(wins);
        holder.lossesView.setText(losses);

        holder.container.setTag(team);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Team team = (Team) view.getTag();
                if (team != null && mListener != null){
                    mListener.onItemClick(team.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface TeamListAdapterInterface{
        void onItemClick(int teamId);
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout container;
        public TextView nameView,winsView,lossesView;


        public TeamViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.item_team_container);
            nameView = itemView.findViewById(R.id.item_team_name);
            winsView = itemView.findViewById(R.id.item_team_wins);
            lossesView = itemView.findViewById(R.id.item_team_losses);
        }
    }
}
