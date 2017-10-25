package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by RyanWalt on 10/24/17.
 */
public class GoFishController extends Application {
    private GoFish game;
    public Label numPlayerText;
    public Slider playerSlider;
    public Button playButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PopUp.fxml"));
        Scene popUp = new Scene(root, 200, 200);
        stage.setTitle("Player Number");
        stage.setScene(popUp);
        stage.show();
    }

    /**
     * Button listener to open the GoFish fxml file.
     */
    public void onButtonAction(){
        try {
            Stage gameStage = new Stage();
            Parent root1 = FXMLLoader.load(getClass().getResource("GoFishGUI.fxml"));
            Scene scene = new Scene(root1, 1000, 700);
            gameStage.setTitle("Go Fish!");
            gameStage.setScene(scene);
            gameStage.show();

        }
        catch(IOException e){
            System.out.println(e);
        }
        game = new GoFish((int) playerSlider.getValue());
        System.out.println(game.getMessage());
    }

    public void playGame(){
        while (game.getGameDeckSize() > 0){
            System.out.println(game.getTurnMessage());
            Player currentPlayer = game.getPlayer();
            game.takeTurn(currentPlayer, 0, 0);
            System.out.println(game.getMessage());


        }
    }



}