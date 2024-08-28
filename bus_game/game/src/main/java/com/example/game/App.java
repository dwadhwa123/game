package com.example.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * JavaFX App
 */
public class App extends Application {
    private Scene signInScene;
    public static int height = 400;
    public static int width = 800;
    public static int userBasicPrice = 0;
    public static int userQualityPrice = 0;
    public static int userAdvertisingSpend = 0;
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
    public static MongoDB mdb = new MongoDB("user_collection");
    public static MongoDB mdbAdmin = new MongoDB("admin");
    public static ScheduledExecutorService schedulerWar;
    public static ScheduledExecutorService schedulerEntrant;
    public static ScheduledExecutorService schedulerCustomerIncrease;
    public static ScheduledExecutorService schedulerCumulative;
    public static int numPlayers = 0;
    public static int timer = 0;
    public static double newEntrantPercentage = 100.0;
    public static boolean isFirstDecisionPeriod = true;
    public static ArrayList<String> enemyUsernames;
    public static boolean isWarDisruption = false;
    public static boolean isNewEntrantDisruption = false;

    public static ArrayList<Integer[]> lastEnemyDecisions = new ArrayList<>();
    public static ArrayList<Double[]> lastEnemyCustomers = new ArrayList<>();

    public static boolean hasAccumulated = false;
    public static boolean changeDetected = false;
    private final static ReentrantLock lock = new ReentrantLock();

    @Override
    public void start(Stage stage) throws IOException {
       new SignIn(stage, this);
    }

    public static void main(String[] args) {
        launch();
    }

    public static void startMonitoringWar(LocalDateTime ltd, Stage currStage, App currApp) {
        schedulerWar = Executors.newScheduledThreadPool(1);
        schedulerWar.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(ltd)) {
                Platform.runLater(() -> {
                    App.isWarDisruption = true;
                    if(!App.isNewEntrantDisruption){
                        lock.lock();
                        new WarDisruption(currStage, currApp);
                        lock.unlock();
                    }
                });
                schedulerWar.shutdown();
                ArrayList<Double> timeChoices = App.mdbAdmin.getAdminInputs();
                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDateTime futureDateTime = currentDateTime.plusSeconds((long) (timeChoices.get(0) * 60));
                App.startMonitoringWar(futureDateTime, currStage, currApp);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static void startMonitoringEntrant(LocalDateTime ltd, Stage currStage, App currApp) {
        schedulerEntrant = Executors.newScheduledThreadPool(1);
        schedulerEntrant.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(ltd)) {
                Platform.runLater(() -> {
                    App.isNewEntrantDisruption = true;
                    if(!App.isWarDisruption){
                        lock.lock();
                        new NewEntrantDistruption(currStage, currApp);
                        lock.unlock();
                    }
                });
                schedulerEntrant.shutdown();
                ArrayList<Double> timeChoices = App.mdbAdmin.getAdminInputs();
                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDateTime futureDateTime = currentDateTime.plusSeconds((long) (timeChoices.get(1) * 60));
                App.startMonitoringEntrant(futureDateTime, currStage, currApp);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static void startMonitoringCustomerIncrease(LocalDateTime ltd){
        schedulerCustomerIncrease = Executors.newScheduledThreadPool(1);
        schedulerCustomerIncrease.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(ltd)) {
                Platform.runLater(() -> {
                    App.changeDetected = true;
                    App.basicCustomers *= 1.05;
                    App.qualityCustomers *= 1.05;
                });
                schedulerCustomerIncrease.shutdown();
                ArrayList<Double> timeChoices = App.mdbAdmin.getAdminInputs();
                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDateTime futureDateTime = currentDateTime.plusSeconds((long) (timeChoices.get(2) * 60));
                App.startMonitoringCustomerIncrease(futureDateTime);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static void startMonitoringCumulative(LocalDateTime ltd) {
        schedulerCumulative = Executors.newScheduledThreadPool(1);
        schedulerCumulative.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(ltd)) {
                Platform.runLater(() -> {
                    ArrayList<Integer[]> enemyInputs = App.mdb.recieveMultipleEnemyInputs(App.username, App.gameNumber);
                    lastEnemyDecisions = enemyInputs;
                    ArrayList<Double[]>  basicAndQuality = new ArrayList<>();
                    ArrayList<String> usernames = App.mdb.getEnemyUsernames(App.username, App.gameNumber);

                for(String str: usernames){
                    ArrayList<Integer[]> enemyInputs2 = App.mdb.recieveMultipleEnemyInputs(str, App.gameNumber);
                    Integer[] inputs = App.mdb.getInput(str);
                    Double[] customerResults = ResultCalculations.multiPlayerCustomerCalculations(inputs[0], inputs[1], inputs[2], enemyInputs2);
                    basicAndQuality.add(customerResults);
                }
                App.lastEnemyCustomers = basicAndQuality;  

                    //lastEnemyCustomers
                    App.isFirstDecisionPeriod = false;
                    App.hasAccumulated = true;
                    Double[] profitRevenueResults = new Double[2];
                    profitRevenueResults = ResultCalculations.multiPlayerCalculations(App.userBasicPrice, App.userQualityPrice, App.userAdvertisingSpend, enemyInputs);
                    Double[] userValues = App.mdb.getUserCumulative(App.username);
                    if(profitRevenueResults[0].isNaN()){
                        profitRevenueResults[0] = 0.0;
                    }
                    if(profitRevenueResults[1].isNaN()){
                        profitRevenueResults[1] = 0.0;
                    }
                    App.mdb.saveCumulative(App.username, Math.floor((userValues[0] +  profitRevenueResults[0]) * 100) / 100.0, Math.floor((userValues[1] +  profitRevenueResults[1]) * 100) / 100.0);
                });
                schedulerCumulative.shutdown();
                ArrayList<Double> timeChoices = App.mdbAdmin.getAdminInputs();
                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDateTime futureDateTime = currentDateTime.plusSeconds((long) (timeChoices.get(4) * 60));
                App.timer = (int) (long) (timeChoices.get(4) * 60);
                App.startMonitoringCumulative(futureDateTime);
            }
            App.timer--;
        }, 0, 1, TimeUnit.SECONDS);
    }

}


