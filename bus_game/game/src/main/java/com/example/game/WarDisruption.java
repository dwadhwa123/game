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
import java.util.*;
import java.time.LocalDateTime;

public class WarDisruption extends BorderPane{
    private Header3 header;
    private Scene warDisruptionScene;
    private Button backButton;

    WarDisruption(Stage currStage, App currApp){
        currStage.setResizable(true);
        header = new Header3("War Has Broken Out - Material and Robot Shortages");
        this.setTop(header);
        warDisruptionScene = new Scene(this, App.width, App.height);
        currStage.setScene(warDisruptionScene);
        App.costPerBasicDrone = 20;
        App.costPerQualityDrone = 60;
        App.robotsCostPerPeriod = 400;
        ArrayList<Double> choices = App.mdbAdmin.getAdminInputs();
        App.basicCustomers =  (int) (App.basicCustomers * (1-(choices.get(5).doubleValue()/100)));
        App.qualityCustomers = (int) (App.qualityCustomers * (1-(choices.get(5).doubleValue()/100)));
        LocalDateTime warEndTime = LocalDateTime.now().plusSeconds((long) (choices.get(6) * 60));
        App.startMonitoringWarEnd(warEndTime);
        backButton.setOnAction(e -> {
            App.isWarDisruption = false;
            if(App.isNewEntrantDisruption){
                new NewEntrantDistruption(currStage, currApp);
            }
            else{
                new CorporateLobby(currStage, currApp);
            }
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

