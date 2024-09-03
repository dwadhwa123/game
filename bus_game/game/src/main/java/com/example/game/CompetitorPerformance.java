package com.example.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class CompetitorPerformance extends BorderPane {
    private Header header;
    private Button homePageButton;
    private Button ourPerformanceButton;
    private Button competitorPerformanceButton;
    private Button decisionSummaryButton;
    private Button cumulativePerformanceButton;
    private Button competitorDecisionButton;
    private LeftSideButtons lsb;
    private Scene ourPerformanceScene;
    private Inputs inputs;
    private Timeline timeline; 
    private Label timer;
    TextField revenueTF;
    TextField profitTF;
    List<TextField> revenueTextFields = new ArrayList<>();
    List<TextField> profitTextFields = new ArrayList<>();
    CompetitorPerformance(Stage currStage, App currApp){
        currStage.setResizable(true);
        header = new Header();
        this.setTop(header);
        lsb = new LeftSideButtons();
        this.setLeft(lsb);
        ourPerformanceScene = new Scene(this, App.width, App.height);
        currStage.setScene(ourPerformanceScene);
        inputs = new Inputs();
        this.setCenter(inputs);
        homePageButton.setOnAction(e -> {
            timeline.stop();
            new CorporateLobby(currStage, currApp);
        });

        ourPerformanceButton.setOnAction(e -> {
            timeline.stop();
            new OurPerformance(currStage, currApp);
        });

        decisionSummaryButton.setOnAction(e -> {
            timeline.stop();
            new DecisionSummary(currStage, currApp);
        });

        competitorDecisionButton.setOnAction(e -> {
            timeline.stop();
            new CompetitorDecisions(currStage, currApp);
        });
        
        cumulativePerformanceButton.setOnAction(e -> {
            timeline.stop();
            new CumulativePerformance(currStage, currApp);
        });

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if(App.timer >= 60){
                timer.setText(String.valueOf(App.timer/60) + " min");
            }
            else{
                timer.setText(String.valueOf(App.timer)); // show seconds if under a minute
            }

            //only checks for changes when the competitor makes a change
            if(App.changeDetected){
                ArrayList<Double[]> enemyScores = Inputs.getEnemyScores();
                Platform.runLater(() -> {
                    for(int i = 0; i < revenueTextFields.size(); i++){
                        revenueTextFields.get(i).setText(String.valueOf(enemyScores.get(i)[0]));
                        profitTextFields.get(i).setText(String.valueOf(enemyScores.get(i)[1]));
                    }
                });
            }
            App.changeDetected = false;

        }));
        timeline.setCycleCount(Timeline.INDEFINITE); //Runs for an indefinite time
        timeline.play();
    }


    class Header extends HBox{
        Header(){
            homePageButton = new Button("Home Page");
            ourPerformanceButton = new Button("Our Performance");
            homePageButton.setPrefHeight(40);
            ourPerformanceButton.setPrefHeight(40);
            timer = new Label();
            timer.setFont(new Font(20));
            if(App.timer >= 60){
                timer.setText(String.valueOf(App.timer/60) + " min");
            }
            else{
                timer.setText(String.valueOf(App.timer)); // show seconds if under a minute
            }
            timer.setPrefSize(140, 20); // set size of timer label
            timer.setAlignment(Pos.TOP_LEFT);
            competitorPerformanceButton = new Button("Competitor Performance");
            competitorPerformanceButton.setPrefHeight(40);
            cumulativePerformanceButton = new Button("Cumulative Performance");
            cumulativePerformanceButton.setPrefHeight(40);
            competitorDecisionButton = new Button("Competitor Decisions");
            competitorDecisionButton.setPrefHeight(40);
            competitorPerformanceButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
            this.setPrefSize(500, 60);
            this.setStyle("-fx-font-family: serif");
            this.getChildren().add(timer);
            this.getChildren().add(homePageButton);
            this.getChildren().add(ourPerformanceButton);
            this.getChildren().add(competitorPerformanceButton);
            this.getChildren().add(competitorDecisionButton);
            this.getChildren().add(cumulativePerformanceButton);
        }

    }

    class LeftSideButtons extends VBox{
        LeftSideButtons(){
            decisionSummaryButton = new Button("Decision Summary");
            this.getChildren().add(decisionSummaryButton);
            this.setPrefSize(500, 60);
            this.setStyle("-fx-font-family: serif");
            this.setAlignment(Pos.CENTER_LEFT);
        }

    }

    class Inputs extends VBox{
        Inputs(){

            ArrayList<Double[]> enemyScores = getEnemyScores();
            
            Platform.runLater(() -> {
            int index = 0;
            for(Double[] score: enemyScores){
                this.setPrefSize(500, 20);
                this.setStyle("-fx-font-family: serif");
                Label revenueLabel = new Label();
                revenueLabel.setText(App.enemyUsernames.get(index) + "'s Revenue"); // 
                revenueLabel.setPrefSize(250, 20);// set size of Revenue label
                revenueLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
                revenueLabel.setAlignment(Pos.CENTER_LEFT);
                this.getChildren().add(revenueLabel); // add label to VBox

                revenueTF = new TextField(String.valueOf(score[0])); 
                revenueTF.setPrefSize(380, 20); // set size of text field
                revenueTF.setStyle("-fx-font-family: serif"); // set background color
                // texfield                
                revenueTF.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
                revenueTF.setEditable(false);
                this.getChildren().add(revenueTF); 
                this.setAlignment(Pos.CENTER_LEFT);
                revenueTextFields.add(revenueTF);

                this.setPrefSize(500, 20);
                this.setStyle("-fx-font-family: serif");
                Label profitLabel = new Label();
                profitLabel.setText(App.enemyUsernames.get(index) + "'s Profit"); // 
                profitLabel.setPrefSize(250, 20);
                profitLabel.setPadding(new Insets(10, 0, 10, 0)); 
                profitLabel.setAlignment(Pos.CENTER_LEFT);
                this.getChildren().add(profitLabel); 

                profitTF = new TextField(String.valueOf(score[1])); 
                profitTF.setPrefSize(380, 20);
                profitTF.setStyle("-fx-font-family: serif"); 
                // texfield                
                profitTF.setPadding(new Insets(10, 0, 10, 0)); 
                profitTF.setEditable(false);
                this.getChildren().add(profitTF); 
                this.setAlignment(Pos.CENTER_LEFT);
                profitTextFields.add(profitTF);

                index++;
            }
            });
            App.changeDetected = false;
        }

        //Does the multi player calculations from each competitor's perspective to calculate all the revenues and profits
        public static ArrayList<Double[]> getEnemyScores(){
            ArrayList<Double[]> profitsAndRevenues = new ArrayList<>();
            ArrayList<String> usernames = App.mdb.getEnemyUsernames(App.username, App.gameNumber);
            for(String str: usernames){
                ArrayList<Integer[]> enemyInputs = App.mdb.recieveMultipleEnemyInputs(str, App.gameNumber);
                Integer[] inputs = App.mdb.recieveUserInputs(str);
                Double[] profitRevenueResults = ResultCalculations.multiPlayerCalculations(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], enemyInputs);
                profitsAndRevenues.add(profitRevenueResults);
            }
            return profitsAndRevenues;
        }


        }
    }
