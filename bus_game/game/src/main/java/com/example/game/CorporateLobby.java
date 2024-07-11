package com.example.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CorporateLobby extends BorderPane {
    private Header header;
    private Button homePageButton;
    private Button ourPerformanceButton;
    private Button competitorPerformanceButton;
    private Button decisionSummaryButton;
    private LeftSideButtons lsb;
    private Scene corporateLobbyScene;
    private InitialConditionLabels initalConditionLabels;
    CorporateLobby(Stage currStage, App currApp){
        header = new Header();
        this.setTop(header);
        lsb = new LeftSideButtons();
        this.setLeft(lsb);
        initalConditionLabels = new InitialConditionLabels();
        this.setCenter(initalConditionLabels);
        corporateLobbyScene = new Scene(this, 1200, 800);
        currStage.setScene(corporateLobbyScene);

        ourPerformanceButton.setOnAction(e -> {
            new OurPerformance(currStage, currApp);
        });

        competitorPerformanceButton.setOnAction(e -> {
            new CompetitorPerformance(currStage, currApp);
        });

        decisionSummaryButton.setOnAction(e -> {
            new DecisionSummary(currStage, currApp);
        });
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

    class InitialConditionLabels extends VBox{
        Label pricePerBasicDrone;
        Label costPerBasicDrone;
        Label pricePerQualityDrone;
        Label costPerQualityDrone;
        Label basicCustomers;
        Label qualityCustomers;
        Label robotsCostPerPeriod;
        Label robotsMadePerPeriod;
        InitialConditionLabels(){
            this.setStyle("-fx-font-family: serif");
            pricePerBasicDrone = new Label();
            pricePerBasicDrone.setText("Price Per Basic Drone: $60");
            pricePerBasicDrone.setPrefSize(200, 20);
            pricePerBasicDrone.setPadding(new Insets(10, 0, 10, 0));
            pricePerBasicDrone.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(pricePerBasicDrone);

            this.setStyle("-fx-font-family: serif");
            costPerBasicDrone = new Label();
            costPerBasicDrone.setText("Cost Per Basic Drone: $10");
            costPerBasicDrone.setPrefSize(200, 20);
            costPerBasicDrone.setPadding(new Insets(10, 0, 10, 0));
            costPerBasicDrone.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(costPerBasicDrone);

            this.setStyle("-fx-font-family: serif");
            pricePerQualityDrone = new Label();
            pricePerQualityDrone.setText("Price Per Quality Drone: $100");
            pricePerQualityDrone.setPrefSize(200, 20);
            pricePerQualityDrone.setPadding(new Insets(10, 0, 10, 0));
            pricePerQualityDrone.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(pricePerQualityDrone);

            this.setStyle("-fx-font-family: serif");
            costPerQualityDrone = new Label();
            costPerQualityDrone.setText("Cost Per Quality Drone: $30");
            costPerQualityDrone.setPrefSize(200, 20);
            costPerQualityDrone.setPadding(new Insets(10, 0, 10, 0));
            costPerQualityDrone.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(costPerQualityDrone);

            this.setStyle("-fx-font-family: serif");
            basicCustomers = new Label();
            basicCustomers.setText("Basic Customers: 200");
            basicCustomers.setPrefSize(200, 20);
            basicCustomers.setPadding(new Insets(10, 0, 10, 0));
            basicCustomers.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(basicCustomers);

            this.setStyle("-fx-font-family: serif");
            qualityCustomers = new Label();
            qualityCustomers.setText("Quality Customers: 200");
            qualityCustomers.setPrefSize(200, 20);
            qualityCustomers.setPadding(new Insets(10, 0, 10, 0));
            qualityCustomers.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(qualityCustomers);

            this.setStyle("-fx-font-family: serif");
            robotsCostPerPeriod = new Label();
            robotsCostPerPeriod.setText("Robots Cost Per Period: $200");
            robotsCostPerPeriod.setPrefSize(200, 20);
            robotsCostPerPeriod.setPadding(new Insets(10, 0, 10, 0));
            robotsCostPerPeriod.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(robotsCostPerPeriod);

            this.setStyle("-fx-font-family: serif");
            robotsMadePerPeriod = new Label();
            robotsMadePerPeriod.setText("Robots Made Per Period: 10");
            robotsMadePerPeriod.setPrefSize(200, 20);
            robotsMadePerPeriod.setPadding(new Insets(10, 0, 10, 0));
            robotsMadePerPeriod.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(robotsMadePerPeriod);

        }
    }

}