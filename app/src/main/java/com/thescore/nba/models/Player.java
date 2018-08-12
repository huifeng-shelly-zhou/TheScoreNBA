package com.thescore.nba.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Player {
    private int id = 0;
    private String lastName = "";
    private String firstName = "";
    private String position = "";
    private int number = 0;

    public Player() {
    }

    public Player(JSONObject data) {
        try {
            if (data.has("id")){
                id = data.getInt("id");
            }
            if (data.has("number")){
                number = data.getInt("number");
            }
            if (data.has("last_name")){
                lastName = data.getString("last_name");
            }
            if (data.has("first_name")){
                firstName = data.getString("first_name");
            }
            if (data.has("position")){
                position = data.getString("position");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getPosition() {
        return position;
    }

    public int getNumber() {
        return number;
    }
}
