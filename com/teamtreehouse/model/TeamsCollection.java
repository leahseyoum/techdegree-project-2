package com.teamtreehouse.model;

import java.util.*;

public class TeamsCollection {
    private Set<Team> mTeams;

    public TeamsCollection() {
        mTeams = new HashSet<Team>();
    }


    public boolean addTeam(Team team, int maxNumberOfTeams) {
        if (mTeams.size() >= maxNumberOfTeams) {
            return false;
        }
        if (mTeams.add(team)){
            return true;
        }
        return false;
    }


    public List<Team> getTeams() {
        List<Team> list = new ArrayList<>(mTeams);
        Collections.sort(list);
        return list;
    }

    public Team getTeamByName(String name)  {
        for (Team team : mTeams) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }




}
