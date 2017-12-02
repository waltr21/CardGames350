package com.company;

        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Random;
        import java.util.ResourceBundle;

        import javafx.application.Application;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Label;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.Pane;
        import javafx.scene.layout.VBox;
        import javafx.stage.Stage;


public class FiftyTwoPickupController extends Application implements Initializable {

    @FXML
    public Pane stack;

    public Deck deck;


    FiftyTwoPickup game;
    Scene fiftyTwoPickupScene;

    public FiftyTwoPickupController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        game = new FiftyTwoPickup();
        //game.setup();
        //game.deal();
        deck = new Deck(false);
        while (deck.getSize() > 0) {
            Card c = deck.removeTop();
            placeCard(c);
        }


    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FiftyTwoPickupGUI.fxml"));
        fiftyTwoPickupScene = new Scene(root, 1000, 700);
        stage.setTitle("War!");
        stage.setScene(fiftyTwoPickupScene);
        stage.show();
    }


    public void placeCard(Card c) {
        ImageView cardImage = new ImageView("file:" + c.imagePath());

        Random r = new Random();
        int x = r.nextInt(1000);
        int y = r.nextInt(700);
        cardImage.setFitHeight(150);
        cardImage.setFitWidth(100);
        cardImage.setLayoutX(x);
        cardImage.setLayoutY(y);
        stack.getChildren().add(cardImage);
        //cardImage.set


    }

}
