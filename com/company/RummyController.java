package com.company;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


public class RummyController extends Application implements Initializable {
    private int playerIndex;
    private boolean showing,draw,discard;
    private Rummy rummy;
    @FXML
    private Label messageText, turnText, userCardsLabel, discardLabel;
    @FXML
    private ScrollPane discardPane, handPane;
    @FXML
    private ImageView cardImage1, cardImage2,
            cardImage3, cardImage4, cardDeck;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rummy = new Rummy(4);
        discardPane.setLayoutX(cardImage1.getLayoutX());
        discardPane.setLayoutY(cardImage1.getLayoutY());
        discardLabel.setLayoutX(discardPane.getLayoutX());
        discardLabel.setLayoutY(discardPane.getLayoutY());
        discardPane.setLayoutX(483);
        discardPane.setLayoutY(224);
        discardLabel.setText(rummy.getDiscard().toString());
        discardPane.setContent(discardLabel);
        discardLabel.setVisible(true);
        handPane.setVisible(false);
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
            handPane.setTranslateX(cardImage1.getLayoutX()-15);
            handPane.setTranslateY((cardImage1.getLayoutY()-15));
            handPane.setContent(userCardsLabel);
        }
        else if (rummy.getTurn() == 2){
            handPane.setTranslateX(cardImage2.getLayoutX()-15);
            handPane.setTranslateY(cardImage2.getLayoutY()-15);
            handPane.setContent(userCardsLabel);
        }
        else if (rummy.getTurn() == 3){
            handPane.setTranslateX(cardImage3.getLayoutX()-15);
            handPane.setTranslateY(cardImage3.getLayoutY()-15);
            handPane.setContent(userCardsLabel);
        }
        else if (rummy.getTurn() == 4){
            handPane.setTranslateX(cardImage4.getLayoutX()-15);
            handPane.setTranslateY(cardImage4.getLayoutY()-15);
            handPane.setContent(userCardsLabel);
        }

        if (!showing) {
            handPane.setVisible(true);
            userCardsLabel.setVisible(true);
            showing = true;
        }
        else{
            handPane.setVisible(false);
            userCardsLabel.setVisible(false);
            showing = false;
        }

    }

    @FXML public void onMeld1Action() {
        if (rummy.getMelds().get(0).size() > 0){
            userCardsLabel.setText(rummy.toString((rummy.getMelds().get(0))));
            handPane.setContent(userCardsLabel);
            handPane.setTranslateX(cardImage1.getLayoutX()-15);
            handPane.setTranslateY(cardImage1.getLayoutY()-15);
            if (!showing) {
                handPane.setVisible(true);
                userCardsLabel.setVisible(true);
                showing = true;
            } else {
                handPane.setVisible(false);
                userCardsLabel.setVisible(false);
                showing = false;
            }
        }
    }

    @FXML void onMeld2Action(){

        if (rummy.getMelds().get(1).size() > 1){
            userCardsLabel.setText(rummy.toString((rummy.getMelds().get(1))));
            handPane.setContent(userCardsLabel);
            handPane.setTranslateX(cardImage2.getLayoutX()-15);
            handPane.setTranslateY(cardImage2.getLayoutY()-15);
            if (!showing) {
                handPane.setVisible(true);
                userCardsLabel.setVisible(true);
                showing = true;
            } else {
                handPane.setVisible(false);
                userCardsLabel.setVisible(false);
                showing = false;
            }
        }

    }

    @FXML void onMeld3Action(){

        if (rummy.getMelds().get(2).size() > 2){
            userCardsLabel.setText(rummy.toString((rummy.getMelds().get(2))));
            handPane.setContent(userCardsLabel);
            handPane.setTranslateX(cardImage3.getLayoutX()-15);
            handPane.setTranslateY(cardImage3.getLayoutY()-15);
            if (!showing) {
                handPane.setVisible(true);
                userCardsLabel.setVisible(true);
                showing = true;
            } else {
                handPane.setVisible(false);
                userCardsLabel.setVisible(false);
                showing = false;
            }
        }


    }

    @FXML void onMeld4Action(){

        if (rummy.getMelds().get(3).size() > 3){
            userCardsLabel.setText(rummy.toString((rummy.getMelds().get(3))));
            handPane.setContent(userCardsLabel);
            handPane.setTranslateX(cardImage4.getLayoutX()-15);
            handPane.setTranslateY(cardImage4.getLayoutY()-15);
            if (!showing) {
                handPane.setVisible(true);
                userCardsLabel.setVisible(true);
                showing = true;
            } else {
                handPane.setVisible(false);
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

    @FXML public void onCardExitDeck(){cardDeck.setBlendMode(BlendMode.SRC_OVER);}

    @FXML
    private void setMessageText(String m){
        messageText.setText(m);
    }

    @FXML
    private void setTurnText(String m){
        turnText.setText(m);
    }

    @FXML public void onEndTurn(){

        if(rummy.getStatus()){
            return;
        }
        if(!draw && !discard){

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

        dialog.showAndWait();
        String s,v;
        try {
            s = suit.getText().toLowerCase().substring(0, 1);
            v = value.getText().toLowerCase();
        } catch (Exception e){

            s = "";
            v = "";
        }
        int s_int;
        int v_int;
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
            default:
                return;

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
                try {
                    v_int = Integer.parseInt(v);
                }
                catch (Exception e){
                    return;
                }
                if(v_int > 10 || v_int < 2){
                    return;
                }
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
        handPane.setVisible(false);
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

        if(rummy.getStatus() || draw){
            return;
        }
        rummy.removeTopDeck();
        userCardsLabel.setText(rummy.toString((rummy.getCurrentPlayer().getCards())));
        draw = true;

    }

    @FXML void discardClicked(){

        if(rummy.getStatus() || discard){
            return;
        }
        TextInputDialog cardAmount = new TextInputDialog();
        cardAmount.setTitle("Discard Pile");
        cardAmount.setHeaderText("");
        cardAmount.setContentText("Enter the amount of cards to draw from discard pile");
        Optional<String> amount = cardAmount.showAndWait();
        if(!amount.isPresent())
            return;
        int x;
        try{
            x = Integer.parseInt(amount.get());
        } catch (Exception e){
            return;
        }
        rummy.takeDiscard(x);
        userCardsLabel.setText(rummy.toString((rummy.getCurrentPlayer())));
        discardLabel.setText(rummy.toString(rummy.getDiscard()));
        discard = true;

    }

    //Source: https://stackoverflow.com/questions/31556373/javafx-dialog-with-2-input-fields
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
            dialog.showAndWait();
            String s,v;
            try {
                s = suit.getText().toLowerCase().substring(0, 1);
                v = value.getText().toLowerCase();
            }
            catch (Exception e){
                s = "";
                v = "";
            }
            int s_int;
            int v_int;
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
                default:
                    return;

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
                    try{
                        v_int = Integer.parseInt(v);
                    }
                    catch (Exception e){
                        return;
                    }
                    if(v_int > 10 || v_int < 2){
                        return;
                    }
                    break;

            }
            meld.add(new Card(v_int,s_int));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Meld");
            alert.setHeaderText("");
            alert.setContentText("Add another card to meld?");
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.isPresent() && (choice.get() == ButtonType.CANCEL)) {
                break;
            }
        }
        rummy.playMeld(meld);
        setMessageText(rummy.getMessage());
        if(Objects.equals(rummy.getMessage(), "Meld played!")){
            setTurnText(rummy.getTurnMessage());
        }
        else{
            setMessageText(rummy.getMessage());
        }
    }
}
