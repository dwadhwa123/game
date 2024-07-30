package com.example.game;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;

import com.example.game.AdminChoices.Choices;

public class AdminChoices extends BorderPane{
    private Scene adminChoicesScene;
    Header3 header;
    TextField warDecision;
    TextField newEntrantDecision;
    TextField customerIncreaseDecision;
    private Button saveButton;
    ArrayList<Integer> intInputs;
    Choices choices;
    AdminChoices(Stage currStage, App currApp){
        currStage.setResizable(true);
        header = new Header3();
        this.setTop(header);
        intInputs = App.mdbAdmin.getAdminInputs();
        choices = new Choices(intInputs);
        this.setRight(choices);
        adminChoicesScene = new Scene(this, App.width, App.height);
        currStage.setScene(adminChoicesScene);



        saveButton.setOnAction(e -> {
            int warChoice = Integer.parseInt(warDecision.getText());
            int newEntrantChoice = Integer.parseInt(newEntrantDecision.getText());
            int customerIncreaseChoice = Integer.parseInt(customerIncreaseDecision.getText());
            ArrayList<Integer> choices = new ArrayList<>();
            choices.add(warChoice); choices.add(newEntrantChoice); choices.add(customerIncreaseChoice);
            App.mdbAdmin.saveAdminDecisions(choices);
        });   
    }
    class Header3 extends HBox{
        Header3(){
            Label header = new Label("Administrative Choices");
            header.setPrefSize(300, 60);
            header.setStyle("-fx-font-family: serif");
            header.setFont(new Font(20));
            header.setAlignment(Pos.CENTER);
            this.setPrefSize(300, 100);
            this.getChildren().add(header);
            this.setAlignment(Pos.CENTER);
        }

    }

    class Choices extends VBox{
        Label warDecisionLabel;
        Label newEntrantLabel;
        Label customerIncreaseLabel;
        Choices(ArrayList<Integer> integerInputs){
            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            warDecisionLabel = new Label();

            warDecisionLabel.setText("War Disruption Time");
            warDecisionLabel.setPrefSize(170, 20);
            warDecisionLabel.setPadding(new Insets(10, 0, 10, 0));
            warDecisionLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(warDecisionLabel);
            String input = String.valueOf(integerInputs.get(0));
            warDecision = new TextField(input);
            warDecision.setPrefSize(380, 20);
            warDecision.setStyle("-fx-font-family: serif");

            warDecision.setPadding(new Insets(10, 0, 10, 0));
            warDecision.setEditable(true);
            this.getChildren().add(warDecision);
            this.setAlignment(Pos.CENTER_LEFT);

            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            newEntrantLabel = new Label();

            newEntrantLabel.setText("New Entrant Disruption Time");
            newEntrantLabel.setPrefSize(200, 20);
            newEntrantLabel.setPadding(new Insets(10, 0, 10, 0));
            newEntrantLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(newEntrantLabel);
            String input2 = String.valueOf(integerInputs.get(1));
            newEntrantDecision = new TextField(input2);
            newEntrantDecision.setPrefSize(380, 20);
            newEntrantDecision.setStyle("-fx-font-family: serif");

            newEntrantDecision.setPadding(new Insets(10, 0, 10, 0));
            newEntrantDecision.setEditable(true);
            this.getChildren().add(newEntrantDecision);
            this.setAlignment(Pos.CENTER_LEFT);

            this.setPrefSize(500, 20);
            this.setStyle("-fx-font-family: serif");
            customerIncreaseLabel = new Label();

            customerIncreaseLabel.setText("Customer Increase (5%)");
            customerIncreaseLabel.setPrefSize(150, 20);
            customerIncreaseLabel.setPadding(new Insets(10, 0, 10, 0));
            customerIncreaseLabel.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().add(customerIncreaseLabel);
            String input3 = String.valueOf(integerInputs.get(2));
            customerIncreaseDecision = new TextField(input3);
            customerIncreaseDecision.setPrefSize(380, 20);
            customerIncreaseDecision.setStyle("-fx-font-family: serif");

            customerIncreaseDecision.setPadding(new Insets(10, 0, 10, 0));
            customerIncreaseDecision.setEditable(true);
            this.getChildren().add(customerIncreaseDecision);
            this.setAlignment(Pos.CENTER_LEFT);


            saveButton = new Button("Save");
            saveButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            this.getChildren().add(saveButton);
            this.setPrefSize(500, 60);
            this.setStyle("-fx-font-family: serif");
            this.setAlignment(Pos.CENTER_LEFT);

        }


    }
}
