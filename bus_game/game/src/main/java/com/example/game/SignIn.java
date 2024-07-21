package com.example.game;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;

import javafx.geometry.Insets;
import javafx.scene.text.*;

import java.time.LocalDateTime;


public class SignIn extends BorderPane {
    private Header3 header3;
    private AccountLogIn accountLogIn;

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


        currStage.setResizable(true);
        this.setCenter(accountLogIn);

        loginButton.setOnAction(e -> {
            if(!App.mdb.correctUsernameCheck(username.getUsernameField().getText(), password.getPasswordField().getText())){
                correctPasswordCheck.setVisible(true);
            }
            else{
                App.username = username.getUsernameField().getText();
                Integer[] userInputs = App.mdb.recieveUserInputs(App.username);
                App.userBasicPrice = userInputs[0];
                App.userQualityPrice = userInputs[1];
                App.userAdvertisingSpend = userInputs[2];
                App.gameNumber = App.mdb.getGameNumber(App.username);
                new CorporateLobby(currStage, currApp);
            }
            
        });
        signUpButton.setOnAction(e -> {
            App.mdb.addEntry(username.getUsernameField().getText(), password.getPasswordField().getText());
            if((App.mdb.getSize()+2)%2 == 1){
                App.playerNumber = 1;
                try {
                    App.mdb.stopUntilChange();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            else if((App.mdb.getSize()+2)%2 == 0){
                App.playerNumber = 2;
            }
            App.username = username.getUsernameField().getText();
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime futureDateTime = currentDateTime.plusSeconds(300);
            App.startMonitoring(futureDateTime, currStage, currApp);
            LocalDateTime futureDateTime2 = currentDateTime.plusSeconds(400);
            App.startMonitoringEntrant(futureDateTime2, currStage, currApp);
            new CorporateLobby(currStage, currApp);
            System.out.print(App.username + " Change Detection exit worked");
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
            correctPasswordCheck.setPrefSize(120, 20);
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

