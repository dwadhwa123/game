package com.example.game;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.FindIterable;

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

public class DecisionSummary extends BorderPane {
    private Header header;
    private Button homePageButton;
    private Button ourPerformanceButton;
    private Button competitorPerformanceButton;
    private Button decisionSummaryButton;
    private Button saveButton;
    private Button cumulativePerformanceButton;
    private LeftSideButtons lsb;
    private Scene decisionSummaryScene;
    private Inputs inputs;
    private ArrayList<Integer> intInputs;
    TextField basicPriceInput;
    TextField qualityPriceInput;
    TextField advertisingInput;
    DecisionSummary(Stage currStage, App currApp){
        currStage.setResizable(true);
        header = new Header();
        this.setTop(header);
        lsb = new LeftSideButtons();
        this.setLeft(lsb);
        decisionSummaryScene = new Scene(this, 800, 400);
        intInputs = new ArrayList<>();
        intInputs.add(App.userBasicPrice);
        intInputs.add(App.userQualityPrice);
        intInputs.add(App.userAdvertisingPrice);
        inputs = new Inputs(intInputs);
        this.setCenter(inputs);
        currStage.setScene(decisionSummaryScene);

        ourPerformanceButton.setOnAction(e -> {
            new OurPerformance(currStage, currApp);
        });

        competitorPerformanceButton.setOnAction(e -> {
            new CompetitorPerformance(currStage, currApp);
        });

        homePageButton.setOnAction(e -> {
            new CorporateLobby(currStage, currApp);
        });
        cumulativePerformanceButton.setOnAction(e -> {
            new CumulativePerformance(currStage, currApp);
        });
        saveButton.setOnAction(e -> {
            String basicInput = basicPriceInput.getText();
            App.userBasicPrice = Integer.parseInt(basicInput);
            String qualityInput = qualityPriceInput.getText();
            App.userQualityPrice = Integer.parseInt(qualityInput);
            String advertisingPriceInput = advertisingInput.getText();
            App.userAdvertisingPrice = Integer.parseInt(advertisingPriceInput);
            App.mdb.saveDecisions(App.username, App.userBasicPrice, App.userQualityPrice, App.userAdvertisingPrice);

            Integer[] enemyInputs = App.mdb.recieveEnemyInputs(App.username, App.gameNumber);
            Double[] profitRevenueResults = new Double[2];

            if(enemyInputs[0] == 0 && enemyInputs[1] == 0 && enemyInputs[2] == 0){
                profitRevenueResults[0] = 0.0;
                profitRevenueResults[1] = 0.0;
            }
            else{
                profitRevenueResults = ResultCalculations.twoPlayerCalculations(App.userBasicPrice, App.userQualityPrice, App.userAdvertisingPrice, enemyInputs[0], enemyInputs[1], enemyInputs[2]);
            }
            Double[] userValues = App.mdb.getUserCumulative(App.username);
            App.mdb.saveCumulative(App.username, userValues[0] +  profitRevenueResults[0], userValues[1] + profitRevenueResults[1]);

            String enemyUsername = App.mdb.getEnemyUsername(App.username, App.gameNumber);
            Double[] enemyValues = App.mdb.getUserCumulative(enemyUsername);

            Double[] profitRevenueResultsEnemy = new Double[2];
            if(enemyInputs[0] == 0 && enemyInputs[1] == 0 && enemyInputs[2] == 0){
                profitRevenueResultsEnemy[0] = 0.0;
                profitRevenueResultsEnemy[1] = 0.0;
            }
            else{
                profitRevenueResultsEnemy = ResultCalculations.twoPlayerCalculations(enemyInputs[0], enemyInputs[1], enemyInputs[2], App.userBasicPrice, App.userQualityPrice, App.userAdvertisingPrice);
            }
            App.mdb.saveCumulative(enemyUsername, enemyValues[0] +  profitRevenueResultsEnemy[0], enemyValues[1] + profitRevenueResultsEnemy[1]);


        });
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
            decisionSummaryButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
            this.getChildren().add(decisionSummaryButton);
            this.setPrefSize(500, 60);
            this.setStyle("-fx-font-family: serif");
            this.setAlignment(Pos.CENTER_LEFT);
        }

    }

    class Inputs extends VBox{
        Label basicPrice;
        Label qualityPrice;
        Label advertisingPrice;
        Inputs(ArrayList<Integer> integerInputs){
            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            basicPrice = new Label();

            basicPrice.setText("Basic Price" + ": ");
            basicPrice.setPrefSize(100, 20);
            basicPrice.setPadding(new Insets(10, 0, 10, 0));
            basicPrice.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(basicPrice);
            String input = String.valueOf(integerInputs.get(0));
            basicPriceInput = new TextField(input);
            basicPriceInput.setPrefSize(380, 20);
            basicPriceInput.setStyle("-fx-font-family: serif");

            basicPriceInput.setPadding(new Insets(10, 0, 10, 0));
            basicPriceInput.setEditable(true);
            this.getChildren().add(basicPriceInput);
            this.setAlignment(Pos.CENTER_LEFT);

            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            qualityPrice = new Label();

            qualityPrice.setText("Quality Price" + ": ");
            qualityPrice.setPrefSize(100, 20);
            qualityPrice.setPadding(new Insets(10, 0, 10, 0));
            qualityPrice.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(qualityPrice);
            String input2 = String.valueOf(integerInputs.get(1));
            qualityPriceInput = new TextField(input2);
            qualityPriceInput.setPrefSize(380, 20);
            qualityPriceInput.setStyle("-fx-font-family: serif");

            qualityPriceInput.setPadding(new Insets(10, 0, 10, 0));
            qualityPriceInput.setEditable(true);
            this.getChildren().add(qualityPriceInput);
            this.setAlignment(Pos.CENTER_LEFT);

            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            advertisingPrice = new Label();

            advertisingPrice.setText("Advertising Price" + ": ");
            advertisingPrice.setPrefSize(100, 20);
            advertisingPrice.setPadding(new Insets(10, 0, 10, 0));
            advertisingPrice.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(advertisingPrice);
            String input3 = String.valueOf(integerInputs.get(2));
            advertisingInput = new TextField(input3);
            advertisingInput.setPrefSize(380, 20);
            advertisingInput.setStyle("-fx-font-family: serif");

            advertisingInput.setPadding(new Insets(10, 0, 10, 0));
            advertisingInput.setEditable(true);
            this.getChildren().add(advertisingInput);
            this.setAlignment(Pos.CENTER_LEFT);


            saveButton = new Button("Save");
            saveButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            this.getChildren().add(saveButton);
            this.setPrefSize(500, 60);
            this.setStyle("-fx-font-family: serif");
            this.setAlignment(Pos.CENTER_LEFT);

        }


    }
}

