package oosdcoursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    //Teams ArrayList to store all our Teams instances. I have used an array list because I wanted to be able to
    // dynamically allocate memory, so there can be as few or as many teams as possible. They are also more compatible
    // with the JavaFX choice menu's.
    List<Team> teams = new ArrayList<Team>();

    // Import all of our JavaFX widgets into the controller

    @FXML
    private TextField txtAddTeam;

    @FXML
    private Label lblAddTeamIndicator;

    @FXML
    private TextField txtAddPlayer;

    @FXML
    private Label lblAddPlayerIndicator;

    @FXML
    private ChoiceBox choiceAddPlayersTeam;

    @FXML
    private Label lblTeamStats1;

    @FXML
    private Label lblTeamStats2;

    @FXML
    private Label lblTeamStats3;

    @FXML
    private Label lblTeamStats4;


    public Admin() {
        boolean hasAdminPower = true;
    }

    public void initTeamsList() {
        // Function to initialise our drop-down teams list menu using an Observable List
        // PARAMETERS : N/A
        // RETURNS : void
        int teamsListIndex = 0;
        // get the filepath of the teams file
        String teamsFP = "src/oosdcoursework/teams.csv";
        String line = "";
        // set up the ObservableList (subtype of ArrayList) which is used as the drop-down menu options
        ObservableList<String> teamsList = FXCollections.observableArrayList();
        //
        try {
            BufferedReader br = new BufferedReader(new FileReader(teamsFP));
            // while there are still lines to be read
            while ((line = br.readLine()) != null) {
                String[] teamLine = line.split(",", -1);
                teamsList.add(teamLine[0]);
                // create new Team object and add to list
                teams.add(new Team(teamLine[0], Integer.parseInt(teamLine[1]), Integer.parseInt(teamLine[2]), Integer.parseInt(teamLine[3])));
                teamsListIndex++;
            }

            choiceAddPlayersTeam.setItems(teamsList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewTeam(ActionEvent event) {
        // Function to add a team to our teams.csv file
        // PARAMETERS : button action
        // RETURNS : void
        String fp = "src/oosdcoursework/teams.csv";
        try {
            // create our file writer
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fp, true)));
            // add the team to our teams file, with 0 matches played, 0 matches won, 0 sets won
            pw.println(txtAddTeam.getText() + ",0,0,0,");
            lblAddTeamIndicator.setText("Team successfully added");

            pw.flush();
            pw.close();

            initTeamsList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewPlayer(ActionEvent event) {
        // Function add a new player and their team to our players.csv file
        // PARAMETERS : button action
        // RETURNS : void
        String playerFp = "src/oosdcoursework/players.csv";
        try {
            // create our file writer
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(playerFp, true)));
            // add the player and their team to our players.csv file
            pw.println(txtAddPlayer.getText() + "," + choiceAddPlayersTeam.getValue() + ",");
            pw.flush();
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createTeamsObjects() {
        // Function to initialise the teams array periodically, so we can work on it.
        // PARAMETERS : N/A
        // RETURNS : void
        int teamsListIndex = 0;
        String teamsFP = "src/oosdcoursework/teams.csv";
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(teamsFP));
            // while there are still lines to be read
            while ((line = br.readLine()) != null) {
                // create an array from our line, using the comma as the delimiter
                String[] teamLine = line.split(",", -1);
                // create new Team object and add to list
                teams.add(new Team(teamLine[0], Integer.parseInt(teamLine[1]), Integer.parseInt(teamLine[2]), Integer.parseInt(teamLine[3])));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateFixtures (ActionEvent event) {
        // Function to generate team fixtures. Uses a simple algorithm that makes sure teams don't play themselves, and
        //          each team only plays eachother twice, home and away.
        // PARAMETERS : button event
        // RETURNS : void

        // initialise the teams array
        createTeamsObjects();
        String fp = "src/oosdcoursework/fixtures.csv";
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fp)));
            // for each team in column 1 ..
            for (int i = 0; i < (teams.size()); i++) {
                // for each team in column 2 ..
                for (int j = i + 1; j < teams.size(); j++) {
                    // create a match between each team, home and away.
                    pw.println(teams.get(i).name + "," + teams.get(j).name + ",");
                    pw.println(teams.get(j).name + "," + teams.get(i).name + ",");
                }
            }
            pw.flush();
            pw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openStatsReport(ActionEvent event) throws IOException {
        // Function to open the stats page
        // PARAMETERS : N/A
        // RETURNS : void

        // load the stats page
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("oosdcoursework/StatsPage.fxml"));
        Scene scene = new Scene(root, 1200, 720);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public void displayStats(ActionEvent event) {
        // Function to display each team stats
        // PARAMETERS : N/A
        // RETURNS : void
        createTeamsObjects();
        // loop through the teams ArrayList and append their stats to the stats label
        for (int i = 0; i < teams.size(); i++) {
            lblTeamStats1.setText(lblTeamStats1.getText() + teams.get(i).name + " : Matches Played = " + teams.get(i).matchesPlayed + ", Matches Won = " + teams.get(i).matchesWon + ", Sets Won : " + teams.get(i).setsWon + "\n");
        }
    }




}
