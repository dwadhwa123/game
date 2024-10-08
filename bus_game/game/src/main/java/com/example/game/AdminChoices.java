package com.example.game;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.*;
import java.time.LocalDateTime;

public class AdminChoices extends BorderPane{
    private Scene adminChoicesScene;
    Header3 header;
    TextField warDecision;
    TextField newEntrantDecision;
    TextField customerIncreaseDecision;
    TextField numPlayersDecision;
    TextField decisionLengthDecision;
    TextField warCustomerDecreaseDecision;
    TextField warLengthDecision;
    TextField newEntrantDecreaseDecision;
    private Button saveButton;
    private Button startGameButton;
    private Button endGameButton;
    private Label decisionSaved;
    ArrayList<Double> doubleInputs;
    Choices choices;
    AdminChoices(Stage currStage, App currApp, boolean started){
        currStage.setResizable(true);
        header = new Header3();
        this.setTop(header);
        doubleInputs = App.mdbAdmin.getAdminInputs();
        choices = new Choices(doubleInputs, started);
        choices.setPadding(new Insets(0, 0, 100,40));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(choices);
        scrollPane.setFitToWidth(true); 
        this.setCenter(scrollPane);

        this.setLeft(choices);
        adminChoicesScene = new Scene(this, App.width, App.height);
        currStage.setScene(adminChoicesScene);


        //saves the admin choices to database
        saveButton.setOnAction(e -> {
            double warChoice = Double.parseDouble(warDecision.getText());
            double newEntrantChoice = Double.parseDouble(newEntrantDecision.getText());
            double customerIncreaseChoice = Double.parseDouble(customerIncreaseDecision.getText());
            double numPlayersChoice = Double.parseDouble(numPlayersDecision.getText());
            double decisionLengthChoice = Double.parseDouble(decisionLengthDecision.getText());
            double warCustomerDecreaseChoice = Double.parseDouble(warCustomerDecreaseDecision.getText());
            double warLengthChoice = Double.parseDouble(warLengthDecision.getText());
            double newEntrantDecreaseChoice = Double.parseDouble(newEntrantDecreaseDecision.getText());
            ArrayList<Double> choices = new ArrayList<>();
            choices.add(warChoice); choices.add(newEntrantChoice); choices.add(customerIncreaseChoice);
            choices.add(numPlayersChoice); choices.add(decisionLengthChoice); choices.add(warCustomerDecreaseChoice);
            choices.add(warLengthChoice); choices.add(newEntrantDecreaseChoice); 
            App.mdbAdmin.saveAdminDecisions(choices);
            Platform.runLater(() -> {
                decisionSaved.setText("Choices Saved");
                decisionSaved.setVisible(true);
            });
        });   

        //changes the started value to true and saves the time the button was pressed
        startGameButton.setOnAction( e -> {
            LocalDateTime currentDateTime = LocalDateTime.now();
            App.mdbAdmin.startGame(currentDateTime);
            startGameButton.setStyle("-fx-background-color: orange; -fx-text-fill: black;");
            endGameButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            Platform.runLater(() -> {
                decisionSaved.setText("Game Started");
                decisionSaved.setVisible(true);
            });
        });

        //changes the started value to false
        endGameButton.setOnAction( e -> {
            App.mdbAdmin.endGame();
            endGameButton.setStyle("-fx-background-color: orange; -fx-text-fill: black;");
            startGameButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            Platform.runLater(() -> {
                decisionSaved.setText("Game Ended");
                decisionSaved.setVisible(true);
            });
            App.mdb.setAllToZero();
        });
    }
    class Header3 extends HBox{
        Header3(){
            Label header = new Label("Administrative Choices");
            header.setPrefSize(300, 50);
            header.setStyle("-fx-font-family: serif");
            header.setFont(new Font(20));
            header.setAlignment(Pos.CENTER);
            this.setPrefSize(300, 100);
            this.getChildren().add(header);
            this.setAlignment(Pos.CENTER);
        }

    }

