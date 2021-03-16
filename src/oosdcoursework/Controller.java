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
import java.util.Arrays;
import java.util.List;

public class Controller {

    Admin admin = new Admin();

    @FXML
    private Label lblTeamRankings;

    @FXML
    private Label lblstatus;

    @FXML
    private TextField txtusername;

    @FXML
    private TextField txtpassword;

    @FXML
    private ChoiceBox chcHomeTeam;

    @FXML
    private ChoiceBox chcAwayTeam;

    @FXML
    private ChoiceBox chcAwayPlayer1;

    @FXML
    private ChoiceBox chcAwayPlayer2;

    @FXML
    private ChoiceBox chcHomePlayer1;

    @FXML
    private ChoiceBox chcHomePlayer2;

    @FXML
    private TextField txtHome1Away1_1;
    @FXML
    private TextField txtHome1Away1_2;
    @FXML
    private TextField txtHome1Away1_3;

    @FXML
    private TextField txtHome1Away2_1;
    @FXML
    private TextField txtHome1Away2_2;
    @FXML
    private TextField txtHome1Away2_3;

    @FXML
    private TextField txtHome2Away1_1;
    @FXML
    private TextField txtHome2Away1_2;
    @FXML
    private TextField txtHome2Away1_3;

    @FXML
    private TextField txtHome2Away2_1;
    @FXML
    private TextField txtHome2Away2_2;
    @FXML
    private TextField txtHome2Away2_3;
    @FXML
    private TextField txtDouble1;
    @FXML
    private TextField txtDouble2;
    @FXML
    private TextField txtDouble3;
    @FXML
    private TextField txtFinalScores;
    @FXML
    private Label lblFixturesResults;
    @FXML
    private Label lblTeamRankingsIndicator;

//    List<TextField> setScores = new ArrayList<TextField>(Arrays.asList(txtHome1Away1_1, txtHome1Away1_2, txtHome1Away1_3, txtHome1Away2_1, txtHome1Away2_2, txtHome1Away2_3,
//            txtHome2Away1_1, txtHome2Away1_2, txtHome2Away1_3, txtHome2Away2_1, txtHome2Away2_2, txtHome2Away2_3, txtDouble1, txtDouble2, txtDouble3));



    //Teams ArrayList to store all our Teams instances. I have used an array list because I wanted to be able to
    // dynamically allocate memory, so there can be as few or as many teams as possible. They are also more compatible
    // with the JavaFX choice menu's.
    List<Team> teams = new ArrayList<Team>();

    private void loadPage (String pagePath, int width, int height) throws Exception {
        // simple function to quickly load pages. Avoids repeating code.
        // PARAMETERS : file path, window width, window height
        // RETURNS : N/A
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(pagePath));
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showLogin (ActionEvent event) throws Exception {
        // Show the login page when Admin Page is selected.
        loadPage("/oosdcoursework/Login.fxml", 400, 400);
    }

    public void adminLogin (ActionEvent event) throws Exception {
        // Function to authenticate admin login.
        if (txtusername.getText().equals("admin") && txtpassword.getText().equals("pass")) {
            lblstatus.setText("Login successful");

            loadPage("/oosdcoursework/AdminPage.fxml", 1200, 720);
        } else {
            lblstatus.setText("Login failed");
        }
    }

