package com.teamtreehouse.model;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private String mName;
    private String mCoach;
    private List<Player> mTeamPlayers;

    public Team(String name, String coach) {
        mName = name;
        mCoach = coach;
        mTeamPlayers = new ArrayList<Player>();
    }

   public String getName() {
        return mName;
   }

   public String getCoach() {
        return mCoach;
   }

    public List<Player> getTeamPlayers() {
        return mTeamPlayers;
    }

   public boolean addPlayer(Player player) {
        if (!mTeamPlayers.contains(player) && mTeamPlayers.size() < 11) {
            mTeamPlayers.add(player);
            return true;
        }
        return false;
   }

   public boolean removePlayer(Player player) {
        if (mTeamPlayers.contains(player)) {
            mTeamPlayers.remove(player);
            return true;
        }
        return false;
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return mName.equals(team.mName);
    }

    @Override
    public int hashCode() {
        return mName.hashCode();
    }
}
