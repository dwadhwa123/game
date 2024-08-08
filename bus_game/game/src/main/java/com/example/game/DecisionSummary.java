package com.example.game;

import java.util.ArrayList;

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
import java.time.LocalDateTime;

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
    TextField advertisingSpendInput;
    DecisionSummary(Stage currStage, App currApp){
        currStage.setResizable(true);
        header = new Header();
        this.setTop(header);
        lsb = new LeftSideButtons();
        this.setLeft(lsb);
        decisionSummaryScene = new Scene(this, App.width, App.height);
        intInputs = new ArrayList<>();
        intInputs.add(App.userBasicPrice);
        intInputs.add(App.userQualityPrice);
        intInputs.add(App.userAdvertisingSpend);
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
                String advertisingInput = advertisingSpendInput.getText();
                App.userAdvertisingSpend = Integer.parseInt(advertisingInput);
                App.mdb.saveDecisions(App.username, App.userBasicPrice, App.userQualityPrice, App.userAdvertisingSpend);
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
        Label advertisingSpend;
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
            advertisingSpend = new Label();

            advertisingSpend.setText("Advertising Spend" + ": ");
            advertisingSpend.setPrefSize(140, 20);
            advertisingSpend.setPadding(new Insets(10, 0, 10, 0));
            advertisingSpend.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(advertisingSpend);
            String input3 = String.valueOf(integerInputs.get(2));
            advertisingSpendInput = new TextField(input3);
            advertisingSpendInput.setPrefSize(380, 20);
            advertisingSpendInput.setStyle("-fx-font-family: serif");

            advertisingSpendInput.setPadding(new Insets(10, 0, 10, 0));
            advertisingSpendInput.setEditable(true);
            this.getChildren().add(advertisingSpendInput);
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

