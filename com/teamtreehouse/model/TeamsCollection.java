package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;

public class TeamsCollection {
    private List<Team> mTeams;

    public TeamsCollection() {
        mTeams = new ArrayList<>();
    }

    public void addTeam(Team team) {
        mTeams.add(team);
    }
}
