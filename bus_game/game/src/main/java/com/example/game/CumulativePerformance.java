package com.example.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class CumulativePerformance extends BorderPane {
    private Header header;
    private Button homePageButton;
    private Button ourPerformanceButton;
    private Button competitorPerformanceButton;
    private Button decisionSummaryButton;
    private Button cumulativePerformanceButton;
    private LeftSideButtons lsb;
    private Scene cumulativePerformanceScene;
    private Inputs inputs;
    private Timeline timeline;
    TextField revenueTF;
    TextField profitTF;
    TextField enemyRevenueTF;
    TextField enemyProfitTF;
    List<TextField> revenueTextFields = new ArrayList<>();
    List<TextField> profitTextFields = new ArrayList<>();
    Integer[] enemyInputs;
    CumulativePerformance(Stage currStage, App currApp){
        currStage.setResizable(true);
        header = new Header();
        this.setTop(header);
        lsb = new LeftSideButtons();
        this.setLeft(lsb);
        cumulativePerformanceScene = new Scene(this, App.width, App.height);
        currStage.setScene(cumulativePerformanceScene);
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

        competitorPerformanceButton.setOnAction(e -> {
            timeline.stop();
            new CompetitorPerformance(currStage, currApp);
        });

        decisionSummaryButton.setOnAction(e -> {
            timeline.stop();
            new DecisionSummary(currStage, currApp);
        });

        timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            Double [] userCumulative = App.mdb.getUserCumulative(App.username);
            revenueTF.setText(String.valueOf(userCumulative[0])); 
            profitTF.setText(String.valueOf(userCumulative[1])); 

            ArrayList<Double[]> enemyScores = Inputs.getEnemyScores();
            for(int i = 0; i < revenueTextFields.size(); i++){
                revenueTextFields.get(i).setText(String.valueOf(enemyScores.get(i)[0]));
                profitTextFields.get(i).setText(String.valueOf(enemyScores.get(i)[1]));
            }

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
            competitorPerformanceButton = new Button("Competitor Performance");
            competitorPerformanceButton.setPrefHeight(40);
            cumulativePerformanceButton = new Button("Cumulative Performance");
            cumulativePerformanceButton.setPrefHeight(40);
            cumulativePerformanceButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
            this.setPrefSize(500, 60);
            this.setStyle("-fx-font-family: serif");
            this.getChildren().add(homePageButton);
            this.getChildren().add(ourPerformanceButton);
            this.getChildren().add(competitorPerformanceButton);
            this.getChildren().add(cumulativePerformanceButton);
            this.setAlignment(Pos.CENTER);
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
            Double [] userCumulative = App.mdb.getUserCumulative(App.username);
           
            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            Label revenueLabel = new Label();
            revenueLabel.setText("Revenue"); // 
            revenueLabel.setPrefSize(100, 20);// set size of Revenue label
            revenueLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            revenueLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(revenueLabel); 

            revenueTF = new TextField(String.valueOf(userCumulative[0])); 
            revenueTF.setPrefSize(380, 20); // set size of text field
            revenueTF.setStyle("-fx-font-family: serif"); // set background color
            // texfield                
            revenueTF.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
            revenueTF.setEditable(false);
            this.getChildren().add(revenueTF); 
            this.setAlignment(Pos.CENTER_LEFT);

            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            Label profitLabel = new Label();
            profitLabel.setText("Profit"); // 
            profitLabel.setPrefSize(100, 20);// set size of Profit label
            profitLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            profitLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(profitLabel); 

            profitTF = new TextField(String.valueOf(userCumulative[1])); 
            profitTF.setPrefSize(380, 20); 
            profitTF.setStyle("-fx-font-family: serif"); 
            // texfield                
            profitTF.setPadding(new Insets(10, 0, 10, 0)); 
            profitTF.setEditable(false);
            this.getChildren().add(profitTF); 
            this.setAlignment(Pos.CENTER_LEFT);


            ArrayList<Double[]> enemyScores = getEnemyScores();

            for(Double[] score: enemyScores){
                this.setPrefSize(500, 20);
                this.setStyle("-fx-font-family: serif");
                Label revenueEnemyLabel = new Label();
                revenueEnemyLabel.setText("Competitor's Revenue"); // 
                revenueEnemyLabel.setPrefSize(160, 20);// set size of Revenue label
                revenueEnemyLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
                revenueEnemyLabel.setAlignment(Pos.CENTER_LEFT);
                this.getChildren().add(revenueEnemyLabel); 

                enemyRevenueTF = new TextField(String.valueOf(score[0])); 
                enemyRevenueTF.setPrefSize(380, 20); // set size of text field
                enemyRevenueTF.setStyle("-fx-font-family: serif"); // set background color
                // texfield                
                enemyRevenueTF.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
                enemyRevenueTF.setEditable(false);
                this.getChildren().add(enemyRevenueTF); 
                this.setAlignment(Pos.CENTER_LEFT);
                revenueTextFields.add(enemyRevenueTF);

                this.setPrefSize(500, 20);
                this.setStyle("-fx-font-family: serif");
                Label profitEnemyLabel = new Label();
                profitEnemyLabel.setText("Competitors's Profit"); // 
                profitEnemyLabel.setPrefSize(160, 20);// set size of Profit label
                profitEnemyLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
                profitEnemyLabel.setAlignment(Pos.CENTER_LEFT);
                this.getChildren().add(profitEnemyLabel); 

                enemyProfitTF = new TextField(String.valueOf(score[1])); 
                enemyProfitTF.setPrefSize(380, 20); 
                enemyProfitTF.setStyle("-fx-font-family: serif"); 
                // texfield                
                enemyProfitTF.setPadding(new Insets(10, 0, 10, 0)); 
                enemyProfitTF.setEditable(false);
                this.getChildren().add(enemyProfitTF); 
                this.setAlignment(Pos.CENTER_LEFT);
                profitTextFields.add(enemyProfitTF);
            }

            


            }

            public static ArrayList<Double[]> getEnemyScores(){
                ArrayList<Double[]> profitsAndRevenues = new ArrayList<>();
                ArrayList<String> usernames = App.mdb.getEnemyUsernames(App.username, App.gameNumber);
                for(String str: usernames){
                    profitsAndRevenues.add(App.mdb.getUserCumulative(str));
                }
                return profitsAndRevenues;
            }


        }
    }
