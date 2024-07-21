package com.example.game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewEntrantDistruption extends BorderPane{
    private Header3 header;
    private Scene newEntrantDisruptionScene;
    private Button backButton;

    NewEntrantDistruption(Stage currStage, App currApp){
        currStage.setResizable(true);
        App.newEntrantDistruption = true;
        App.warDisruption = false;
        header = new Header3("New Entrant Into the Market");
        this.setTop(header);
        newEntrantDisruptionScene = new Scene(this, App.width, App.height);
        currStage.setScene(newEntrantDisruptionScene);
        App.costPerBasicDrone = App.costPerBasicDrone / 2;
        App.costPerQualityDrone = App.costPerQualityDrone / 2;
        App.robotsCostPerPeriod = App.robotsCostPerPeriod / 2;
        backButton.setOnAction(e -> {
            new CorporateLobby(currStage, currApp);
        });
    }
    


    class Header3 extends HBox {
        private Text titleText;
    

        Header3(String title) {
            

            this.setPrefSize(500, 200); // Size of the header
            this.setStyle("-fx-font-family: serif");

            backButton = new Button("Back");
            this.getChildren().add(backButton);
            backButton.setAlignment(Pos.CENTER_LEFT);
            titleText = new Text(title); // Text of the Header
            titleText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            this.getChildren().add(titleText);
            this.setAlignment(Pos.CENTER); // Align the text to the Center
        }
    }
}