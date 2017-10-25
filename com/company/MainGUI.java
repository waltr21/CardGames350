package com.company;/**
 * Created by RyanWalt on 10/11/17.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainGUI extends Application {
    public Button goFishButton;
    int numPlayers;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainGUI.fxml"));

        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Button handler for the goFish game.
     * Creates a new Go Fish game.
     */
    public void handleGoFishAction(){
        GoFishController newGoFish = new GoFishController();
        try {
            newGoFish.start(new Stage());
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public int getNumPlayers(){
        return numPlayers;
    }


}
