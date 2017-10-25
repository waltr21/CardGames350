package com.company;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by RyanWalt on 10/24/17.
 */
public class GoFishController extends Application implements Initializable {
    int numPlayers;
    private GoFish game;
    @FXML public Label messageText;
    public Button showButton;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //playGame();
        game = new GoFish(3);
        setMessageText(game.getMessage());
        playGame();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public GoFishController(){
        numPlayers = 0;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GoFishGUI.fxml"));
        Scene goFish = new Scene(root, 1000, 700);
        stage.setTitle("Go Fish!");
        stage.setScene(goFish);
        stage.show();
        //game = new GoFish(4);
        //System.out.println(game.getMessage());
        //messageText.setText(numPlayers + "");
        //initialize("testing");
        //playGame();
    }

    public void onButtonAction(){
        System.out.println("testing");
        messageText.setText("testing2");
    }

    @FXML public void setMessageText(String m){
        messageText.setText(m);
    }

    @FXML void playGame(){
        int count = 0;
        Player currentPlayer = game.getPlayer();

        game.takeTurn(currentPlayer, 0, 0);
        setMessageText(game.getMessage());
        System.out.println(game.getMessage());
        count++;

    }

}