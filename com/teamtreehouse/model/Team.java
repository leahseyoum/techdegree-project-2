package com.teamtreehouse.model;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private String mName;
    private String mCoach;
    private List<Player> mPlayers;

    public Team(String name, String coach) {
        mName = name;
        mCoach = coach;
        mPlayers = new ArrayList<>();
    }

   public String getName() {
        return mName;
   }

   public String getCoach() {
        return mCoach;
   }

   public boolean addPlayer(Player player) {
        if (!mPlayers.contains(player) && mPlayers.size() < 11) {
            mPlayers.add(player);
            return true;
        }
        return false;
   }
}
