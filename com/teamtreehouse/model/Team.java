package com.teamtreehouse.model;

public class Team {
    private String mName;
    private String mCoach;

    public Team(String name, String coach) {
        mName = name;
        mCoach = coach;
    }

   public String getName() {
        return mName;
   }

   public String getCoach() {
        return mCoach;
   }
}
