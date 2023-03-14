package minesweeper;

import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseButton;
import java.util.Scanner;
import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Main extends Application{
    private board currGame;
    private PopupWindow lastGames;
    private int level, mines, time, supermineOn, N;
    protected static int TILE_SIZE = 40;
    private final int WIDTH = 500;
    private Label mineLabel = new Label();
    private Label flagLabel = new Label();
    private Label timeLabel = new Label();
    private Timeline timeline;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        // Tiles Grid (Board of game)
        GridPane boardPane = new GridPane();
        root.setBottom(boardPane);

        // Initialize Last Games list for "Round" Menu Item
        lastGames = new PopupWindow();

        // Menu Bar
        MenuBar menuBar = new MenuBar();
        root.setTop(menuBar);

        // Create the File menu and add some menu items
        Menu AppMenu = new Menu("Application");
        MenuItem createItem = new MenuItem("Create");
        MenuItem loadItem = new MenuItem("Load");
        MenuItem startItem = new MenuItem("Start");
        MenuItem exitItem = new MenuItem("Exit");
        AppMenu.getItems().addAll(createItem, loadItem, startItem, exitItem);

        // Add the File menu to the menu bar
        menuBar.getMenus().add(AppMenu);

        // Create the Help menu and add a menu item
        Menu detailsMenu = new Menu("Details");
        MenuItem roundItem = new MenuItem("Round");
        MenuItem solutionItem = new MenuItem("Solution");
        detailsMenu.getItems().addAll(roundItem, solutionItem);

        // Add the Help menu to the menu bar
        menuBar.getMenus().add(detailsMenu);

        // ** Handle Menu Bar **

        // * "Create" Menu Item *
        createItem.setOnAction(event -> {
            // PopUp Window
            Stage popupStage = new Stage();
            VBox popupContent = new VBox();
            popupContent.setAlignment(Pos.CENTER);
            popupContent.setSpacing(10);

            // Fields to be filled by the player about the game characteristics
            Label idLabel = new Label("SCENARIO-ID");
            idLabel.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 15));
            TextField idField = new TextField();
            Label levelLabel = new Label("LEVEL");
            levelLabel.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 15));
            TextField levelField = new TextField();
            Label minesLabel = new Label("NUMBER OF MINES");
            minesLabel.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 15));
            TextField minesField = new TextField();
            Label supermineLabel = new Label("SUPERMINE");
            supermineLabel.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 15));
            TextField supermineField = new TextField();
            Label timeLabel = new Label("MAX TIME");
            timeLabel.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 15));
            TextField timeField = new TextField();
            popupContent.getChildren().addAll(idLabel, idField, levelLabel, levelField,
                    minesLabel, minesField, supermineLabel, supermineField, timeLabel, timeField);

            // Submit Button and Scenario-ID.txt creation
            Button submitButton = new Button("Submit");
            submitButton.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 15));
            submitButton.setTextFill(Color.STEELBLUE);
            submitButton.setOnAction(submitEvent -> {
                createScenarioFile(idField.getText(), levelField.getText(), minesField.getText(), timeField.getText(),
                            supermineField.getText());
                popupStage.close();
            });
            popupContent.getChildren().add(submitButton);

            // Set the PopUp Window
            Scene popupScene = new Scene(popupContent, 200, 400);
            popupStage.setScene(popupScene);
            popupStage.setResizable(false);
            popupStage.show();
        });

        // * "Load" Menu Item *
        loadItem.setOnAction(event -> {
            Stage popupStage = new Stage();
            VBox popupContent = new VBox();
            popupContent.setAlignment(Pos.CENTER);
            popupContent.setSpacing(10);

            // Field "SCENARIO-ID" to be filled: Player specifies here which game they want to load
            Label idLabel = new Label("SCENARIO-ID");
            idLabel.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 15));
            TextField idField = new TextField();
            popupContent.getChildren().addAll(idLabel, idField);

            // Submit Button: When clicked the Scenario-ID.txt is set as input for game characteristics
            // and then the game is being set and loaded.
            Button submitButton = new Button("Submit");
            submitButton.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 15));
            submitButton.setTextFill(Color.STEELBLUE);
            submitButton.setOnAction(submitEvent -> {
                // Setup, check for errors and load game
                settings(idField.getText());
                loadGame();
                popupStage.close();
            });
            popupContent.getChildren().add(submitButton);

            // Set the PopUp Window
            Scene popupScene = new Scene(popupContent, 200, 200);
            popupStage.setScene(popupScene);
            popupStage.setResizable(false);
            popupStage.show();
        });

        // * "Start" Menu Item *
        startItem.setOnAction(event -> {
            play(root, boardPane, primaryStage);
        });

        // * "Exit" Menu Item *
        exitItem.setOnAction(event -> {
            System.exit(0);
        });

        // * "Round" Menu Item *
        roundItem.setOnAction(event -> {
            if(currGame.getWinner() != null){
                lastGames.addContent("Total Mines: " + currGame.getMines() + '\n' +
                        "Number of Rounds: " + currGame.getRound() + '\n' +
                        "Total Time: " + currGame.getTime() + '\n' +
                        "Winner: " + currGame.getWinner() + '\n' + '\n' +
                        "-*-*-*-*-*-*-*-*-*-*-" + '\n' + '\n');
            }
            lastGames.getStage().show();
        });

        // * "Solution" Menu Item *
        solutionItem.setOnAction(event -> {
            currGame.doLose();
        });

        // ** Set Scene **
        Scene scene = new Scene(root, WIDTH, WIDTH);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("MediaLab Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // settings(scenarioID): Loads and scans the text file with SCENARIO-ID = scenarioID
    // and sets game characteristics (level, total number of mines, total time in seconds,
    // supermine existence) accordingly
    private void settings(String scenarioID) {
        Exceptions exc = new Exceptions();
        try {
            Scanner sc = new Scanner(new File("medialab/SCENARIO-" + scenarioID + ".txt"));
            List<Integer> buffer = new ArrayList<Integer>();
            int l = 0;
            while (sc.hasNextLine()) {
                buffer.add(sc.nextInt());
                l++;
            }
            exc.checkFile(l);
            sc.close();

            level = buffer.get(0);
            mines = buffer.get(1);
            time = buffer.get(2);
            supermineOn = buffer.get(3);

        } catch(InvalidDescriptionException ide){
            popupMessage("Exception", "Error: " + ide.getMessage());
        } catch (FileNotFoundException e) {
            popupMessage("Exception", "Error: File not found");
        }
    }

    // loadGame(): Checks whether the game characteristics given are valid for use and if so
    // sets up the new game (currGame) and creates the "mines.txt" file for the currGame
    private void loadGame(){
        Exceptions exc = new Exceptions();
        try{
            exc.checkInput(level,mines,time,supermineOn);
            currGame = new board(level,mines,time,supermineOn);
            N = currGame.getN();
            // Setup current game
            currGame.setup();
            // Create and save mines.txt
            createMinesFile();

        } catch(InvalidValueException ive) {
            popupMessage("Exception", "Error: " + ive.getMessage());
        }
    }

    // play(root, boardPane): Redefines the boardPane according to the currGame creates currGame counters
    // (mineLabel: total number of mines, flagLabel: number of used flags, timeLabel: remaining time)
    private void play(BorderPane root, GridPane boardPane, Stage primaryStage){
        final int[] seconds = {currGame.getTime()};

        // Show total number of mines
        mineLabel = new Label("\uD83D\uDCA3 " + currGame.getMines());
        mineLabel.setStyle("-fx-font-size: 20px; -fx-font-family: 'DejaVu Sans Mono'; " +
                "-fx-font-weight: bold; -fx-background-color: #D3D3D3; " +
                "-fx-background-insets: -10 -10 -10 -10; " +
                "-fx-background-radius: 10;" + "-fx-text-fill: black;");
        //Show number of flags used
        flagLabel = new Label("\uD83D\uDEA9 " + currGame.getCntFlags());
        flagLabel.setStyle("-fx-font-size: 20px; -fx-font-family: 'DejaVu Sans Mono'; " +
                "-fx-font-weight: bold; -fx-background-color: #D3D3D3; " +
                "-fx-background-insets: -10 -10 -10 -10; " +
                "-fx-background-radius: 10;" + "-fx-text-fill: black;");
        // Show remaining time in "minutes":"seconds" form
        timeLabel = new Label();
        if (seconds[0] % 60 > 9) timeLabel.setText(" \u23F0 0" + seconds[0] / 60 + ":" + seconds[0] % 60 + " ");
        else timeLabel.setText(" \u23F0 0" + seconds[0] / 60 + ":0" + seconds[0] % 60 + " ");
        timeLabel.setStyle("-fx-font-size: 20px; -fx-font-family: 'DejaVu Sans Mono'; " +
                "-fx-font-weight: bold; -fx-text-fill: red; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0); " +
                "-fx-background-color: linear-gradient(to bottom, black 0%, #444444 50%, black 100%); " +
                "-fx-border-color: black; " + "-fx-border-width: 3px;");

        countdown(seconds);

        // Create labelBox, which contains currGame counters and add it to scene
        HBox labelBox = new HBox(50, mineLabel, timeLabel, flagLabel);
        labelBox.setAlignment(Pos.CENTER);
        root.setCenter(labelBox);

        // Reset boardPane and primaryStage to get rid of previews games boardPanes
        // and renew stage dimensions
        boardPane = new GridPane();
        root.setBottom(boardPane);
        primaryStage.setHeight(currGame.getN() * TILE_SIZE + 200);
        primaryStage.setWidth(currGame.getN() * TILE_SIZE + 120);

        // Add margin between labelBox and boardPane
        BorderPane.setMargin(labelBox, new Insets(15, 0, 0, 0));
        BorderPane.setMargin(boardPane, new Insets(20));
        boardPane.setAlignment(Pos.CENTER);

        // Define boardPane: Add each tile from board "currGame" to the boardPane
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Tile tile = currGame.getHidden()[i][j];
                onClick(currGame, tile.stackPane, tile);
                tile.stackPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED,
                        CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                tile.stackPane.setPadding(new Insets(-0.5));
                boardPane.add(tile.stackPane, i, j);
            }
        }
    }

    // countdown(seconds): Applies countdown and displays remaining time in "minutes":"seconds" form.
    // Seconds are initialized by currGame.time. If currGame is finished (won or lost) or time is up
    // countdown stops.
    private void countdown(int[] seconds){
        if (timeline != null) {
            timeline.stop();
        }
        // Clock countdown (for remaining time)
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if(seconds[0] > 0 && !currGame.getHasLost() && !currGame.win()) {
                seconds[0]--;
                if (seconds[0] == 0) {
                    currGame.doLose();
                }
                if (seconds[0] % 60 > 9) timeLabel.setText(" \u23F0 0" + seconds[0] / 60 + ":" + seconds[0] % 60 + " ");
                else timeLabel.setText(" \u23F0 0" + seconds[0] / 60 + ":0" + seconds[0] % 60 + " ");
            }
        })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // createMinesFile(): Creates text file "mines.txt" which contains the positioning and supermine property
    // value for all currGame mines
    private void createMinesFile() {
        try {
            FileWriter fw = new FileWriter("medialab/mines.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (currGame.getHidden()[i][j] instanceof Mine) {
                        bw.write(i + "," + j + ",");
                        if (currGame.getHidden()[i][j] instanceof Supermine) bw.write("1");
                        else bw.write("0");
                        bw.newLine();
                    }
                }
            }

            bw.close();
            fw.close();

        } catch (IOException e) {
            popupMessage("Exception", "An error occurred while creating 'mines.txt'");
        }
    }


    // createScenarioFile(id, level, mines, time, supermineOn): Creates text file "medialab/SCENARIO-id.txt" which
    // contains game characteristics
    private void createScenarioFile(String id, String level, String mines, String time, String supermineOn) {
        try {
            FileWriter fw = new FileWriter("medialab/SCENARIO-" + id + ".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            if(!level.equals("")) { bw.write(level); }
            if(!mines.equals("")) { bw.newLine(); bw.write(mines); }
            if(!time.equals("")) { bw.newLine(); bw.write(time); }
            if(!supermineOn.equals("")) {  bw.newLine(); bw.write(supermineOn); }
            bw.close();
            fw.close();

        } catch (IOException e) {
            popupMessage("Exception", "An error occurred while creating 'SCENARIO-ID.txt'");
        }
    }

    // updateFlagLabel(): When a tile is right-clicked the number of flags is changed. This method updates the
    // label of flag counter
    private void updateFlagLabel() {
        flagLabel.setText("\uD83D\uDEA9 " + currGame.getCntFlags());
    }

    // onClick(currGame, stackPane, tile): Handles left and right click
    private void onClick(board currGame, StackPane stackPane, Tile tile) {
        stackPane.setOnMouseClicked(e -> {
            // If game is finished (won or lost) deactivate clicking
            if(currGame.win() || currGame.getHasLost()) return;
            // Left click: Increases number of rounds (a move is made) and reveals clicked tile
            if (e.getButton() == MouseButton.PRIMARY) {
                currGame.changeRound();
                tile.reveal();
            }
            // Right click: Flags or unflags clicked tile
            else if (e.getButton() == MouseButton.SECONDARY){
                if(tile.flagged) tile.unflag();
                else tile.flag();
                updateFlagLabel();
            }
            // Checks if game is won
            if(currGame.win()) {
                popupMessage("Victory", "Congratulations!");
            }
        });
    }

    // Exception messages Popup Window
    private void popupMessage(String title, String message){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle(title);

        // create a Label object to display the exception message
        Label label = new Label(message);


        // create a Button object to close the popup window
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> popup.close());

        // create a VBox layout to hold the Label and Button objects
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        // create a new Scene object with the VBox layout as its root
        Scene scene = new Scene(layout, 300, 100);

        // set the Scene of the popup window and show it
        popup.setScene(scene);
        popup.showAndWait();
    }
}


