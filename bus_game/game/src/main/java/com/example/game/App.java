package com.example.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private Scene signInScene;
    public static int userBasicPrice = 0;
    public static int userQualityPrice = 0;
    public static int userAdvertisingPrice = 0;
    public static String username;
    public static int pricePerBasicDrone = 60;
    public static int costPerBasicDrone = 10;
    public static int pricePerQualityDrone = 100;
    public static int costPerQualityDrone = 30;
    public static int basicCustomers = 200;
    public static int qualityCustomers = 200;
    public static int robotsCostPerPeriod= 200;
    public static int robotsMadePerPeriod = 10;
    public static int playerNumber = 0;
    public static MongoDB mdb = new MongoDB();
    @Override
    public void start(Stage stage) throws IOException {
        SignIn signIn = new SignIn(stage, this);
        signInScene = new Scene(signIn, 1200, 800);
        stage.setScene(signInScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}


