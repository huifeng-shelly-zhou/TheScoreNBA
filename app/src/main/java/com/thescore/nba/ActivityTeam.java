package com.thescore.nba;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.thescore.nba.adapters.PlayerListRecyclerViewAdapter;
import com.thescore.nba.models.Team;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class ActivityTeam extends AppCompatActivity {
    public static final String TEAM_ID = "NBA_TEAM_ID";

    private TextView team_wins_num,team_losses_num;
    private RecyclerView team_player_recycler;
    private PlayerListRecyclerViewAdapter adapter;
    private int teamId;
    private Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        team_wins_num = findViewById(R.id.team_wins_num);
        team_losses_num = findViewById(R.id.team_losses_num);
        team_player_recycler = findViewById(R.id.team_player_list);

        // Enable the Up button
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null){
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        // get team data
        Intent intent = getIntent();
        if (intent != null){
            teamId = intent.getIntExtra(TEAM_ID, -1);
            team = getTeamById(teamId);
        }
        else if (savedInstanceState != null){
            teamId = savedInstanceState.getInt(TEAM_ID, -1);
            team = getTeamById(teamId);
        }

        // populate data
        if (team != null && toolbar != null){
            toolbar.setTitle(team.getFullName());
            team_wins_num.setText(String.valueOf(team.getWins()));
            team_losses_num.setText(String.valueOf(team.getLosses()));

            // set player recycler view
            adapter = new PlayerListRecyclerViewAdapter(team.getPlayers());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            team_player_recycler.setLayoutManager(linearLayoutManager);
            team_player_recycler.setItemAnimator(new DefaultItemAnimator());
            team_player_recycler.setAdapter(adapter);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(TEAM_ID, teamId);
        super.onSaveInstanceState(savedInstanceState);
    }

    private Team getTeamById(int id){
        try {
            InputStream inputStream = getAssets().open("input.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONArray teamsArray = new JSONArray(json);

            for (int i = 0; i < teamsArray.length(); i++){
                Team team = new Team(teamsArray.getJSONObject(i), true);
                if (team.getId() == id){
                    return team;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
