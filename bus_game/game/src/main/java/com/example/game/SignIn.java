package com.example.game;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.text.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.example.game.SignIn.AccountLogIn;
import com.example.game.SignIn.Password;
import com.example.game.SignIn.Username;

public class SignIn extends BorderPane {
    private Header3 header3;
    private AccountLogIn accountLogIn;
    private Scene signInScene;

    private Button loginButton;
    private Button signUpButton;

    private Username username;
    private Password password;

    private Label correctPasswordCheck;


    String defaultLabelStyle = "-fx-font-family: serif";

    SignIn(Stage currStage, App currApp){
        header3 = new Header3("Welcome");
        accountLogIn = new AccountLogIn();
        this.setTop(header3);

        loginButton = accountLogIn.getLoginButton();
        signUpButton = accountLogIn.getSignUpButton();

        signInScene = new Scene(this, App.width, App.height);
        currStage.setScene(signInScene);
        currStage.show();

        currStage.setResizable(true);
        this.setCenter(accountLogIn);

        loginButton.setOnAction(e -> {
            boolean isStarted = App.mdbAdmin.getStarted();
            App.username = username.getUsernameField().getText();
            if(App.username.equals("admin")){
                if(!App.mdbAdmin.correctUsernameCheck(username.getUsernameField().getText(), password.getPasswordField().getText())){
                    correctPasswordCheck.setVisible(true);
                }
                else{
                    new AdminChoices(currStage, currApp, isStarted);
                }
            }
            if(!App.mdb.correctUsernameCheck(username.getUsernameField().getText(), password.getPasswordField().getText())){
                correctPasswordCheck.setText("Incorrect Password");
                correctPasswordCheck.setVisible(true);
            }
            else{
                if(isStarted){
                    //App.mdb.setToZero(App.username);
                    ArrayList<Double> timeChoices = App.mdbAdmin.getAdminInputs();
                    Integer[] userInputs = App.mdb.recieveUserInputs(App.username);
                    App.userBasicPrice = userInputs[0];
                    App.userQualityPrice = userInputs[1];
                    App.userAdvertisingSpend = userInputs[2];
                    App.userBasicDroneRobots = userInputs[3];
                    App.userQualityDroneRobots = userInputs[4];
                    App.gameNumber = App.mdb.getGameNumber(App.username);
                    System.out.print(App.gameNumber);


                    LocalDateTime gameStartTime = App.mdbAdmin.getStartTime();
                    Duration duration = Duration.between(gameStartTime, LocalDateTime.now());
                    long differenceInSeconds = duration.getSeconds();
                    App.username = username.getUsernameField().getText();
                    App.mdb.makeStarted(App.username);

                    long timeWar = (long) (timeChoices.get(0) * 60);
                    long timeNewEntrant = (long) (timeChoices.get(1) * 60);
                    long timeCustomerIncrease = (long) (timeChoices.get(2) * 60);
                    long timeCumulative = (long) (timeChoices.get(4) * 60);
                    Double warPercentage = timeChoices.get(5);
                    Double newEntrant = timeChoices.get(6);

                    int numTimesWar = (int) (differenceInSeconds/timeWar);
                    int numTimesNewEntrant = (int) (differenceInSeconds/timeNewEntrant);
                    int numTimesCustomerIncrease = (int) (differenceInSeconds/timeCustomerIncrease);
                    int numTimesCumulative = (int) (differenceInSeconds/timeCumulative);

                    if(numTimesWar > 0){
                        App.costPerBasicDrone = 20;
                        App.costPerQualityDrone = 60;
                        App.robotsCostPerPeriod = 400;
                    }
                   
                    App.basicCustomers =  (int) (App.basicCustomers * Math.pow((1-(warPercentage.doubleValue()/100)), numTimesWar));
                    App.qualityCustomers = (int) (App.qualityCustomers * Math.pow((1-(warPercentage.doubleValue()/100)), numTimesWar));
                    
                    double decimalDecrease = (100-newEntrant) * 0.01;
                    App.newEntrantPercentage *= Math.pow(decimalDecrease, numTimesNewEntrant);

                    App.basicCustomers *= Math.pow(1.05, numTimesCustomerIncrease);
                    App.qualityCustomers *= Math.pow(1.05, numTimesCustomerIncrease);


                    LocalDateTime futureDateTime = gameStartTime.plusSeconds(timeWar * (numTimesWar+1));
                    App.startMonitoringWar(futureDateTime, currStage, currApp);
                    LocalDateTime futureDateTime2 = gameStartTime.plusSeconds(timeNewEntrant * (numTimesNewEntrant+1));
                    App.startMonitoringEntrant(futureDateTime2, currStage, currApp);
                    LocalDateTime futureDateTime3 = gameStartTime.plusSeconds(timeCustomerIncrease * (numTimesCustomerIncrease+1));
                    App.startMonitoringCustomerIncrease(futureDateTime3);
                    LocalDateTime futureDateTime4 = gameStartTime.plusSeconds(timeCumulative * (numTimesCumulative+1));
                    App.startMonitoringCumulative(futureDateTime4);

                    App.timer = (int) (((long) timeCumulative * (numTimesCumulative+1) - differenceInSeconds));



                    App.numPlayers = (int) App.mdb.getGameNumberSize(App.gameNumber);
                    for(int i = 0; i < App.numPlayers-1; i++){
                        Integer[] zeroes = {0, 0, 0, 0, 0};
                        Double[] customers = {0.0, 0.0};
                        App.lastEnemyCustomers.add(customers);
                        App.lastEnemyDecisions.add(zeroes);
                    }
                    App.mdb.watchForGameChange(App.gameNumber);
                    App.mdbAdmin.watchForGameEnd(currStage, currApp);
                    App.enemyUsernames = App.mdb.getEnemyUsernames(App.username, App.gameNumber);
                    Platform.runLater(() -> {
                        new CorporateLobby(currStage, currApp); 
                    });
                }
                else{
                    correctPasswordCheck.setText("Game has not started yet");
                    correctPasswordCheck.setVisible(true);
                }
            }
            
        });
        signUpButton.setOnAction(e -> {
            if(username.getUsernameField().getText().equals("admin")){
                App.mdbAdmin.addAdmin(password.getPasswordField().getText()); 
                new AdminChoices(currStage, currApp, false);
            }
            if(App.mdbAdmin.getStarted()){
                if(App.mdb.getSize() == 0){
                    App.gameNumber = 1;
                }
                else{
                    boolean previousStarted = App.mdb.lastStarted();
                    if(previousStarted){
                        App.gameNumber = App.mdb.getPreviousGameNumber() + 1;
                    }
                    else{
                        App.gameNumber = App.mdb.getPreviousGameNumber();
                    }
                }
                System.out.print(App.gameNumber);
                ArrayList<Double> timeChoices = App.mdbAdmin.getAdminInputs();
                App.numPlayers = (int) (timeChoices.get(3).doubleValue());
                App.mdb.addEntry(username.getUsernameField().getText(), password.getPasswordField().getText(), App.gameNumber);
                App.playerNumber = (int) App.mdb.getGameNumberSize(App.gameNumber);
                Platform.runLater(() -> {
                    correctPasswordCheck.setText("Successfully Registered. Waiting for opponents...");
                    correctPasswordCheck.setVisible(true);
                });
                new Thread(() -> {
                    if(App.playerNumber < App.numPlayers){
                        try {
                            for (int i = App.playerNumber; i < App.numPlayers; i++) {
                                App.mdb.stopUntilInsertion(); 
                            }
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    LocalDateTime gameStartTime = App.mdbAdmin.getStartTime();
                    Duration duration = Duration.between(gameStartTime, LocalDateTime.now());
                    long differenceInSeconds = duration.getSeconds();
                    App.username = username.getUsernameField().getText();
                    App.mdb.makeStarted(App.username);

    
                    long timeWar = (long) (timeChoices.get(0) * 60);
                    long timeNewEntrant = (long) (timeChoices.get(1) * 60);
                    long timeCustomerIncrease = (long) (timeChoices.get(2) * 60);
                    long timeCumulative = (long) (timeChoices.get(4) * 60);
                    Double warPercentage = timeChoices.get(5);
                    Double newEntrant = timeChoices.get(6);

                    int numTimesWar = (int) (differenceInSeconds/timeWar);
                    int numTimesNewEntrant = (int) (differenceInSeconds/timeNewEntrant);
                    int numTimesCustomerIncrease = (int) (differenceInSeconds/timeCustomerIncrease);
                    int numTimesCumulative = (int) (differenceInSeconds/timeCumulative);

                    if(numTimesWar > 0){
                        App.costPerBasicDrone = 20;
                        App.costPerQualityDrone = 60;
                        App.robotsCostPerPeriod = 400;
                    }
                
                    App.basicCustomers =  (int) (App.basicCustomers * Math.pow((1-(warPercentage.doubleValue()/100)), numTimesWar));
                    App.qualityCustomers = (int) (App.qualityCustomers * Math.pow((1-(warPercentage.doubleValue()/100)), numTimesWar));
                    
                    double decimalDecrease = (100-newEntrant) * 0.01;
                    App.newEntrantPercentage *= Math.pow(decimalDecrease, numTimesNewEntrant);

                    App.basicCustomers *= Math.pow(1.05, numTimesCustomerIncrease);
                    App.qualityCustomers *= Math.pow(1.05, numTimesCustomerIncrease);


                    LocalDateTime futureDateTime = gameStartTime.plusSeconds(timeWar * (numTimesWar+1));
                    App.startMonitoringWar(futureDateTime, currStage, currApp);
                    LocalDateTime futureDateTime2 = gameStartTime.plusSeconds(timeNewEntrant * (numTimesNewEntrant+1));
                    App.startMonitoringEntrant(futureDateTime2, currStage, currApp);
                    LocalDateTime futureDateTime3 = gameStartTime.plusSeconds(timeCustomerIncrease * (numTimesCustomerIncrease+1));
                    App.startMonitoringCustomerIncrease(futureDateTime3);
                    LocalDateTime futureDateTime4 = gameStartTime.plusSeconds(timeCumulative * (numTimesCumulative+1));
                    App.startMonitoringCumulative(futureDateTime4);

                    App.timer = (int) (((long) timeCumulative * (numTimesCumulative+1) - differenceInSeconds));

                    for(int i = 0; i < App.numPlayers-1; i++){
                        Integer[] zeroes = {0, 0, 0, 0, 0};
                        App.lastEnemyDecisions.add(zeroes);
                        Double[] customers = {0.0, 0.0};
                        App.lastEnemyCustomers.add(customers);
                    }

                    App.mdb.watchForGameChange(App.gameNumber);
                    App.mdbAdmin.watchForGameEnd(currStage, currApp);
                    App.enemyUsernames = App.mdb.getEnemyUsernames(App.username, App.gameNumber);

                    Platform.runLater(() -> {
                        new CorporateLobby(currStage, currApp); 
                    });
                }).start();
                
            }
            else{
                correctPasswordCheck.setText("Game has not started yet");
                correctPasswordCheck.setVisible(true);
            }
        });

    }


    class Header3 extends HBox {
        private Text titleText;

        Header3(String title) {
            this.setPrefSize(500, 60); // Size of the header
            this.setStyle("-fx-font-family: serif");
            titleText = new Text(title); // Text of the Header
            titleText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            this.getChildren().add(titleText);
            this.setAlignment(Pos.CENTER); // Align the text to the Center
        }
    }

    class Password extends HBox {

        private TextField password;
        private Label prompt;

        Password(){

            this.setPrefSize(500, 30);
            this.setStyle("-fx-font-family: serif");

            prompt = new Label();
            prompt.setText("Password: "); // create password prompt
            prompt.setPrefSize(80, 20);
            prompt.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            prompt.setAlignment(Pos.CENTER);
            this.getChildren().add(prompt); // add index label to hbox

            password = new TextField(); // create password text field
            password.setPrefSize(380, 20); // set size of text field
            password.setStyle("-fx-font-family: serif"); // set background color of
            // texfield
            password.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
            password.setEditable(true);
            this.getChildren().add(password); // add textlabel to password
            this.setAlignment(Pos.CENTER);
        }

        public TextField getPasswordField() {
            return this.password;
        }
    }

    class Username extends HBox {

        private TextField username;
        private Label prompt;

        Username() {
            this.setPrefSize(500, 80);
            this.setStyle("-fx-font-family: serif");
            prompt = new Label();
            prompt.setText("Username: "); // create prompt label
            prompt.setPrefSize(80, 20); // set size of prompt label
            prompt.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the label
            this.getChildren().add(prompt);

            username = new TextField(); // create username text field
            username.setPrefSize(380, 20); // set size of text field
            username.setStyle("-fx-font-family: serif"); // set background color
            // texfield
            username.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
            username.setEditable(true);
            this.getChildren().add(username); 
            this.setAlignment(Pos.CENTER);
        }

        public TextField getUsernameField() {
            return this.username;
        }
    }

    class CorrectPasswordLabel extends HBox{
        CorrectPasswordLabel(){
            correctPasswordCheck = new Label("Incorrect Password");
            correctPasswordCheck.setPrefSize(400, 20);
            correctPasswordCheck.setPadding(new Insets(10, 0, 10, 0));
            correctPasswordCheck.setAlignment(Pos.CENTER);
            correctPasswordCheck.setVisible(false);
            this.getChildren().add(correctPasswordCheck);
        }
        
    }

    class AccountLogIn extends VBox {

        AccountLogIn(){

            this.setStyle("-fx-font-family: serif");

            username = new Username();
            password = new Password();
            CorrectPasswordLabel cpl = new CorrectPasswordLabel();
            this.getChildren().addAll(username, password, cpl);
            cpl.setAlignment(Pos.CENTER);
            this.setAlignment(Pos.CENTER);
            loginButton = new Button("Log In");
            signUpButton = new Button("Register");

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(0);

            hBox.getChildren().addAll(loginButton, signUpButton);
            this.getChildren().addAll(hBox);
        }

        public Button getLoginButton() {
            return loginButton;
        }

        public Button getSignUpButton() {
            return signUpButton;
        }

    }

}

