package com.teamtreehouse.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Comparator;

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

    public Set<Team> getTeams() {
        List<Team> list = new ArrayList<>(mTeams);
        list.sort( new Comparator<Team>() {
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
