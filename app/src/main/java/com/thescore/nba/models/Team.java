package com.thescore.nba.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;

public class Team {
    private int id = 0;
    private int wins = 0;
    private int losses = 0;
    private String fullName = "";
    private ArrayList<Player> players = new ArrayList<>();

    public Team() {
    }

    public Team(JSONObject data, boolean includedPlayer) {
        try {
            if (data.has("id")){
                id = data.getInt("id");
            }
            if (data.has("wins")){
                wins = data.getInt("wins");
            }
            if (data.has("losses")){
                losses = data.getInt("losses");
            }
            if (data.has("full_name")){
                fullName = data.getString("full_name");
            }

            if (data.has("players") && includedPlayer){
                JSONArray array = data.getJSONArray("players");
                for (int i = 0; i < array.length(); i++){
                    players.add(new Player(array.getJSONObject(i)));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public String getFullName() {
        return fullName;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    //Comparator for sorting the list by team fullName
    public static Comparator<Team> TeamNameComparator = new Comparator<Team>() {

        public int compare(Team t1, Team t2) {
            String teamName1 = t1.getFullName().toUpperCase();
            String teamName2 = t2.getFullName().toUpperCase();

            //ascending order
            return teamName1.compareTo(teamName2);
        }
    };

    /*Comparator for sorting the list by wins*/
    public static Comparator<Team> TeamWinsComparator = new Comparator<Team>() {

        public int compare(Team t1, Team t2) {

            int wins1 = t1.getWins();
            int wins2 = t2.getWins();

            /*For descending order*/
            return wins2-wins1;
        }
    };

    /*Comparator for sorting the list by wins*/
    public static Comparator<Team> TeamLossesComparator = new Comparator<Team>() {

        public int compare(Team t1, Team t2) {

            int loss1 = t1.getLosses();
            int loss2 = t2.getLosses();

            /*For descending order*/
            return loss2-loss1;
        }
    };

    @Override
    public String toString() {
        return "team name: " + fullName + "; wins: " + String.valueOf(wins) + "; losses: " + String.valueOf(losses);
    }
}
