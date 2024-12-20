package com.teamtreehouse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Selections {
    private BufferedReader mReader;
    private Map<String, String> mMenu;
    private TeamsCollection mTeamsCollection;
    private List<Player> mPlayers;
    private Queue<Player> mWaitingList;


    public Selections(TeamsCollection teamsCollection) {
        mTeamsCollection = teamsCollection;
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mPlayers = new ArrayList<>(Arrays.asList(Players.load()));
        mWaitingList = new ArrayDeque<>();
        mMenu = new HashMap<>();
        mMenu.put("create", "Create a new team");
        mMenu.put("add", "Add player to a team");
        mMenu.put("waiting", "Add player to the waiting list");
        mMenu.put("remove", "Remove player from a team");
        mMenu.put("report", "View a report of a team by height");
        mMenu.put("balance", "View a report teams by experience");
        mMenu.put("roster", "View team roster");
        mMenu.put("generate", "Generate fair teams");
        mMenu.put("quit", "Exits the program");

    }

    //prompt methods
    private String promptAction() throws IOException {
        System.out.printf("%nMenu%n");
        for (Map.Entry<String, String> option : mMenu.entrySet()) {
            System.out.printf("%s: %s%n", option.getKey(), option.getValue());
        }
        System.out.println("What do you want to do? ");
        String choice = mReader.readLine();
        return choice.trim().toLowerCase();
    }


    private String promptNewTeamName() throws IOException {
        System.out.println("What is the team name: ");
        String teamName = mReader.readLine();
        return teamName.trim().toLowerCase();
    }

    private String promptNewTeamCoach() throws IOException {
        System.out.println("What is the coach name: ");
        String teamCoach = mReader.readLine();
        return teamCoach.trim().toLowerCase();
    }

    private int promptSelectPlayerIdx() throws IOException {
        System.out.println("Select a player by number: ");
        String playerString = mReader.readLine();
        int idx = Integer.parseInt(playerString.trim());
        return idx - 1;
    }

    private Team promptSelectTeam() throws IOException {
        showTeams();
        System.out.println("Select a team: ");
        String teamString = mReader.readLine();
        String finalTeamString = teamString.trim().toLowerCase();
        return mTeamsCollection.getTeamByName(finalTeamString);
    }

    // prompts to create player to add to waiting list
    public Player promptCreatePlayer() throws IOException {
        System.out.println("Creating a new player");
        System.out.println("What is the players first name?");
        String firstName = mReader.readLine().trim();
        System.out.println("What is the players last name?");
        String lastName = mReader.readLine().trim();
        System.out.println("What is the players height in inches?");
        int height = Integer.parseInt(mReader.readLine().trim());
        System.out.println("What is the players previous experience?");
        boolean experience = Boolean.parseBoolean(mReader.readLine().trim());
        return new Player(firstName, lastName, height, experience);
    }

    // prompts to add player from waiting list to team after removal of another player
    private void promptReplacePlayer(Team team) throws IOException {
        if (!mWaitingList.isEmpty()) {

            System.out.println("Do you want to add a player from the waiting list? Yes or No:");
            String response = mReader.readLine().trim();
            if (response.equalsIgnoreCase("yes")) {
                Player player = removePlayerFromWaitingList();
                team.addPlayer(player);
                System.out.printf("%s %s was added to %s",
                        player.getFirstName(),
                        player.getLastName(),
                        team.getName());
            }
        }
    }

    //shows all players
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

    //shows all teams
    private void showTeams() {
        int i = 1;
        for (Team team : mTeamsCollection.getTeams()) {
            System.out.printf(" %s %n",
                    team.getName());
            i++;
        }
    }

    //shows player on specific team
    private void showPlayersOnTeam(Team team) throws IOException {
        Set<Player> players = team.getTeamPlayers();
        Set<Player> sortedSet = new TreeSet<>(players);
        int i = 1;
        System.out.printf("%s%n",
                team.getName());
        for (Player player : sortedSet) {
            System.out.printf("%d). first name: %s last name: %s  height in inches: %d previous experience: %s %n",
                    i,
                    player.getFirstName(),
                    player.getLastName(),
                    player.getHeightInInches(),
                    player.isPreviousExperience());
            i++;

        }
    }

    private void showHeightReport(Team team) {
        Set<Player> players = team.getTeamPlayers();
        Map<String, ArrayList<Player>> heightMap = new HashMap<>();
        heightMap.put("35in - 40in", new ArrayList<>());
        heightMap.put("41in - 46in", new ArrayList<>());
        heightMap.put("47in - 50in", new ArrayList<>());
        for (Player player : players) {
            int playerHeight = player.getHeightInInches();
            if (playerHeight >= 35 && playerHeight <= 40) {
                heightMap.get("35in - 40in").add(player);
            } else if (playerHeight >= 41 && playerHeight <= 46) {
                heightMap.get("41in - 46in").add(player);
            } else if (playerHeight >= 47 && playerHeight <= 50) {
                heightMap.get("47in - 50in").add(player);
            }
        }

        for (Map.Entry<String, ArrayList<Player>> entry : heightMap.entrySet()) {
            System.out.printf("%s:%n count:%d%n",
                    entry.getKey(),
                    entry.getValue().size());
            for(Player player : entry.getValue()) {
                System.out.printf("first name: %s last name: %s  height in inches: %d previous experience: %s %n",
                        player.getFirstName(),
                        player.getLastName(),
                        player.getHeightInInches(),
                        player.isPreviousExperience());
            }
        }
    }

    private void showBalanceReport(Map<String, int[]> balanceMap) {
        for (Map.Entry<String, int[]> entry : balanceMap.entrySet()) {
            float percentageOfExperiencedPlayers = ((float)entry.getValue()[0] * 100.0f)/ ((float)entry.getValue()[0] + (float)entry.getValue()[1]);

            System.out.printf("%s:%n has experience: %d%n has no experience: %d%n percentage of experienced players: %f%n%n",
                    entry.getKey(),
                    entry.getValue()[0],
                    entry.getValue()[1],
                    percentageOfExperiencedPlayers);
        }
    }

    private Map<String, int[]> createBalanceReport() {
        List<Team> teams = mTeamsCollection.getTeams();
        Map<String, int[]> balanceMap = new HashMap<>();

        for (Team team : teams) {
            balanceMap.put(team.getName(), new int[2]);
            Set<Player> players = team.getTeamPlayers();
            int count1 = 0;
            int count2 = 0;

            for (Player player : players) {
                if (player.isPreviousExperience()) {
                    count1++;
                } else {
                    count2++;
                }
            }

            balanceMap.get(team.getName())[0] += count1;
            balanceMap.get(team.getName())[1] += count2;
        }

        return balanceMap;
    }

    private Team createTeam() throws IOException {
        String teamName = promptNewTeamName();
        String teamCoach = promptNewTeamCoach();
        Team newTeam = new Team(teamName, teamCoach);
        boolean createResult = mTeamsCollection.addTeam(newTeam);
        if (createResult) {
            System.out.printf("%s coached by %s created. %n",
                    teamName,
                    teamCoach);
        } else {
            System.out.println("Cannot create team");
        }

        return newTeam;
    }

    // generates teams by iterating through sorted list of players by experience
    private void createTeams() throws IOException {

        Team newTeam1 = createTeam();
        Team newTeam2 = createTeam();
        Team newTeam3 = createTeam();

        List<Player> playersSortedBySkill = new ArrayList<>(mPlayers);
        playersSortedBySkill.sort(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return Boolean.compare(o2.isPreviousExperience(), o1.isPreviousExperience());
            }
        });

        int teamIdx = 0;
        for (Player player : playersSortedBySkill) {
            switch (teamIdx) {
                case 0:
                    if(newTeam1.addPlayer(player)) {
                        mPlayers.remove(player);
                    };
                    break;
                case 1:
                    if(newTeam2.addPlayer(player)) {
                        mPlayers.remove(player);
                    }
                    break;
                case 2:
                    if(newTeam3.addPlayer(player)) {
                        mPlayers.remove(player);
                    }
                    break;
            }
            teamIdx = (teamIdx + 1) % 3;

        }


        showPlayersOnTeam(newTeam1);
        showPlayersOnTeam(newTeam2);
        showPlayersOnTeam(newTeam3);
    }

    // adds player back to available players after being removed from team
    public void addPlayerBack(Player player) {
        mPlayers.add(player);
        Collections.sort(mPlayers);
    }

    private void addPlayerToWaitingList(Player player) {
        mWaitingList.add(player);
        System.out.printf("%s %s added to waiting list, there are %d players on the waiting list.%n",
                player.getFirstName(),
                player.getLastName(),
                mWaitingList.size());
    }

    private Player removePlayerFromWaitingList() {
        return mWaitingList.poll();
    }

    private Player selectAvailablePlayer() throws IOException {
        Collections.sort(mPlayers);
        int idx = promptSelectPlayerIdx();
        if (idx == -1 || idx > mPlayers.size()) {
            return null;
        }
        return mPlayers.get(idx);
    }

    private Player selectPlayerFromTeam(Set<Player> teamPlayers) throws IOException {
        List <Player> tempArrayList = new ArrayList<>(teamPlayers);
        Collections.sort(tempArrayList);
        int idx = promptSelectPlayerIdx();
        if (idx == -1 || idx > teamPlayers.size()) {
            return null;
        }
        return tempArrayList.get(idx);
    }

    public void run() {
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice) {
                    case "create":
                        if(!mPlayers.isEmpty()) {
                            createTeam();
                        } else {
                            System.out.println("Cannot create team, there are no available players.");
                        }
                        break;
                    case "add":
                        Team team = promptSelectTeam();
                        if (!mPlayers.isEmpty()) {
                            showPlayers();
                            Player player = selectAvailablePlayer();
                            boolean addResult = team.addPlayer(player);
                            if (!addResult || player == null) {
                                System.out.printf("You cannot add this player %n");
                            } else {
                                mPlayers.remove(player);
                                System.out.printf("%s %s added to %s %n%n",
                                        player.getFirstName(),
                                        player.getLastName(),
                                        team.getName());
                            }
                        } else {
                            System.out.println("There are no available players");
                        }
                        break;
                    case "remove":
                        Team removalTeam = promptSelectTeam();
                        if(!removalTeam.getTeamPlayers().isEmpty()) {
                            showPlayersOnTeam(removalTeam);
                            Player removalPlayer = selectPlayerFromTeam(removalTeam.getTeamPlayers());
                            boolean removalResult = removalTeam.removePlayer(removalPlayer);
                            if (!removalResult) {
                                System.out.println("You cannot remove this player");
                            } else {
                                addPlayerBack(removalPlayer);
                                System.out.printf("%s %s was removed from %s%n",
                                        removalPlayer.getFirstName(),
                                        removalPlayer.getLastName(),
                                        removalTeam.getName());
                            }
                            promptReplacePlayer(removalTeam);
                        } else {
                            System.out.println("This team has no players");
                        }
                        break;
                    case "report":
                        Team heightReportTeam = promptSelectTeam();
                        showHeightReport(heightReportTeam);
                        break;
                    case "balance":
                        Map<String, int[]> balanceMap = createBalanceReport();
                        showBalanceReport(balanceMap);
                        break;
                    case "waiting":
                        Player newPlayer = promptCreatePlayer();
                        addPlayerToWaitingList(newPlayer);
                        break;
                    case "quit":
                        System.out.println("Goodbye!");
                        break;
                    case "roster":
                        Team rosterTeam = promptSelectTeam();
                        showPlayersOnTeam(rosterTeam);
                        break;
                    case "generate":
                        if(!mPlayers.isEmpty()) {
                            createTeams();
                        } else {
                            System.out.println("Cannot create team, there are no available players.");
                        }
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