    public void showTeamLists(ActionEvent event) throws Exception {
        // Function to set the teams lists in the scoresheet page
        // PARAMETER : button action
        int teamsListIndex = 0;
        // filepath
        String teamsFP = "src/oosdcoursework/teams.csv";
        String line = "";
        // create a javafx observable list for the choice menu
        ObservableList<String> teamsList = FXCollections.observableArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader(teamsFP));
            // while there are still lines to read
            while ((line = br.readLine()) != null) {
                // split the line into component parts
                String[] teamLine = line.split(",", -1);
                teamsList.add(teamLine[0]);
                // create new Team object and add to list
                teams.add(new Team(teamLine[0], Integer.parseInt(teamLine[1]), Integer.parseInt(teamLine[2]), Integer.parseInt(teamLine[3])));
                teamsListIndex++;
            }
            // set the choice boxes values
            chcHomeTeam.setItems(teamsList);
            chcAwayTeam.setItems(teamsList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createTeamsObjects() {
        // Function to create the teams objects. Same as in Admin. Currently unused.
        String teamsFP = "src/oosdcoursework/teams.csv";
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(teamsFP));

            while ((line = br.readLine()) != null) {
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

    public void showPlayerLists(ActionEvent event) throws Exception {
        // Function to initialise the players lists in the scoresheet, based on the teams we passed.
        // PARAMETERS : button event
        // RETURNS : N/A

        // create the JavaFX observable lists for the choice boxes.
        ObservableList<String> playerListHome = FXCollections.observableArrayList();
        ObservableList<String> playerListAway = FXCollections.observableArrayList();
        String line = "";
        // create the file reader.
        BufferedReader br = new BufferedReader(new FileReader("src/oosdcoursework/players.csv"));
        // while there are still lines to read ..
        while ((line = br.readLine()) != null) {
            // Split our line using comma as the delimiter.
            String[] playerLine = line.split(",", -1);
            // if the home team matches ..
            if (playerLine[1].equals(chcHomeTeam.getValue())) {
                // add to home players list
                playerListHome.add(playerLine[0]);
            }
            else if (playerLine[1].equals(chcAwayTeam.getValue())) {
                playerListAway.add(playerLine[0]);
            }

        }
        // set choice boxes.
        chcHomePlayer1.setItems(playerListHome);
        chcHomePlayer2.setItems(playerListHome);
        chcAwayPlayer1.setItems(playerListAway);
        chcAwayPlayer2.setItems(playerListAway);

    }

    public void openStatsReport(ActionEvent event) throws Exception {
        // open stats page.
        loadPage("/oosdcoursework/StatsPage.fxml", 1200, 720);
    }

    public void showTeamRankings(ActionEvent event) throws Exception {
        // function to list the top three teams in the tournament, based on matches won.
        // PARAMETERS : button event
        // RETURNS : N/A
        //
        // create an ArrayList to hold our teams that we can work on. Reason for using an array list rather than standard array is given above.
        List<Team> localTeamsList = new ArrayList<Team>();
        // file path
        String fp = "src/oosdcoursework/teams.csv";
        BufferedReader br = new BufferedReader(new FileReader(fp));
        String line = "";
        // while there are still lines to read ..
        while ((line = br.readLine()) != null) {
            // split line using comma as the delimiter
            String[] teamLine = line.split(",", -1);
            // add the team to our local list
            localTeamsList.add(new Team(teamLine[0], Integer.parseInt(teamLine[1]), Integer.parseInt(teamLine[2]), Integer.parseInt(teamLine[3])));
        }
        // Bubble sort algorithm. I have chosen to use a bubble sort, as it is a very easy sorting algorithm to implement
        // It is inefficient compared to other sorts, such as an insertion sort, however when only working with a handful
        // of elements, it makes very little difference at all. If anything, with this few elements, a bubble sort is
        // probably more efficient, as there are less actions for the compiler and processor to take.
        // This is also not the most efficient implementation of a bubble sort, as it checks elements at the end of the array
        // despite them already being in the correct order, and doesn't use a while loop to check if the array is sorted already,
        // however for the same reasons given above, I have chosen to implement a simpler, cruder variation.
        //
        // for each element in the array ..
        for (int i = 0; i < localTeamsList.size() - 1; i++) {
            // for each second order element in the array
            for (int j = 0; j < localTeamsList.size() - i - 1; j++) {
                // if the current element is smaller than the next one (sorting backwards here)
                if (localTeamsList.get(j).matchesWon < localTeamsList.get(j + 1).matchesWon) {
                    // temporary team instance created
                    Team temp = localTeamsList.get(j);
                    localTeamsList.set(j, localTeamsList.get(j + 1));
                    localTeamsList.set(j + 1, temp);
                }
            }
        }

        lblTeamRankings.setText("1st : " + localTeamsList.get(0).name + "\n\n 2nd : " + localTeamsList.get(1).name + "\n\n 3rd : " + localTeamsList.get(2).name);
        lblTeamRankingsIndicator.setText("Team Rankings:");

    }

    public void viewFixturesAndResults(ActionEvent event) throws Exception {
        String fp = "src/oosdcoursework/fixtures.csv";
        BufferedReader br = new BufferedReader(new FileReader(fp));
        String line = "";

        while ((line = br.readLine()) != null) {
            String[] teamLine = line.split(",", -1);
            lblFixturesResults.setText(lblFixturesResults.getText() + "Home team: " + teamLine[0] + " vs " + teamLine[1] + " Away team | Result: " + teamLine[2] + "\n");
        }
    }

    private List<String> readTeamsFile() throws IOException {
        String line = "";
        String teamsFP = "src/oosdcoursework/teams.csv";
        BufferedReader tbr = new BufferedReader(new FileReader(teamsFP));
        List<String> teamsFile = new ArrayList<String>();

        while ((line = tbr.readLine()) != null) {
            teamsFile.add(line);
        }
        return teamsFile;
    }

    private List<String> readResultsFile() throws IOException {
        String line = "";
        String resultsFP = "src/oosdcoursework/results.csv";
        BufferedReader rbr = new BufferedReader(new FileReader(resultsFP));
        List<String> resultsFile = new ArrayList<String>();

        while ((line = rbr.readLine()) != null) {
            resultsFile.add(line);
        }
        return resultsFile;
    }

    public void openScoreSheet(ActionEvent event) throws Exception {
        TextField setScores[] = {txtHome1Away1_1, txtHome1Away1_2, txtHome1Away1_3, txtHome1Away2_1, txtHome1Away2_2, txtHome1Away2_3,
                txtHome2Away1_1, txtHome2Away1_2, txtHome2Away1_3, txtHome2Away2_1, txtHome2Away2_2, txtHome2Away2_3, txtDouble1, txtDouble2, txtDouble3};
        String resultsFP = "src/oosdcoursework/results.csv";
        String homeTeam = (String)chcHomeTeam.getValue();
        String awayTeam = (String)chcAwayTeam.getValue();

        BufferedReader br = new BufferedReader(new FileReader(resultsFP));

        String line = "";

        while ((line = br.readLine()) != null) {
            String[] matchResult = line.split(",", -1);
            if (matchResult[0].equals(homeTeam) && matchResult[1].equals(awayTeam)) {
                chcHomePlayer1.setValue(matchResult[3]);
                chcHomePlayer2.setValue(matchResult[4]);
                chcAwayPlayer1.setValue(matchResult[5]);
                chcAwayPlayer2.setValue(matchResult[6]);
                for (int i = 0; i < setScores.length; i++) {
                    setScores[i].setText(matchResult[i + 7]);
                }
                txtFinalScores.setText(matchResult[matchResult.length - 1]);
            }
        }
    }

    public void calculateScores(ActionEvent event) throws Exception {
        String homeTeam = (String) chcHomeTeam.getValue();
        String awayTeam = (String) chcAwayTeam.getValue();
        String homePlayer1 = (String) chcHomePlayer1.getValue();
        String homePlayer2 = (String) chcHomePlayer2.getValue();
        String awayPlayer1 = (String) chcAwayPlayer1.getValue();
        String awayPlayer2 = (String) chcAwayPlayer2.getValue();
        TextField setScores[] = {txtHome1Away1_1, txtHome1Away1_2, txtHome1Away1_3, txtHome1Away2_1, txtHome1Away2_2, txtHome1Away2_3,
                txtHome2Away1_1, txtHome2Away1_2, txtHome2Away1_3, txtHome2Away2_1, txtHome2Away2_2, txtHome2Away2_3, txtDouble1, txtDouble2, txtDouble3};
        String winningTeam = "";
        String teamsFP = "src/oosdcoursework/teams.csv";
        String resultsFP = "src/oosdcoursework/results.csv";
        List<String> teamsFile = readTeamsFile();
        List<String> resultsFile = readResultsFile();

        boolean gameAlreadyPlayed = false;
        int gameLineIndex = -1;

        PrintWriter tpw = new PrintWriter(new BufferedWriter(new FileWriter(teamsFP, false)));
        PrintWriter pw = new PrintWriter(new FileWriter(resultsFP, true));
        PrintWriter rpwNoAppend = new PrintWriter(new FileWriter(resultsFP, false));


        for (int i = 0; i < resultsFile.size(); i++) {
            String[] curLine = resultsFile.get(i).split(",", -1);
            if (curLine[0].equals(homeTeam) && curLine[1].equals(awayTeam)) {
                gameAlreadyPlayed = true;
                gameLineIndex = i;
            }
        }

        if (!gameAlreadyPlayed) {
            Integer homeTeamScore = 0;
            Integer awayTeamScore = 0;

            for (int i = 0; i < setScores.length; i++) {
                String[] setResult = setScores[i].getText().split(":");
                if (Integer.parseInt(setResult[0]) == 11) {
                    homeTeamScore++;
                } else if (Integer.parseInt(setResult[1]) == 11) {
                    awayTeamScore++;
                }
            }

            if (homeTeamScore > awayTeamScore) winningTeam = homeTeam;
            else winningTeam = awayTeam;

            // TODO : FIX THIS //
            pw.print(homeTeam + "," + awayTeam + "," + winningTeam + "," + homePlayer1 + "," + homePlayer2 + "," + awayPlayer1 + "," + awayPlayer2 + ",");
            for (int i = 0; i < setScores.length; i++) pw.print(setScores[i].getText() + ",");
            pw.print(homeTeamScore.toString() + ":" + awayTeamScore.toString() + ',');
            pw.print("\n");

            for (int i = 0; i < teamsFile.size(); i++) {
                String[] curLine = teamsFile.get(i).split(",", -1);
                if (curLine[0].equals(homeTeam) || curLine[0].equals(awayTeam)) {
                    curLine[1] = String.valueOf(Integer.parseInt(curLine[1]) + 1);

                    if (curLine[0].equals(winningTeam)) {
                        Integer newVal = Integer.parseInt(curLine[2]) + 1;
                        curLine[2] = newVal.toString();
                    }

                    if (curLine[0].equals(homeTeam)) {
                        Integer newVal = Integer.parseInt(curLine[3]) + homeTeamScore;
                        curLine[3] = newVal.toString();
                    } else {
                        Integer newVal = Integer.parseInt(curLine[3]) + awayTeamScore;
                        curLine[3] = newVal.toString();
                    }

                    String newLine = curLine[0] + ',' + curLine[1] + ',' + curLine[2] + ',' + curLine[3] + ',';
                    teamsFile.set(i, newLine);
                }
                tpw.println(teamsFile.get(i));
                txtFinalScores.setText(homeTeamScore.toString() + ":" + awayTeamScore.toString());

            }
            pw.flush();
            pw.close();

        } else {
            Integer homeTeamScore = 0;
            Integer awayTeamScore = 0;

            homeTeamScore = 0;
            awayTeamScore = 0;

            String oldWinningTeam = "";
            Integer oldHomeSetsWon = 0;
            Integer oldAwaySetsWon = 0;

            for (int i = 0; i < resultsFile.size(); i++) {
                String[] curLine = resultsFile.get(i).split(",", -1);
                if (i == gameLineIndex) {
                    oldWinningTeam = curLine[2];
                    String[] finalScore = curLine[curLine.length - 2].split(":", 2);
                    System.out.println(finalScore[0]);
                    System.out.println(finalScore[1]);
                    oldHomeSetsWon = Integer.parseInt(finalScore[0]);
                    oldAwaySetsWon = Integer.parseInt(finalScore[1]);
                    for (int j = 0; j < setScores.length; j++) {
                        curLine[j + 7] = setScores[7].getText();
                    }
                }

                for (int z = 0; z < setScores.length; z++) {
                    String[] setResult = setScores[z].getText().split(":");
                    if (Integer.parseInt(setResult[0]) == 11) {
                        homeTeamScore++;
                    } else if (Integer.parseInt(setResult[1]) == 11) {
                        awayTeamScore++;
                    }
                }

                System.out.println(homeTeamScore);
                System.out.println(awayTeamScore);
                rpwNoAppend.print(curLine[0] + "," + curLine[1] + "," + curLine[2] + "," + curLine[3] + "," + curLine[4] + "," + curLine[5] + "," + curLine[6] + ",");
                for (int k = 0; k < setScores.length; k++) rpwNoAppend.print(setScores[k].getText() + ",");
                rpwNoAppend.print(homeTeamScore.toString() + ":" + awayTeamScore.toString() + ',');
                rpwNoAppend.print("\n");
            }

            for (int i = 0; i < teamsFile.size(); i++) {
                String[] curTeamLine = teamsFile.get(i).split(",", -1);
                if (curTeamLine[0].equals(homeTeam) || curTeamLine[0].equals(awayTeam)) {

                    if (curTeamLine[0].equals(winningTeam) && !curTeamLine[0].equals(oldWinningTeam)) {
                        Integer newVal = Integer.parseInt(curTeamLine[2]) + 1;
                        curTeamLine[2] = newVal.toString();
                    }

                    if (curTeamLine[0].equals(oldWinningTeam) && !curTeamLine[0].equals(winningTeam)) {
                        Integer newVal = Integer.parseInt(curTeamLine[2]) - 1;
                        curTeamLine[2] = newVal.toString();
                    }

                    if (curTeamLine[0].equals(homeTeam)) {
                        Integer newVal = (Integer.parseInt(curTeamLine[3]) - oldHomeSetsWon) + homeTeamScore;
                        curTeamLine[3] = newVal.toString();
                    } else {
                        Integer newVal = (Integer.parseInt(curTeamLine[3]) - oldAwaySetsWon) + awayTeamScore;
                        curTeamLine[3] = newVal.toString();
                    }



                    String newLine = curTeamLine[0] + ',' + curTeamLine[1] + ',' + curTeamLine[2] + ',' + curTeamLine[3] + ',';
                    teamsFile.set(i, newLine);
                }
                tpw.println(teamsFile.get(i));

            }
            txtFinalScores.setText(homeTeamScore.toString() + ":" + awayTeamScore.toString());

            rpwNoAppend.flush();
            rpwNoAppend.close();
        }

            tpw.flush();
            tpw.close();
    }
}
