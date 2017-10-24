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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainGUI.fxml"));

        Scene scene = new Scene(root, 500, 500);



        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();

    }

    public void handleGoFishAction(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GoFishGUI.fxml"));
        try {
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Go Fish!");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch(IOException e){
            System.out.println("Something went very wrong");
        }

    }


}
