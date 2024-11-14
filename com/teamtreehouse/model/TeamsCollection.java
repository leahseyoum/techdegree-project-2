package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TeamsCollection {
    private Set<Team> mTeams;

    public TeamsCollection() {
        mTeams = new HashSet<Team>();
    }


    public boolean addTeam(Team team) {
//        if (mTeams.size() >= maxNumberOfTeams) {
//            return false;
//        }
        return mTeams.add(team);
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
