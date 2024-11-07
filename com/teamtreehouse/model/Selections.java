package com.teamtreehouse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Selections {
    private BufferedReader mReader;
    private Map<String, String> mMenu;
    private TeamsCollection mTeamsCollection;
    private Player[] mPlayers;

    public Selections(TeamsCollection teamsCollection) {
        mTeamsCollection = teamsCollection;
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mPlayers = Players.load();
        mMenu = new HashMap<>();
        mMenu.put("create", "Create a new team");
        mMenu.put("add", "Add player to a team");
        mMenu.put("remove", "Remove player from a team");
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

    private void showPlayers() {
        int i = 1;
        for (Player player : mPlayers) {
            System.out.printf("%d). first name: %s last name: %s  height in inches: %d previous experience: %s %n",
                    i,
                    player.getFirstName(),
                    player.getLastName(),
                    player.getHeightInInches(),
                    player.isPreviousExperience());
            i++;

        }
    }

    private Player promptSelectPlayer() throws IOException {
        System.out.println("Select a player by number: ");
        String playerString = mReader.readLine();
        int playerNumber = Integer.parseInt(playerString.trim());
        return mPlayers[playerNumber -= 1];
    }

    private void showTeams() {
       for (Team team : mTeamsCollection.getTeams()) {
           System.out.printf("%s %n",
                   team.getName());
       }
    }

    private Team promptSelectTeam() throws IOException {
        showTeams();
        System.out.println("Select a team: ");
        String teamString = mReader.readLine();
        return mTeamsCollection.getTeamByName(teamString);

    }

    private void showPlayersOnTeam(Team team) throws IOException {
        List<Player> players = team.getTeamPlayers();
        int i = 1;
        for (Player player : players) {
            System.out.printf("%d). first name: %s last name: %s  height in inches: %d previous experience: %s %n",
                    i,
                    player.getFirstName(),
                    player.getLastName(),
                    player.getHeightInInches(),
                    player.isPreviousExperience());
            i++;
        }
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
                    case "add":
                        Team team = promptSelectTeam();
                        showPlayers();
                        Player player = promptSelectPlayer();
                        boolean result = team.addPlayer(player);
                        if (!result) {
                            System.out.printf("You cannot add this player %n");
                        } else {
                            System.out.printf("%s %s added to %s %n%n",
                                    player.getFirstName(),
                                    player.getLastName(),
                                    team.getName());
                        }
                        break;
                    case "remove":
                        Team removalTeam = promptSelectTeam();
                        showPlayersOnTeam(removalTeam);
                        Player removalPlayer = promptSelectPlayer();
                        boolean removalResult = removalTeam.removePlayer(removalPlayer);
                                if (!removalResult) {
                                    System.out.println("You cannot remove this player");
                                } else {
                                    System.out.printf("%s %s was removed from %s%n",
                                            removalPlayer.getFirstName(),
                                            removalPlayer.getLastName(),
                                            removalTeam.getName());
                                }
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
