package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * Controller class for the popup menue for
 * specific games.
 */
public class PopUpController extends Application {
    public Slider playerSlider;
    public Button playButton;
    private int numPlayers;

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
    public void onButtonAction() {
        GoFishController newGame = new GoFishController();
        numPlayers = (int) playerSlider.getValue();
        newGame.numPlayers = numPlayers;
        //newGame.setNumPlayers(numPlayers);
        try {
            newGame.start(new Stage());
        } catch (Exception e) {
            System.out.println("From PopUp: \n" + e);
        }
    }
}