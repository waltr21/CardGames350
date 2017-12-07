package com.company;


        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Random;
        import java.util.ResourceBundle;

        import javafx.application.Application;
        import javafx.event.EventHandler;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.Pane;
        import javafx.scene.layout.VBox;
        import javafx.scene.text.Font;
        import javafx.stage.Stage;
        import javafx.scene.input.*;


public class FiftyTwoPickupController extends Application implements Initializable {

    @FXML
    public Pane stack;

    public Deck deck;

    public int pickedUp = 0;


    FiftyTwoPickup game;
    Scene fiftyTwoPickupScene;

    public FiftyTwoPickupController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        game = new FiftyTwoPickup();
        tossCards();
    }

    public void tossCards () {
        while (game.deck.getSize() > 0) {
            Card c = game.deck.removeTop();
            placeCard(c);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FiftyTwoPickupGUI.fxml"));
        fiftyTwoPickupScene = new Scene(root, 1000, 700);
        stage.setTitle("52 Pickup");
        stage.setScene(fiftyTwoPickupScene);
        stage.show();
    }


    public void placeCard(Card c) {
        ImageView cardImage = new ImageView("file:" + c.imagePath());

        Random r = new Random();
        int x = r.nextInt(900);
        int y = r.nextInt(550);
        cardImage.setFitHeight(150);
        cardImage.setFitWidth(100);
        cardImage.setLayoutX(x);
        cardImage.setLayoutY(y);

        // card add event handler
        cardImage.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                stack.getChildren().remove(cardImage);
                pickedUp++;
                if (pickedUp == 52) {
                    end();
                }
            }
        });

        stack.getChildren().add(cardImage);
        //cardImage.set

    }

    public void end() {

        Label youWin = new Label();
        youWin.setText("You win!");
        youWin.setLayoutX(460);
        youWin.setLayoutY(500);
        youWin.setFont(new Font(30.0));
        stack.getChildren().add(youWin);

        Button newGame = new Button();
        newGame.setText("New game");
        newGame.setLayoutX(500);
        newGame.setLayoutY(400);
        newGame.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                stack.getChildren().removeAll(stack.getChildren());
                game.reset();
                tossCards();
            }
        });

        stack.getChildren().add(newGame);
    }

    @FXML public void clickedCard(ImageView i){
        System.out.println("asds");
    }

}
