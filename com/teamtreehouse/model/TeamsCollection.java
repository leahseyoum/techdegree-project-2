package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;

public class TeamsCollection {
    private List<Team> mTeams;

    public TeamsCollection() {
        mTeams = new ArrayList<Team>();
    }

    public void addTeam(Team team) {
        mTeams.add(team);
    }

    public List<Team> getTeams() {
        return mTeams;
    }

    public Team getTeamByName(String name) {
        for (Team team : mTeams) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }


}
