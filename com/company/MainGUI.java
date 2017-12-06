package com.company;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class to handle the main screen GUI
 */
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

    public void handleFiftyTwoPickupAction() {
        FiftyTwoPickupController newFiftyTwoPickup = new FiftyTwoPickupController();
        try {
            newFiftyTwoPickup.start(new Stage());
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void handleRummyAction() {
        RummyController rummy = new RummyController();
        try {
            rummy.start(new Stage());
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void handleWarAction() {
        WarController newWar = new WarController();
        try {
            newWar.start(new Stage());
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }



}
