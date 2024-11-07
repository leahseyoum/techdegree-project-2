package com.teamtreehouse.model;
import java.util.Set;
import java.util.HashSet;

public class Team  implements Comparable<Team>{
    private String mName;
    private String mCoach;
    private Set<Player> mTeamPlayers;

    public Team(String name, String coach) {
        mName = name;
        mCoach = coach;
        mTeamPlayers = new HashSet<Player>();
    }

   public String getName() {
        return mName;
   }

   public String getCoach() {
        return mCoach;
   }

    public Set<Player> getTeamPlayers() {
        return mTeamPlayers;
    }

   public boolean addPlayer(Player player) {
        if (mTeamPlayers.size() < 11) {
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
    public int compareTo(Team other) {
        return getName().compareTo(other.getName());
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
