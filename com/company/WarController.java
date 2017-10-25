package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by RyanWalt on 10/24/17.
 */
public class WarController extends Application implements Initializable {
    int numPlayers;
    private GoFish game;
    @FXML public Label messageText, turnText;
    @FXML public Button showButton;
    @FXML public ChoiceBox valueChoice;

    //@FXML public ImageView cardImage1;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        game = new War();
        resetChoiceBox();
        setTurnText(game.getTurnMessage());
        setMessageText(game.getMessage());
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
    }

    @FXML public void onButtonAction(){
        System.out.println("testing");
        messageText.setText("testing2");

    }

    @FXML public void onCardDrag1(){
        System.out.println("Drag Detected!!");
    }

    @FXML public void onCardClicked1(){
        System.out.println("Card 1 clicked!");
        int playerIndex = 1;
        playTurn(playerIndex, 2);
        setChoiceBoxPos();
        valueChoice.setVisible(true);
    }

    @FXML public void onCardClicked2(){
        System.out.println("Card 2 clicked!");
        int playerIndex = 2;
        playTurn(playerIndex, 2);
        setChoiceBoxPos();
        valueChoice.setVisible(true);
    }

    @FXML public void onCardClicked3(){
        System.out.println("Card 3 clicked!");
        int playerIndex = 3;
        playTurn(playerIndex, 2);
        setChoiceBoxPos();
        valueChoice.setVisible(true);
    }

    @FXML public void onCardClicked4(){
        System.out.println("Card 4 clicked!");
        int playerIndex = 4;
        playTurn(playerIndex, 2);
        setChoiceBoxPos();
        valueChoice.setVisible(true);
    }

    @FXML public void setMessageText(String m){
        messageText.setText(m);
    }

    @FXML public void setTurnText(String m){
        turnText.setText(m);
    }

    @FXML public void resetChoiceBox(){
        valueChoice.setItems(FXCollections.observableArrayList(
                "Ace", "2", "3", "4", "5", "6", "7",
                "8", "9", "10", "Jack", "Queen", "King"));
        valueChoice.setVisible(false);
        valueChoice.setLayoutX(0.0);
        valueChoice.setLayoutY(0.0);
    }

    @FXML public void setChoiceBoxPos(){
        Point p = MouseInfo.getPointerInfo().getLocation();
        valueChoice.setLayoutX(p.getX());
        valueChoice.setLayoutY(p.getY());
    }

    @FXML void playTurn(int playerIndex, int value){
        Player currentPlayer = game.getPlayer();
        game.takeTurn(currentPlayer, playerIndex, 2);
        setMessageText(game.getMessage());
        setTurnText(game.getTurnMessage());
        System.out.println(game.getMessage());
    }
}