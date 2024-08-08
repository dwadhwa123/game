package com.example.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CorporateLobby extends BorderPane {
    //private Header header;
    Label timer;
    private FullHeader fullHeader;
    private Button homePageButton;
    private Button ourPerformanceButton;
    private Button competitorPerformanceButton;
    private Button decisionSummaryButton;
    private Button cumulativePerformanceButton;
    private LeftSideButtons lsb;
    private Scene corporateLobbyScene;
    private InitialConditionLabels initalConditionLabels;
    private Timeline timeline;
    Label basicCustomers;
    Label qualityCustomers;
    CorporateLobby(Stage currStage, App currApp){
        // header = new Header();
        // this.setTop(header);
        fullHeader = new FullHeader();
        this.setTop(fullHeader);
        lsb = new LeftSideButtons();
        this.setLeft(lsb);
        initalConditionLabels = new InitialConditionLabels();
        this.setCenter(initalConditionLabels);
        corporateLobbyScene = new Scene(this, App.width, App.height);
        currStage.setScene(corporateLobbyScene);

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
        cumulativePerformanceButton.setOnAction(e -> {
            timeline.stop();
            new CumulativePerformance(currStage, currApp);
        });

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            basicCustomers.setText("Basic Customers: " + App.basicCustomers);
            qualityCustomers.setText("Quality Customers: " + App.qualityCustomers);
            timer.setText(String.valueOf(App.timer));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); //Runs for an indefinite time
        timeline.play();
    }


    class Header extends HBox{
        Header(){
            homePageButton = new Button("Home Page");
            homePageButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
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
        }

    }
    class Timer extends HBox{
        Timer(){
            timer = new Label();
            timer.setFont(new Font(20));
            timer.setText(String.valueOf(App.timer)); // create prompt label
            timer.setPrefSize(140, 20); // set size of prompt label
            timer.setPadding(new Insets(10, 0, 10, 0));
            this.setStyle("-fx-font-family: serif");
            this.getChildren().add(timer);
        }
    }

    class FullHeader extends HBox{
        FullHeader(){
            Timer t = new Timer();
            Header h = new Header();
            this.getChildren().addAll(t, h);
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

    class InitialConditionLabels extends VBox{
        Label pricePerBasicDrone;
        Label costPerBasicDrone;
        Label pricePerQualityDrone;
        Label costPerQualityDrone;
        Label robotsCostPerPeriod;
        Label robotsMadePerPeriod;
        InitialConditionLabels(){
            this.setStyle("-fx-font-family: serif");
            pricePerBasicDrone = new Label();
            pricePerBasicDrone.setText("Price Per Basic Drone: $" + App.pricePerBasicDrone);
            pricePerBasicDrone.setPrefSize(200, 20);
            pricePerBasicDrone.setPadding(new Insets(10, 0, 10, 0));
            pricePerBasicDrone.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(pricePerBasicDrone);

            this.setStyle("-fx-font-family: serif");
            costPerBasicDrone = new Label();
            costPerBasicDrone.setText("Cost Per Basic Drone: $" + App.costPerBasicDrone);
            costPerBasicDrone.setPrefSize(200, 20);
            costPerBasicDrone.setPadding(new Insets(10, 0, 10, 0));
            costPerBasicDrone.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(costPerBasicDrone);

            this.setStyle("-fx-font-family: serif");
            pricePerQualityDrone = new Label();
            pricePerQualityDrone.setText("Price Per Quality Drone: $" + App.pricePerQualityDrone);
            pricePerQualityDrone.setPrefSize(200, 20);
            pricePerQualityDrone.setPadding(new Insets(10, 0, 10, 0));
            pricePerQualityDrone.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(pricePerQualityDrone);

            this.setStyle("-fx-font-family: serif");
            costPerQualityDrone = new Label();
            costPerQualityDrone.setText("Cost Per Quality Drone: $" + App.costPerQualityDrone);
            costPerQualityDrone.setPrefSize(200, 20);
            costPerQualityDrone.setPadding(new Insets(10, 0, 10, 0));
            costPerQualityDrone.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(costPerQualityDrone);

            this.setStyle("-fx-font-family: serif");
            basicCustomers = new Label();
            basicCustomers.setText("Basic Customers: " + App.basicCustomers);
            basicCustomers.setPrefSize(200, 20);
            basicCustomers.setPadding(new Insets(10, 0, 10, 0));
            basicCustomers.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(basicCustomers);

            this.setStyle("-fx-font-family: serif");
            qualityCustomers = new Label();
            qualityCustomers.setText("Quality Customers: " + App.qualityCustomers);
            qualityCustomers.setPrefSize(200, 20);
            qualityCustomers.setPadding(new Insets(10, 0, 10, 0));
            qualityCustomers.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(qualityCustomers);

            this.setStyle("-fx-font-family: serif");
            robotsCostPerPeriod = new Label();
            robotsCostPerPeriod.setText("Robots Cost Per Period: $" + App.robotsCostPerPeriod);
            robotsCostPerPeriod.setPrefSize(200, 20);
            robotsCostPerPeriod.setPadding(new Insets(10, 0, 10, 0));
            robotsCostPerPeriod.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(robotsCostPerPeriod);

            this.setStyle("-fx-font-family: serif");
            robotsMadePerPeriod = new Label();
            robotsMadePerPeriod.setText("Robots Made Per Period: " + App.robotsMadePerPeriod);
            robotsMadePerPeriod.setPrefSize(200, 20);
            robotsMadePerPeriod.setPadding(new Insets(10, 0, 10, 0));
            robotsMadePerPeriod.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(robotsMadePerPeriod);

        }
    }

}