package com.example.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

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
    public static long gameNumber;
    public static MongoDB mdb = new MongoDB();
    private static ScheduledExecutorService scheduler;
    private static ScheduledExecutorService schedulerEntrant;
    public static boolean warDisruption = false;
    public static boolean newEntrantDistruption = false;

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

    public static void startMonitoring(LocalDateTime ltd, Stage currStage, App currApp) {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(ltd)) {
                Platform.runLater(() -> {
                    new WarDisruption(currStage, currApp);
                });
                scheduler.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static void startMonitoringEntrant(LocalDateTime ltd, Stage currStage, App currApp) {
        schedulerEntrant = Executors.newScheduledThreadPool(1);
        schedulerEntrant.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(ltd)) {
                Platform.runLater(() -> {
                    new NewEntrantDistruption(currStage, currApp);
                });
                schedulerEntrant.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

}


