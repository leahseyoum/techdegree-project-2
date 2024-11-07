package com.teamtreehouse.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class TeamsCollection {
    private List<Team> mTeams;

    public TeamsCollection() {
        mTeams = new ArrayList<Team>();
    }


    public void addTeam(Team team) {
        mTeams.add(team);
    }

    public List<Team> getTeams() {
        mTeams.sort( new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return mTeams;
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
