package com.thescore.nba;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.thescore.nba.adapters.TeamListRecyclerViewAdapter;
import com.thescore.nba.models.Team;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ActivityTeamList extends AppCompatActivity implements TeamListRecyclerViewAdapter.TeamListAdapterInterface{
    private final String TAG = ActivityTeamList.class.getSimpleName();

    RecyclerView team_list_recycler;
    ProgressBar my_progress_bar;

    ArrayList<Team> teams;
    TeamListRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        team_list_recycler = findViewById(R.id.team_list_recycler);
        my_progress_bar = findViewById(R.id.my_progress_bar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(getResources().getString(R.string.nba_team));
        }

        teams = getTeamsData();
        my_progress_bar.setVisibility(View.GONE);

        adapter = new TeamListRecyclerViewAdapter(teams, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        team_list_recycler.setLayoutManager(linearLayoutManager);
        team_list_recycler.setItemAnimator(new DefaultItemAnimator());
        team_list_recycler.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nba_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_wins:
                // Sort teams by wins
                sortTeamsByWins();
                return true;

            case R.id.action_sort_losses:
                // Sort teams by losses
                sortTeamsByLosses();
                return true;

            case R.id.action_sort_name:
                // Sort teams by name
                sortTeamsByName();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private ArrayList<Team> getTeamsData(){
        ArrayList<Team> teams = new ArrayList<>();
        try {
            InputStream inputStream = getAssets().open("input.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONArray teamsArray = new JSONArray(json);
            Log.i(TAG, "Team count: " + String.valueOf(teamsArray.length()));


            for (int i = 0; i < teamsArray.length(); i++){
                teams.add(new Team(teamsArray.getJSONObject(i), false));
            }

            Collections.sort(teams, Team.TeamNameComparator);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return teams;
    }

    private void sortTeamsByName(){
        if (teams.size() > 0 && adapter != null){
            Collections.sort(teams, Team.TeamNameComparator);
            adapter.updateList(teams);
        }
    }

    private void sortTeamsByWins(){
        if (teams.size() > 0 && adapter != null){
            Collections.sort(teams, Team.TeamWinsComparator);
            adapter.updateList(teams);
        }
    }

    private void sortTeamsByLosses(){
        if (teams.size() > 0 && adapter != null){
            Collections.sort(teams, Team.TeamLossesComparator);
            adapter.updateList(teams);
        }
    }

    @Override
    public void onItemClick(int teamId) {

        Intent intent = new Intent(ActivityTeamList.this, ActivityTeam.class);
        intent.putExtra(ActivityTeam.TEAM_ID, teamId);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }
}
