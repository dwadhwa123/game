package com.example.game;

import java.util.ArrayList;

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

public class DecisionSummary extends BorderPane {
    private Header header;
    Label timer;
    private Button homePageButton;
    private Button ourPerformanceButton;
    private Button competitorPerformanceButton;
    private Button decisionSummaryButton;
    private Button saveButton;
    private Button cumulativePerformanceButton;
    private Button competitorDecisionButton;
    private LeftSideButtons lsb;
    private Scene decisionSummaryScene;
    private Inputs inputs;
    private ArrayList<Integer> intInputs;
    private Timeline timeline;
    private Label decisionSaved;
    TextField basicPriceInput;
    TextField qualityPriceInput;
    TextField advertisingSpendInput;
    TextField basicDroneRobotsInput;
    TextField qualityDroneRobotsInput;
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
        intInputs.add(App.userBasicDroneRobots);
        intInputs.add(App.userQualityDroneRobots);
        inputs = new Inputs(intInputs);
        this.setCenter(inputs);
        currStage.setScene(decisionSummaryScene);

        ourPerformanceButton.setOnAction(e -> {
            timeline.stop();
            new OurPerformance(currStage, currApp);
        });

        competitorPerformanceButton.setOnAction(e -> {
            timeline.stop();
            new CompetitorPerformance(currStage, currApp);
        });

        homePageButton.setOnAction(e -> {
            timeline.stop();
            new CorporateLobby(currStage, currApp);
        });

        competitorDecisionButton.setOnAction(e -> {
            timeline.stop();
            new CompetitorDecisions(currStage, currApp);
        });
        
        cumulativePerformanceButton.setOnAction(e -> {
            timeline.stop();
            new CumulativePerformance(currStage, currApp);
        });
        saveButton.setOnAction(e -> {   
            String basicInput = basicPriceInput.getText();
            App.userBasicPrice = Integer.parseInt(basicInput);
            String qualityInput = qualityPriceInput.getText();
            App.userQualityPrice = Integer.parseInt(qualityInput);
            String advertisingInput = advertisingSpendInput.getText();
            App.userAdvertisingSpend = Integer.parseInt(advertisingInput);
            String basicDroneRobots = basicDroneRobotsInput.getText();
            App.userBasicDroneRobots = Integer.parseInt(basicDroneRobots);
            String qualityDroneRobots = qualityDroneRobotsInput.getText();
            App.userQualityDroneRobots = Integer.parseInt(qualityDroneRobots);
            App.mdb.saveDecisions(App.username, App.userBasicPrice, App.userQualityPrice, App.userAdvertisingSpend, App.userBasicDroneRobots, App.userQualityDroneRobots);
            Platform.runLater(() -> {
                decisionSaved.setText("Decisions Saved");
                decisionSaved.setVisible(true);
            });
        });

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if(App.timer >= 60){
                timer.setText(String.valueOf(App.timer/60) + " min");
            }
            else{
                timer.setText(String.valueOf(App.timer)); // show seconds if under a minute
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE); 
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
        Label basicDroneRobots;
        Label qualityDroneRobots;
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

            basicDroneRobots = new Label();

            basicDroneRobots.setText("Basic Drones" + ": ");
            basicDroneRobots.setPrefSize(200, 20);
            basicDroneRobots.setPadding(new Insets(10, 0, 10, 0));
            basicDroneRobots.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(basicDroneRobots);
            String input4 = String.valueOf(integerInputs.get(3));
            basicDroneRobotsInput = new TextField(input4);
            basicDroneRobotsInput.setPrefSize(380, 20);
            basicDroneRobotsInput.setStyle("-fx-font-family: serif");

            basicDroneRobotsInput.setPadding(new Insets(10, 0, 10, 0));
            basicDroneRobotsInput.setEditable(true);
            this.getChildren().add(basicDroneRobotsInput);

            qualityDroneRobots = new Label();

            qualityDroneRobots.setText("Quality Drones" + ": ");
            qualityDroneRobots.setPrefSize(200, 20);
            qualityDroneRobots.setPadding(new Insets(10, 0, 10, 0));
            qualityDroneRobots.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(qualityDroneRobots);
            String input5 = String.valueOf(integerInputs.get(4));
            qualityDroneRobotsInput = new TextField(input5);
            qualityDroneRobotsInput.setPrefSize(380, 20);
            qualityDroneRobotsInput.setStyle("-fx-font-family: serif");

            qualityDroneRobotsInput.setPadding(new Insets(10, 0, 10, 0));
            qualityDroneRobotsInput.setEditable(true);
            this.getChildren().add(qualityDroneRobotsInput);
            this.setAlignment(Pos.CENTER_LEFT);


            saveButton = new Button("Save");
            saveButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            this.getChildren().add(saveButton);
            this.setPrefSize(500, 60);
            this.setStyle("-fx-font-family: serif");
            this.setAlignment(Pos.CENTER_LEFT);

            decisionSaved = new Label();
            decisionSaved.setText("Choices Saved");
            decisionSaved.setPrefSize(200, 20);
            decisionSaved.setPadding(new Insets(10, 0, 10, 0));
            decisionSaved.setAlignment(Pos.CENTER_LEFT);
            decisionSaved.setVisible(false);
            this.getChildren().add(decisionSaved);

        }


    }
}