    class Choices extends VBox{
        Label warDecisionLabel;
        Label newEntrantLabel;
        Label customerIncreaseLabel;
        Label numPlayersLabel;
        Label decisionLengthLabel;
        Label warCustomerLabel;
        Label warLengthLabel;
        Label newEntrantDecreaseLabel;
        Choices(ArrayList<Double> doubleInputs, boolean started){
            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            warDecisionLabel = new Label();

            warDecisionLabel.setText("War Disruption Time (min)");
            warDecisionLabel.setPrefSize(170, 20);
            warDecisionLabel.setPadding(new Insets(10, 0, 10, 0));
            warDecisionLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(warDecisionLabel);
            String input = String.valueOf(doubleInputs.get(0));
            warDecision = new TextField(input);
            warDecision.setPrefSize(380, 20);
            warDecision.setStyle("-fx-font-family: serif");

            warDecision.setPadding(new Insets(10, 0, 10, 0));
            warDecision.setEditable(true);
            this.getChildren().add(warDecision);
            this.setAlignment(Pos.CENTER_LEFT);

            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            newEntrantLabel = new Label();

            newEntrantLabel.setText("New Entrant Disruption Time (min)");
            newEntrantLabel.setPrefSize(200, 20);
            newEntrantLabel.setPadding(new Insets(10, 0, 10, 0));
            newEntrantLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(newEntrantLabel);
            String input2 = String.valueOf(doubleInputs.get(1));
            newEntrantDecision = new TextField(input2);
            newEntrantDecision.setPrefSize(380, 20);
            newEntrantDecision.setStyle("-fx-font-family: serif");

            newEntrantDecision.setPadding(new Insets(10, 0, 10, 0));
            newEntrantDecision.setEditable(true);
            this.getChildren().add(newEntrantDecision);
            this.setAlignment(Pos.CENTER_LEFT);

            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            customerIncreaseLabel = new Label();

            customerIncreaseLabel.setText("Time for 5% customer increase (min)");
            customerIncreaseLabel.setPrefSize(220, 20);
            customerIncreaseLabel.setPadding(new Insets(10, 0, 10, 0));
            customerIncreaseLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(customerIncreaseLabel);
            String input3 = String.valueOf(doubleInputs.get(2));
            customerIncreaseDecision = new TextField(input3);
            customerIncreaseDecision.setPrefSize(380, 20);
            customerIncreaseDecision.setStyle("-fx-font-family: serif");

            customerIncreaseDecision.setPadding(new Insets(10, 0, 10, 0));
            customerIncreaseDecision.setEditable(true);
            this.getChildren().add(customerIncreaseDecision);
            this.setAlignment(Pos.CENTER_LEFT);


            numPlayersLabel = new Label();
            numPlayersLabel.setText("Number of players");
            numPlayersLabel.setPrefSize(150, 20);
            numPlayersLabel.setPadding(new Insets(10, 0, 10, 0));
            numPlayersLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(numPlayersLabel);
            String input4 = String.valueOf(doubleInputs.get(3));
            numPlayersDecision = new TextField(input4);
            numPlayersDecision.setPrefSize(380, 20);
            numPlayersDecision.setStyle("-fx-font-family: serif");

            numPlayersDecision.setPadding(new Insets(10, 0, 10, 0));
            numPlayersDecision.setEditable(true);
            this.getChildren().add(numPlayersDecision);
            this.setAlignment(Pos.CENTER_LEFT);

            decisionLengthLabel = new Label();
            decisionLengthLabel.setText("Time for Decision Length (min)");
            decisionLengthLabel.setPrefSize(200, 20);
            decisionLengthLabel.setPadding(new Insets(10, 0, 10, 0));
            decisionLengthLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(decisionLengthLabel);
            String input5 = String.valueOf(doubleInputs.get(4));
            decisionLengthDecision = new TextField(input5);
            decisionLengthDecision.setPrefSize(380, 20);
            decisionLengthDecision.setStyle("-fx-font-family: serif");

            decisionLengthDecision.setPadding(new Insets(10, 0, 10, 0));
            decisionLengthDecision.setEditable(true);
            this.getChildren().add(decisionLengthDecision);
            this.setAlignment(Pos.CENTER_LEFT);

            warCustomerLabel = new Label();
            warCustomerLabel.setText("% Decrease in customers during war");
            warCustomerLabel.setPrefSize(220, 20);
            warCustomerLabel.setPadding(new Insets(10, 0, 10, 0));
            warCustomerLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(warCustomerLabel);
            String input6 = String.valueOf(doubleInputs.get(5));
            warCustomerDecreaseDecision = new TextField(input6);
            warCustomerDecreaseDecision.setPrefSize(380, 20);
            warCustomerDecreaseDecision.setStyle("-fx-font-family: serif");

            warCustomerDecreaseDecision.setPadding(new Insets(10, 0, 10, 0));
            warCustomerDecreaseDecision.setEditable(true);
            this.getChildren().add(warCustomerDecreaseDecision);
            this.setAlignment(Pos.CENTER_LEFT);

            warLengthLabel = new Label();
            warLengthLabel.setText("Length of war");
            warLengthLabel.setPrefSize(220, 20);
            warLengthLabel.setPadding(new Insets(10, 0, 10, 0));
            warLengthLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(warLengthLabel);
            String input7 = String.valueOf(doubleInputs.get(6));
            warLengthDecision = new TextField(input7);
            warLengthDecision.setPrefSize(380, 20);
            warLengthDecision.setStyle("-fx-font-family: serif");

            warLengthDecision.setPadding(new Insets(10, 0, 10, 0));
            warLengthDecision.setEditable(true);
            this.getChildren().add(warLengthDecision);


            newEntrantDecreaseLabel = new Label();
            newEntrantDecreaseLabel.setText("% of market share lost per new entrant");
            newEntrantDecreaseLabel.setPrefSize(250, 20);
            newEntrantDecreaseLabel.setPadding(new Insets(10, 0, 10, 0));
            newEntrantDecreaseLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(newEntrantDecreaseLabel);
            String input8 = String.valueOf(doubleInputs.get(7));
            newEntrantDecreaseDecision = new TextField(input8);
            newEntrantDecreaseDecision.setPrefSize(380, 20);
            newEntrantDecreaseDecision.setStyle("-fx-font-family: serif");

            newEntrantDecreaseDecision.setPadding(new Insets(10, 0, 10, 0));
            newEntrantDecreaseDecision.setEditable(true);
            this.getChildren().add(newEntrantDecreaseDecision);
            this.setAlignment(Pos.CENTER_LEFT);

            saveButton = new Button("Save");
            saveButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            this.getChildren().add(saveButton);

            startGameButton = new Button("Start Game");
            endGameButton = new Button("End Game");

            //sets the button that shows which is the current state to orange 
            if(started){
                startGameButton.setStyle("-fx-background-color: orange; -fx-text-fill: black;");
                endGameButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            }
            else{
                endGameButton.setStyle("-fx-background-color: orange; -fx-text-fill: black;");
                startGameButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            }

            this.getChildren().add(startGameButton);
            this.getChildren().add(endGameButton);

            decisionSaved = new Label();
            decisionSaved.setText("Choices Saved");
            decisionSaved.setPrefSize(200, 20);
            decisionSaved.setPadding(new Insets(10, 0, 10, 0));
            decisionSaved.setAlignment(Pos.CENTER_LEFT);
            decisionSaved.setVisible(false);
            this.getChildren().add(decisionSaved);

            this.setPadding(getInsets());

        }


    }
}
