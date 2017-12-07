package com.company;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
//import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class RummyController extends Application implements Initializable {
    private int playerIndex;
    private boolean showing,draw,discard;
    private Rummy rummy;
    @FXML
    public Label messageText, turnText, userCardsLabel, discardLabel;
    @FXML
    public Button showCards, showMelds1, showMelds2, showMelds3, showMelds4, playMeld;
    @FXML
    public ChoiceBox valueChoice;
    @FXML
    public Rectangle showImage, showImage2;
    @FXML
    public ImageView cardImage1, cardImage2,
            cardImage3, cardImage4, cardDeck, cardDiscard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rummy = new Rummy(4);
        userCardsLabel.setLayoutX(cardImage1.getLayoutX());
        userCardsLabel.setLayoutY(cardImage1.getLayoutY());
        showImage.setLayoutX(cardImage1.getLayoutX());
        showImage.setLayoutY(cardImage1.getLayoutY());
        discardLabel.setLayoutX(cardDiscard.getLayoutX());
        discardLabel.setLayoutY(cardDiscard.getLayoutY());
        showImage2.setLayoutX(cardDiscard.getLayoutX());
        showImage2.setLayoutY(cardDiscard.getLayoutY());
        discardLabel.setText(rummy.getDiscard().toString());
        discardLabel.setVisible(true);
        turnText.setText("Player "+ (rummy.getTurn())+", it's your turn.");
        messageText.setText(rummy.getMessage());

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("RummyGUI.fxml"));
        Scene game = new Scene(root, 800, 600);
        stage.setResizable(false);
        stage.setTitle("Rummy");
        stage.setScene(game);
        stage.show();

    }

    @FXML public void onButtonAction(){
        userCardsLabel.setText(rummy.toString((rummy.getCurrentPlayer())));
        if (rummy.getTurn() == 1){
            showImage.setLayoutX(cardImage1.getLayoutX());
            showImage.setLayoutY(cardImage1.getLayoutY());
            userCardsLabel.setLayoutX(cardImage1.getLayoutX());
            userCardsLabel.setLayoutY(cardImage1.getLayoutY());
        }
        else if (rummy.getTurn() == 2){
            showImage.setLayoutX(cardImage2.getLayoutX());
            showImage.setLayoutY(cardImage2.getLayoutY());
            userCardsLabel.setLayoutX(cardImage2.getLayoutX());
            userCardsLabel.setLayoutY(cardImage2.getLayoutY());
        }
        else if (rummy.getTurn() == 3){
            showImage.setLayoutX(cardImage3.getLayoutX());
            showImage.setLayoutY(cardImage3.getLayoutY());
            userCardsLabel.setLayoutX(cardImage3.getLayoutX());
            userCardsLabel.setLayoutY(cardImage3.getLayoutY());
        }
        else if (rummy.getTurn() == 4){
            showImage.setLayoutX(cardImage4.getLayoutX());
            showImage.setLayoutY(cardImage4.getLayoutY());
            userCardsLabel.setLayoutX(cardImage4.getLayoutX());
            userCardsLabel.setLayoutY(cardImage4.getLayoutY());
        }

        if (!showing) {
            //showCards.setText("Hide cards");
            showImage.setVisible(true);
            userCardsLabel.setVisible(true);
            showing = true;
        }
        else{
            //showCards.setText("Show cards");
            showImage.setVisible(false);
            userCardsLabel.setVisible(false);
            showing = false;
        }

    }

    @FXML void onPlayMeldAction(){

        if(rummy.getStatus()){
            return;
        }
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Play Meld");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField suit = new TextField();
        suit.setPromptText("Player");
        TextField value = new TextField();
        value.setPromptText("Suit");
        gridPane.add(new Label("Suit"),0,0);
        gridPane.add(suit, 1, 0);
        gridPane.add(new Label("Value:"), 2, 0);
        gridPane.add(value, 3, 0);

        dialog.getDialogPane().setContent(gridPane);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(suit.getText(), value.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

    }

    @FXML public void onMeld1Action() {
        if (rummy.getMelds().get(0).size() > 0){
            userCardsLabel.setText(rummy.toString((rummy.getMelds().get(0))));
            showImage.setLayoutX(cardImage1.getLayoutX());
            showImage.setLayoutY(cardImage1.getLayoutY());
            userCardsLabel.setLayoutX(cardImage1.getLayoutX());
            userCardsLabel.setLayoutY(cardImage1.getLayoutY());
            if (!showing) {
               // showMelds1.setText("Hide melds");
                showImage.setVisible(true);
                userCardsLabel.setVisible(true);
                showing = true;
            } else {
              //  showMelds1.setText("Show melds");
                showImage.setVisible(false);
                userCardsLabel.setVisible(false);
                showing = false;
            }
        }
    }

    @FXML void onMeld2Action(){

        if (rummy.getMelds().get(1).size() > 1){
            userCardsLabel.setText(rummy.toString((rummy.getMelds().get(1))));
            showImage.setLayoutX(cardImage2.getLayoutX());
            showImage.setLayoutY(cardImage2.getLayoutY());
            userCardsLabel.setLayoutX(cardImage2.getLayoutX());
            userCardsLabel.setLayoutY(cardImage2.getLayoutY());
            if (!showing) {
              //  showMelds2.setText("Hide melds");
                showImage.setVisible(true);
                userCardsLabel.setVisible(true);
                showing = true;
            } else {
             //   showMelds2.setText("Show melds");
                showImage.setVisible(false);
                userCardsLabel.setVisible(false);
                showing = false;
            }
        }

    }

    @FXML void onMeld3Action(){

        if (rummy.getMelds().get(2).size() > 2){
            userCardsLabel.setText(rummy.toString((rummy.getMelds().get(2))));
            showImage.setLayoutX(cardImage3.getLayoutX());
            showImage.setLayoutY(cardImage3.getLayoutY());
            userCardsLabel.setLayoutX(cardImage3.getLayoutX());
            userCardsLabel.setLayoutY(cardImage3.getLayoutY());
            if (!showing) {
             //   showMelds3.setText("Hide melds");
                showImage.setVisible(true);
                userCardsLabel.setVisible(true);
                showing = true;
            } else {
             //   showMelds3.setText("Show melds");
                showImage.setVisible(false);
                userCardsLabel.setVisible(false);
                showing = false;
            }
        }


    }

    @FXML void onMeld4Action(){

        if (rummy.getMelds().get(3).size() > 3){
            userCardsLabel.setText(rummy.toString((rummy.getMelds().get(3))));
            showImage.setLayoutX(cardImage4.getLayoutX());
            showImage.setLayoutY(cardImage4.getLayoutY());
            userCardsLabel.setLayoutX(cardImage4.getLayoutX());
            userCardsLabel.setLayoutY(cardImage4.getLayoutY());
            if (!showing) {
             //   showMelds4.setText("Hide melds");
                showImage.setVisible(true);
                userCardsLabel.setVisible(true);
                showing = true;
            } else {
              //  showMelds4.setText("Show melds");
                showImage.setVisible(false);
                userCardsLabel.setVisible(false);
                showing = false;
            }
        }


    }

    @FXML public void onCardDragDeck(){
        if(draw)
            return;
        cardDeck.setBlendMode(BlendMode.LIGHTEN);
    }

    @FXML public void onCardDragDiscard(){
        if(discard)
            return;
        cardDiscard.setBlendMode(BlendMode.LIGHTEN);
    }

    @FXML public void onCardExitDeck(){cardDeck.setBlendMode(BlendMode.SRC_OVER);}

    @FXML public void onCardExitDiscard(){cardDeck.setBlendMode(BlendMode.SRC_OVER);}

    @FXML public void setMessageText(String m){
        messageText.setText(m);
    }

    @FXML public void setTurnText(String m){
        turnText.setText(m);
    }

    @FXML public void onEndTurn(){

        if(rummy.getStatus()){
            return;
        }
        if(draw == false && discard == false){

            return;

        }
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("End Turn");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField suit = new TextField();
        suit.setPromptText("Suit");
        TextField value = new TextField();
        value.setPromptText("Value");
        gridPane.add(new Label("Suit"),0,0);
        gridPane.add(suit, 1, 0);
        gridPane.add(new Label("Value:"), 2, 0);
        gridPane.add(value, 3, 0);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(suit.getText(), value.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        String s = suit.getText().toLowerCase().substring(0,1);
        String v = value.getText().toLowerCase();
        int s_int = 0;
        int v_int = 0;
        switch (s){

            case "s":
                s_int = 0;
                break;
            case "c":
                s_int = 1;
                break;
            case "h":
                s_int = 2;
                break;
            case "d":
                s_int = 3;
                break;

        }
        switch(v.substring(0,1)){

            case "j":
                v_int = 11;
                break;
            case "q":
                v_int = 12;
                break;
            case "k":
                v_int = 13;
                break;
            case "a":
                v_int = 1;
                break;
            default:
                v_int = Integer.parseInt(v);
                break;

        }
        rummy.discard(new Card(v_int,s_int));
        playerIndex+=1;
        if(playerIndex > 3)
            playerIndex = 0;
        messageText.setText(rummy.getMessage());
        turnText.setText(rummy.getTurnMessage());
        ArrayList<Card> pile = rummy.getDiscard();
        discardLabel.setText(rummy.toString(pile));
        userCardsLabel.setVisible(false);
        showImage.setVisible(false);
        showing = false;
        draw = false;
        discard = false;
        if(rummy.getStatus()){
            int scores[] = rummy.getScores();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game over!");
            alert.setHeaderText(null);
            alert.setContentText("Player 1: +"+scores[0]+" Player 2: "+scores[2]+" Player 3: "+scores[3]+" Player 4: "+scores[4]+"\n"+
            rummy.getMessage());
            alert.showAndWait();
        }

    }

    @FXML void deckClicked(){

        if(rummy.getStatus()){
            return;
        }
        if(draw){

            return;

        }
        rummy.removeTopDeck();
        userCardsLabel.setText(rummy.toString((rummy.getCurrentPlayer().getCards())));
        draw = true;

    }

    @FXML void discardClicked(){

        if(rummy.getStatus()){
            return;
        }
        if(discard){

            return;

        }
        TextInputDialog cardAmount = new TextInputDialog();
        cardAmount.setTitle("Discard Pile");
        cardAmount.setHeaderText("");
        cardAmount.setContentText("Enter the amount of cards to draw from discard pile");
        Optional<String> amount = cardAmount.showAndWait();
        if(!amount.isPresent())
            return;
        int x = Integer.parseInt(amount.get());
        rummy.takeDiscard(x);
        userCardsLabel.setText(rummy.toString((rummy.getCurrentPlayer())));
        discardLabel.setText(rummy.toString(rummy.getDiscard()));
        discard = true;

    }

    @FXML void playMeld(){

        if(rummy.getStatus()){
            return;
        }
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Play Meld");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField suit = new TextField();
        suit.setPromptText("Suit");
        TextField value = new TextField();
        value.setPromptText("Value");
        gridPane.add(new Label("Suit"),0,0);
        gridPane.add(suit, 1, 0);
        gridPane.add(new Label("Value:"), 2, 0);
        gridPane.add(value, 3, 0);

        dialog.getDialogPane().setContent(gridPane);
        ArrayList<Card> meld = new ArrayList<>();
        while(true) {
            Optional<Pair<String, String>> result = dialog.showAndWait();
            String s = suit.getText().toLowerCase().substring(0,1);
            String v = value.getText().toLowerCase();
            int s_int = 0;
            int v_int = 0;
            switch (s){

                case "s":
                    s_int = 0;
                    break;
                case "c":
                    s_int = 1;
                    break;
                case "h":
                    s_int = 2;
                    break;
                case "d":
                    s_int = 3;
                    break;

            }
            switch(v.substring(0,1)){

                case "j":
                    v_int = 11;
                    break;
                case "q":
                    v_int = 12;
                    break;
                case "k":
                    v_int = 13;
                    break;
                case "a":
                    v_int = 1;
                    break;
                default:
                    v_int = Integer.parseInt(v);
                    break;

            }
            meld.add(new Card(v_int,s_int));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Meld");
            alert.setHeaderText("");
            alert.setContentText("Add another card to meld?");
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.get() == ButtonType.CANCEL) {
                break;
            }
        }
        rummy.playMeld(meld);
        setMessageText(rummy.getMessage());
        if(rummy.getMessage() == "Meld played!"){
            playerIndex++;
            setTurnText(rummy.getTurnMessage());
        }
        else{
            setMessageText(rummy.getMessage());
        }
    }
}
