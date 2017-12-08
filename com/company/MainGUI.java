package com.company;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Controller class to handle the main screen GUI
 */
public class MainGUI extends Application {
    @FXML
    public ImageView GoFishImage, pickupImage, warImage, rummyImage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainGUI.fxml"));

        Scene scene = new Scene(root, 900, 700);

        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
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

    public void fishMouseEnter(){
        GoFishImage.setBlendMode(BlendMode.LIGHTEN);
    }

    public void fishMouseExit(){
        GoFishImage.setBlendMode(BlendMode.SRC_OVER);
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

    public void pickupMouseEnter(){
        pickupImage.setBlendMode(BlendMode.LIGHTEN);
    }

    public void pickupMouseExit(){
        pickupImage.setBlendMode(BlendMode.SRC_OVER);
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

    public void warMouseEnter(){
        warImage.setBlendMode(BlendMode.LIGHTEN);
    }

    public void warMouseExit(){
        warImage.setBlendMode(BlendMode.SRC_OVER);
    }

    public void rummyMouseEnter(){
        rummyImage.setBlendMode(BlendMode.LIGHTEN);
    }

    public void rummyMouseExit(){
        rummyImage.setBlendMode(BlendMode.SRC_OVER);
    }

    public void handleRummyAction() {
        RummyController rummy = new RummyController();
        try {
            rummy.start(new Stage());
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
