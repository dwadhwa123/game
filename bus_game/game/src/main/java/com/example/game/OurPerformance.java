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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class OurPerformance extends BorderPane {
    private Header header;
    private Button homePageButton;
    private Button ourPerformanceButton;
    private Button competitorPerformanceButton;
    private Button decisionSummaryButton;
    private Button refreshButton;
    private LeftSideButtons lsb;
    private Scene ourPerformanceScene;
    private Inputs inputs;
    TextField revenueTF;
    TextField profitTF;
    Integer[] enemyInputs;
    OurPerformance(Stage currStage, App currApp){
        header = new Header();
        this.setTop(header);
        lsb = new LeftSideButtons();
        this.setLeft(lsb);
        ourPerformanceScene = new Scene(this, 1200, 800);
        currStage.setScene(ourPerformanceScene);
        inputs = new Inputs();
        this.setCenter(inputs);
        homePageButton.setOnAction(e -> {
            new CorporateLobby(currStage, currApp);
        });

        competitorPerformanceButton.setOnAction(e -> {
            new CompetitorPerformance(currStage, currApp);
        });

        decisionSummaryButton.setOnAction(e -> {
            new DecisionSummary(currStage, currApp);
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            Integer[] enemyInputs = App.mdb.recieveEnemyInputs(App.username, App.gameNumber);
            Double[] profitRevenueResults = new Double[2];
            if(enemyInputs == null){
                profitRevenueResults = ResultCalculations.twoPlayerCalculations(App.userBasicPrice, App.userQualityPrice, App.userAdvertisingPrice, 0, 0, 0);
            }
            else{
                profitRevenueResults = ResultCalculations.twoPlayerCalculations(App.userBasicPrice, App.userQualityPrice, App.userAdvertisingPrice, enemyInputs[0], enemyInputs[1], enemyInputs[2]);
            }
            revenueTF.setText(String.valueOf(profitRevenueResults[0])); 
            profitTF.setText(String.valueOf(profitRevenueResults[1])); 

        }));
        timeline.setCycleCount(Timeline.INDEFINITE); //Runs for an indefinite time
        timeline.play();
    }


    class Header extends HBox{
        Header(){
            homePageButton = new Button("Home Page");
            ourPerformanceButton = new Button("Our Performance");
            ourPerformanceButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
            homePageButton.setPrefHeight(40);
            ourPerformanceButton.setPrefHeight(40);
            competitorPerformanceButton = new Button("Competitor Performance");
            competitorPerformanceButton.setPrefHeight(40);
            this.setPrefSize(500, 60);
            this.setStyle("-fx-font-family: serif");
            this.getChildren().add(homePageButton);
            this.getChildren().add(ourPerformanceButton);
            this.getChildren().add(competitorPerformanceButton);
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
            Integer[] enemyInputs = App.mdb.recieveEnemyInputs(App.username, App.gameNumber);
            Double[] profitRevenueResults = new Double[2];
            if(enemyInputs == null){
                profitRevenueResults = ResultCalculations.twoPlayerCalculations(App.userBasicPrice, App.userQualityPrice, App.userAdvertisingPrice, 0, 0, 0);
            }
            else{
                profitRevenueResults = ResultCalculations.twoPlayerCalculations(App.userBasicPrice, App.userQualityPrice, App.userAdvertisingPrice, enemyInputs[0], enemyInputs[1], enemyInputs[2]);
            }


           
            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            Label revenueLabel = new Label();
            revenueLabel.setText("Revenue"); // 
            revenueLabel.setPrefSize(100, 20);// set size of Revenue label
            revenueLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            revenueLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(revenueLabel); 

            revenueTF = new TextField(String.valueOf(profitRevenueResults[0])); 
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

            profitTF = new TextField(String.valueOf(profitRevenueResults[1])); 
            profitTF.setPrefSize(380, 20); 
            profitTF.setStyle("-fx-font-family: serif"); 
            // texfield                
            profitTF.setPadding(new Insets(10, 0, 10, 0)); 
            profitTF.setEditable(false);
            this.getChildren().add(profitTF); 
            this.setAlignment(Pos.CENTER_LEFT);
            }


        }
    }
