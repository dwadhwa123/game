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

public class CompetitorDecisions extends BorderPane {
    private Header header;
    private Button homePageButton;
    private Button ourPerformanceButton;
    private Button competitorPerformanceButton;
    private Button decisionSummaryButton;
    private Button cumulativePerformanceButton;
    private Button competitorDecisionButton;
    private LeftSideButtons lsb;
    private Scene previousChoicesScene;
    private AllInputs allInputs;
    private Timeline timeline;
    List<VBox> choicesPlusCustomers = new ArrayList<>();
    List<TextField> basicPriceTextFields = new ArrayList<>();
    List<TextField> qualityPriceTextFields = new ArrayList<>();
    List<TextField> advertisingTextFields = new ArrayList<>();
    CompetitorDecisions(Stage currStage, App currApp){
        currStage.setResizable(true);
        header = new Header();
        this.setTop(header);
        lsb = new LeftSideButtons();
        this.setLeft(lsb);
        for(Integer[] choices: App.lastEnemyDecisions){
            choicesPlusCustomers.add(new Inputs(choices));
        }
        allInputs = new AllInputs(choicesPlusCustomers);
        this.setCenter(allInputs);

        previousChoicesScene = new Scene(this, App.width, App.height);
        currStage.setScene(previousChoicesScene);
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
        
        competitorPerformanceButton.setOnAction(e -> {
            timeline.stop();
            new CompetitorPerformance(currStage, currApp);
        });

        cumulativePerformanceButton.setOnAction(e -> {
            timeline.stop();
            new CumulativePerformance(currStage, currApp);
        });

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if(!App.isFirstDecisionPeriod && App.hasAccumulated){
                for(int i = 0; i < basicPriceTextFields.size(); i++){
                    basicPriceTextFields.get(i).setText(String.valueOf(App.lastEnemyDecisions.get(i)[0]));
                    qualityPriceTextFields.get(i).setText(String.valueOf(App.lastEnemyDecisions.get(i)[1]));
                    advertisingTextFields.get(i).setText(String.valueOf(App.lastEnemyDecisions.get(i)[2]));
                }
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
            competitorDecisionButton = new Button("Competitor Decisions");
            competitorDecisionButton.setPrefHeight(40);
            competitorDecisionButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
            cumulativePerformanceButton = new Button("Cumulative Performance");
            cumulativePerformanceButton.setPrefHeight(40);
            this.setPrefSize(500, 60);
            this.setStyle("-fx-font-family: serif");
            this.getChildren().add(homePageButton);
            this.getChildren().add(ourPerformanceButton);
            this.getChildren().add(competitorPerformanceButton);
            this.getChildren().add(competitorDecisionButton);
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
        Inputs(Integer[] enemyChoices){

            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            Label basicPriceLabel = new Label("Basic Price");
            basicPriceLabel.setPrefSize(100, 20);// set size of label
            basicPriceLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            basicPriceLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(basicPriceLabel);

            TextField basicPriceTextField = new TextField(String.valueOf(enemyChoices[0]));
            basicPriceTextField.setPrefSize(380, 20); // set size of text field
            basicPriceTextField.setStyle("-fx-font-family: serif"); // set background color
            // texfield                
            basicPriceTextField.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
            basicPriceTextField.setEditable(false);
            this.getChildren().add(basicPriceTextField); 
            this.setAlignment(Pos.CENTER_LEFT);
            basicPriceTextFields.add(basicPriceTextField);
               

            Label qualityPriceLabel = new Label("Quality Price");
            qualityPriceLabel.setPrefSize(100, 20);// set size of label
            qualityPriceLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            qualityPriceLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(qualityPriceLabel);

            TextField qualityPriceTextField = new TextField(String.valueOf(enemyChoices[1]));
            qualityPriceTextField.setPrefSize(150, 20); // set size of text field
            qualityPriceTextField.setStyle("-fx-font-family: serif"); // set background color
            // texfield                
            qualityPriceTextField.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
            qualityPriceTextField.setEditable(false);
            this.getChildren().add(qualityPriceTextField); 
            this.setAlignment(Pos.CENTER_LEFT);
            qualityPriceTextFields.add(qualityPriceTextField);

            Label advertisingSpendLabel = new Label("Advertising Spend");
            advertisingSpendLabel.setPrefSize(100, 20);// set size of label
            advertisingSpendLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            advertisingSpendLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(advertisingSpendLabel);

            TextField advertisingSpendTextField = new TextField(String.valueOf(enemyChoices[2]));
            advertisingSpendTextField.setPrefSize(150, 20); // set size of text field
            advertisingSpendTextField.setStyle("-fx-font-family: serif"); // set background color
            // texfield                
            advertisingSpendTextField.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
            advertisingSpendTextField.setEditable(false);
            this.getChildren().add(advertisingSpendTextField); 
            this.setAlignment(Pos.CENTER_LEFT);
            advertisingTextFields.add(advertisingSpendTextField);


            }
        }

        // public static ArrayList<Double[]> getEnemyScores(){
        //     ArrayList<Double[]> profitsAndRevenues = new ArrayList<>();
        //     ArrayList<String> usernames = App.mdb.getEnemyUsernames(App.username, App.gameNumber);
        //     for(String str: usernames){
        //         ArrayList<Integer[]> enemyInputs = App.mdb.recieveMultipleEnemyInputs(str, App.gameNumber);
        //         Integer[] inputs = App.mdb.getInput(str);
        //         Double[] profitRevenueResults = ResultCalculations.multiPlayerCalculations(inputs[0], inputs[1], inputs[2], enemyInputs);
        //         profitsAndRevenues.add(profitRevenueResults);
        //     }
        //     return profitsAndRevenues;
        // }


        class AllInputs extends HBox{
            AllInputs(List<VBox> singleInputs){
                this.setStyle("-fx-font-family: serif");
                for(VBox vbox: singleInputs){
                    this.getChildren().add(vbox);
                    vbox.setAlignment(Pos.CENTER);
                }
                this.setAlignment(Pos.CENTER);
            }
        }


        }
    