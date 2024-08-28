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
import javafx.scene.text.Font;
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
    private Label timer;
    List<VBox> choicesPlusCustomers = new ArrayList<>();
    List<TextField> basicPriceTextFields = new ArrayList<>();
    List<TextField> qualityPriceTextFields = new ArrayList<>();
    List<TextField> advertisingTextFields = new ArrayList<>();
    List<TextField> basicCustomerTextFields = new ArrayList<>();
    List<TextField> qualityCustomerTextFields = new ArrayList<>();
    CompetitorDecisions(Stage currStage, App currApp){
        currStage.setResizable(true);
        header = new Header();
        this.setTop(header);
        lsb = new LeftSideButtons();
        this.setLeft(lsb);
        int size = App.lastEnemyDecisions.size();
        for(int i = 0; i < size; i++){
            choicesPlusCustomers.add(new Inputs(i)); //pass in an index to signify which competitor it is 
        }
        allInputs = new AllInputs(choicesPlusCustomers);
        this.setCenter(allInputs);

        previousChoicesScene = new Scene(this, App.width, App.height);
        currStage.setScene(previousChoicesScene);

        //Going to different tabs, stops timelines because they are not needed on other scenes
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

        //Used to allow user to see changes without leaving the scene
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            
            //sets timer in minutes other than last 60 seconds
            if(App.timer >= 60){
                timer.setText(String.valueOf(App.timer/60) + " min");
            }
            else{
                timer.setText(String.valueOf(App.timer)); // show seconds if under a minute
            }
            
            //only check if a decision period has passed
            if(!App.isFirstDecisionPeriod && App.hasAccumulated){
                for(int i = 0; i < basicPriceTextFields.size(); i++){
                    basicPriceTextFields.get(i).setText(String.valueOf(App.lastEnemyDecisions.get(i)[0]));
                    qualityPriceTextFields.get(i).setText(String.valueOf(App.lastEnemyDecisions.get(i)[1]));
                    advertisingTextFields.get(i).setText(String.valueOf(App.lastEnemyDecisions.get(i)[2]));
                    basicCustomerTextFields.get(i).setText(String.valueOf(App.lastEnemyCustomers.get(i)[0]));
                    qualityCustomerTextFields.get(i).setText(String.valueOf(App.lastEnemyCustomers.get(i)[1]));
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
            competitorDecisionButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
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
        Inputs(int index){



            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");

            Label usernameLabel = new Label(App.enemyUsernames.get(index) + "'s Decisions");
            usernameLabel.setPrefSize(150, 20);// set size of label
            usernameLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            usernameLabel.setAlignment(Pos.CENTER);
            this.getChildren().add(usernameLabel);


            Label basicPriceLabel = new Label("Basic Price");
            basicPriceLabel.setPrefSize(100, 20);// set size of label
            basicPriceLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            basicPriceLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(basicPriceLabel);

            TextField basicPriceTextField = new TextField(String.valueOf(App.lastEnemyDecisions.get(index)[0]));
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

            TextField qualityPriceTextField = new TextField(String.valueOf(App.lastEnemyDecisions.get(index)[1]));
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

            TextField advertisingSpendTextField = new TextField(String.valueOf(App.lastEnemyDecisions.get(index)[2]));
            advertisingSpendTextField.setPrefSize(150, 20); // set size of text field
            advertisingSpendTextField.setStyle("-fx-font-family: serif"); // set background color
            // texfield                
            advertisingSpendTextField.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
            advertisingSpendTextField.setEditable(false);
            this.getChildren().add(advertisingSpendTextField); 
            this.setAlignment(Pos.CENTER_LEFT);
            advertisingTextFields.add(advertisingSpendTextField);

            Label basicCustomerLabel = new Label("Basic Customers");
            basicCustomerLabel.setPrefSize(100, 20);// set size of label
            basicCustomerLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            basicCustomerLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(basicCustomerLabel);

            TextField basicCustomerTextField = new TextField(String.valueOf(App.lastEnemyCustomers.get(index)[0]));
            basicCustomerTextField.setPrefSize(150, 20); // set size of text field
            basicCustomerTextField.setStyle("-fx-font-family: serif"); // set background color
            // texfield                
            basicCustomerTextField.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
            basicCustomerTextField.setEditable(false);
            this.getChildren().add(basicCustomerTextField); 
            this.setAlignment(Pos.CENTER_LEFT);
            basicCustomerTextFields.add(basicCustomerTextField);

            Label qualityCustomerLabel = new Label("Quality Customers");
            qualityCustomerLabel.setPrefSize(100, 20);// set size of label
            qualityCustomerLabel.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            qualityCustomerLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(qualityCustomerLabel);

            TextField qualityCustomerTextField = new TextField(String.valueOf(App.lastEnemyCustomers.get(index)[1]));
            qualityCustomerTextField.setPrefSize(150, 20); // set size of text field
            qualityCustomerTextField.setStyle("-fx-font-family: serif"); // set background color
            // texfield                
            qualityCustomerTextField.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
            qualityCustomerTextField.setEditable(false);
            this.getChildren().add(qualityCustomerTextField); 
            this.setAlignment(Pos.CENTER_LEFT);
            qualityCustomerTextFields.add(qualityCustomerTextField);


            }
        }



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
    