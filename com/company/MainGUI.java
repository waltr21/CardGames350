package com.company;/**
 * Created by RyanWalt on 10/11/17.
 */

import javafx.application.Application;
import javafx.fxml.FXML;
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
    @FXML
    public Button goFishButton;

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


        //goFishButton.setText("test");
    }

    /**
     * Button handler for the goFish game.
     * Creates a new Go Fish game.
     */
    public void handleGoFishAction(){
        PopUpController newGoFish = new PopUpController();
        try {
            newGoFish.start(new Stage());
        }
        catch(Exception e){
            System.out.println(e);
        }
    }



}
