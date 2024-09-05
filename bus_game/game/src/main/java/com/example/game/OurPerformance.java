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
import java.util.ArrayList;

public class OurPerformance extends BorderPane {
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
    TextField basicRevenueTF;
    TextField basicProfitTF;
    TextField qualityRevenueTF;
    TextField qualityProfitTF;
    TextField basicInventoryTF;
    TextField qualityInventoryTF;
    Integer[] enemyInputs;
    OurPerformance(Stage currStage, App currApp){
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

        competitorPerformanceButton.setOnAction(e -> {
            timeline.stop();
            new CompetitorPerformance(currStage, currApp);
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
            if(App.changeDetected){
                ArrayList<Integer[]> enemyInputs = App.mdb.recieveMultipleEnemyInputs(App.username, App.gameNumber);
                Double[] profitRevenueResults = ResultCalculations.multiPlayerExtraCalculations(App.userBasicPrice, App.userQualityPrice, App.userAdvertisingSpend, App.userBasicDroneRobots, App.userQualityDroneRobots, enemyInputs);
            
                Platform.runLater(() -> {
                    revenueTF.setText(String.valueOf(profitRevenueResults[0])); 
                    profitTF.setText(String.valueOf(profitRevenueResults[1])); 
                    basicRevenueTF.setText(String.valueOf(profitRevenueResults[2]));
                    basicProfitTF.setText(String.valueOf(profitRevenueResults[3]));
                    qualityRevenueTF.setText(String.valueOf(profitRevenueResults[4]));
                    qualityProfitTF.setText(String.valueOf(profitRevenueResults[5]));
                    basicInventoryTF.setText(String.valueOf(App.lastUserInventory[0]));
                    qualityInventoryTF.setText(String.valueOf(App.lastUserInventory[1]));
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
            ourPerformanceButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
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

            ArrayList<Integer[]> enemyInputs = App.mdb.recieveMultipleEnemyInputs(App.username, App.gameNumber);
            Double[] profitRevenueResults = new Double[6];
            profitRevenueResults = ResultCalculations.multiPlayerExtraCalculations(App.userBasicPrice, App.userQualityPrice, App.userAdvertisingSpend, App.userBasicDroneRobots, App.userQualityDroneRobots, enemyInputs);
           
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

            Label basicRevenueLabel = new Label();
            basicRevenueLabel.setText("Basic Revenue"); // 
            basicRevenueLabel.setPrefSize(100, 20);// set size of Revenue label
            basicRevenueLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            basicRevenueLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(basicRevenueLabel); 

            basicRevenueTF = new TextField(String.valueOf(profitRevenueResults[2])); 
            basicRevenueTF.setPrefSize(380, 20); // set size of text field
            basicRevenueTF.setStyle("-fx-font-family: serif"); // set background color
            // texfield                
            basicRevenueTF.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
            basicRevenueTF.setEditable(false);
            this.getChildren().add(basicRevenueTF); 
            this.setAlignment(Pos.CENTER_LEFT);


            Label basicProfitLabel = new Label();
            basicProfitLabel.setText("Basic Profit");  
            basicProfitLabel.setPrefSize(100, 20);// set size of Profit label
            basicProfitLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            basicProfitLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(basicProfitLabel); 

            basicProfitTF = new TextField(String.valueOf(profitRevenueResults[3])); 
            basicProfitTF.setPrefSize(380, 20); 
            basicProfitTF.setStyle("-fx-font-family: serif"); 
            // texfield                
            basicProfitTF.setPadding(new Insets(10, 0, 10, 0)); 
            basicProfitTF.setEditable(false);
            this.getChildren().add(basicProfitTF); 
            this.setAlignment(Pos.CENTER_LEFT);

            Label qualityRevenueLabel = new Label();
            qualityRevenueLabel.setText("Quality Revenue"); // 
            qualityRevenueLabel.setPrefSize(100, 20);// set size of Revenue label
            qualityRevenueLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            qualityRevenueLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(qualityRevenueLabel); 

            qualityRevenueTF = new TextField(String.valueOf(profitRevenueResults[4])); 
            qualityRevenueTF.setPrefSize(380, 20); // set size of text field
            qualityRevenueTF.setStyle("-fx-font-family: serif"); // set background color              
            qualityRevenueTF.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
            qualityRevenueTF.setEditable(false);
            this.getChildren().add(qualityRevenueTF); 
            this.setAlignment(Pos.CENTER_LEFT);

            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            Label qualityProfitLabel = new Label();
            qualityProfitLabel.setText("Profit"); // 
            qualityProfitLabel.setPrefSize(100, 20);// set size of Profit label
            qualityProfitLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            qualityProfitLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(qualityProfitLabel); 

            qualityProfitTF = new TextField(String.valueOf(profitRevenueResults[5])); 
            qualityProfitTF.setPrefSize(380, 20); 
            qualityProfitTF.setStyle("-fx-font-family: serif"); 
            // texfield                
            qualityProfitTF.setPadding(new Insets(10, 0, 10, 0)); 
            qualityProfitTF.setEditable(false);
            this.getChildren().add(qualityProfitTF); 
            this.setAlignment(Pos.CENTER_LEFT);

            Label basicInventoryLabel = new Label();
            basicInventoryLabel.setText("Basic Inventory"); // 
            basicInventoryLabel.setPrefSize(100, 20);// set size of Revenue label
            basicInventoryLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            basicInventoryLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(basicInventoryLabel); 

            basicInventoryTF = new TextField(String.valueOf(App.lastUserInventory[0])); 
            basicInventoryTF.setPrefSize(380, 20); // set size of text field
            basicInventoryTF.setStyle("-fx-font-family: serif"); // set background color              
            basicInventoryTF.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
            basicInventoryTF.setEditable(false);
            this.getChildren().add(basicInventoryTF); 
            this.setAlignment(Pos.CENTER_LEFT);

            Label qualityInventoryLabel= new Label();
            qualityInventoryLabel.setText("Quality Inventory"); // 
            qualityInventoryLabel.setPrefSize(100, 20);// set size of Profit label
            qualityInventoryLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            qualityInventoryLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(qualityInventoryLabel); 

            qualityInventoryTF = new TextField(String.valueOf(App.lastUserInventory[1])); 
            qualityInventoryTF.setPrefSize(380, 20); 
            qualityInventoryTF.setStyle("-fx-font-family: serif"); 
            // texfield                
            qualityInventoryTF.setPadding(new Insets(10, 0, 10, 0)); 
            qualityInventoryTF.setEditable(false);
            this.getChildren().add(qualityInventoryTF); 
            this.setAlignment(Pos.CENTER_LEFT);








            App.changeDetected = false;
        }


    }
}
