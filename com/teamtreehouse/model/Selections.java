package com.teamtreehouse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Selections {
    private BufferedReader mReader;
    private Map<String, String> mMenu;
    private TeamsCollection mTeamsCollection;

    public Selections(TeamsCollection teamsCollection) {
        mTeamsCollection = teamsCollection;
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new HashMap<>();
        mMenu.put("create", "Create a new team");
        mMenu.put("quit", "Exit the program");
    }

    private String promptAction() throws IOException {
        System.out.printf("Your options are:%n");
        for (Map.Entry<String, String> option : mMenu.entrySet()) {
            System.out.printf("%s: %s%n", option.getKey(), option.getValue());
        }
        System.out.println("What do you want to do? ");
        String choice = mReader.readLine();
        return choice.trim().toLowerCase();
    }

    private String promptNewTeamName() throws IOException {
        System.out.println("Choose a team name: ");
        String teamName = mReader.readLine();
        return teamName.trim();
    }

    private String promptNewTeamCoach() throws IOException {
        System.out.println("Choose a team coach: ");
        String teamCoach = mReader.readLine();
        return teamCoach.trim();
    }

    public void run() {
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice) {
                    case "create":
                        String teamName = promptNewTeamName();
                        String teamCoach = promptNewTeamCoach();
                        Team newTeam = new Team(teamName, teamCoach);
                        mTeamsCollection.addTeam(newTeam);
                        break;
                    case "quit":
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.printf("Unknown choice:  '%s'. Try again.  %n%n%n",
                                choice);
                }
            } catch (IOException ioe) {
                System.out.println("Problem with input");
                ioe.printStackTrace();
            }
        } while(!choice.equals("quit"));
    }
}
