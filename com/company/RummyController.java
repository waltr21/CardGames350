package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class RummyController extends Application implements Initializable {
    private int playerIndex;
    private boolean showing,draw,discard;
    private Rummy rummy;
    @FXML
    public Label messageText, turnText, userCardsLabel;
    @FXML
    public Button showButton, moveButton;
    @FXML
    public ChoiceBox valueChoice;
    @FXML
    public Rectangle showImage;
    @FXML
    public ImageView cardImage1, cardImage2,
            cardImage3, cardImage4, cardDeck, cardDiscard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rummy = new Rummy(4);
        resetChoiceBox();
        userCardsLabel.setLayoutX(cardImage1.getLayoutX());
        userCardsLabel.setLayoutY(cardImage1.getLayoutY());
        showImage.setLayoutX(cardImage1.getLayoutX());
        showImage.setLayoutY(cardImage1.getLayoutY());
        turnText.setText("Player "+ (rummy.getTurn()+1)+", it's your turn.");
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
            showImage.setLayoutX(cardImage1.getLayoutX()/2);
            showImage.setLayoutY(cardImage1.getLayoutY()/2);
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
            showButton.setText("Hide cards");
            showImage.setVisible(true);
            userCardsLabel.setVisible(true);
            showing = true;
        }
        else{
            showButton.setText("Show cards");
            showImage.setVisible(false);
            userCardsLabel.setVisible(false);
            showing = false;
        }
    }

    @FXML public void onCardDrag1(){
        if(playerIndex == 0){
            return;
        }
        cardImage1.setBlendMode(BlendMode.LIGHTEN);
    }

    @FXML public void onCardDrag2(){
        cardImage2.setBlendMode(BlendMode.LIGHTEN);
    }

    @FXML public void onCardDrag3(){
        cardImage3.setBlendMode(BlendMode.LIGHTEN);
    }

    @FXML public void onCardDrag4(){
        cardImage4.setBlendMode(BlendMode.LIGHTEN);
    }

    @FXML public void onCardDragDeck(){
        if(draw)
            return;
        cardDeck.setBlendMode(BlendMode.LIGHTEN);
    }

    @FXML public void onCardDragDiscard(){cardDiscard.setBlendMode(BlendMode.LIGHTEN);}

    @FXML public void onCardExit1(){
        cardImage1.setBlendMode(BlendMode.SRC_OVER);
    }

    @FXML public void onCardExit2(){
        cardImage2.setBlendMode(BlendMode.SRC_OVER);
    }

    @FXML public void onCardExit3(){
        cardImage3.setBlendMode(BlendMode.SRC_OVER);
    }

    @FXML public void onCardExit4(){
        cardImage4.setBlendMode(BlendMode.SRC_OVER);
    }

    @FXML public void onCardExitDeck(){cardDeck.setBlendMode(BlendMode.SRC_OVER);}

    @FXML public void onCardExitDiscard(){cardDeck.setBlendMode(BlendMode.SRC_OVER);}

    @FXML public void onCardClicked1(){
        if(rummy.getTurn() == 0){
            return;
        }
        System.out.println("Card 1 clicked!");
        playerIndex = 1;
        setChoiceBoxPos(cardImage1.getLayoutX(), cardImage1.getLayoutY());
        valueChoice.setVisible(true);
    }

    @FXML public void onCardClicked2(){
        if(rummy.getTurn() == 1){
            return;
        }
        System.out.println("Card 2 clicked!");
        playerIndex = 2;
        setChoiceBoxPos(cardImage2.getLayoutX(), cardImage2.getLayoutY());
        valueChoice.setVisible(true);
    }

    @FXML public void onCardClicked3(){
        if(rummy.getTurn() == 2){
            return;
        }
        System.out.println("Card 3 clicked!");
        playerIndex = 3;
        setChoiceBoxPos(cardImage3.getLayoutX(), cardImage3.getLayoutY());
        valueChoice.setVisible(true);
    }

    @FXML public void onCardClicked4(){
        if(rummy.getTurn() == 3){
            return;
        }
        System.out.println("Card 4 clicked!");
        playerIndex = 4;
        setChoiceBoxPos(cardImage4.getLayoutX(), cardImage4.getLayoutY());
        valueChoice.setVisible(true);
    }

    @FXML public void setMessageText(String m){
        messageText.setText(m);
    }


    @FXML public void setTurnText(String m){
        turnText.setText(m);
    }

   @FXML public void resetChoiceBox(){
        valueChoice.setItems(FXCollections.observableArrayList(
                "Ace", "2", "3", "4", "5", "6", "7",
                "8", "9", "10", "Jack", "Queen", "King"));
        valueChoice.setVisible(false);
        valueChoice.setLayoutX(0.0);
        valueChoice.setLayoutY(0.0);
    }


    @FXML public void setChoiceBoxPos(double x, double y){
        valueChoice.setLayoutX(x);
        valueChoice.setLayoutY(y);
        valueChoice.show();
    }


    @FXML public void onEndTurn(){

        playerIndex+=1;
        if(playerIndex > 3)
            playerIndex = 0;
        messageText.setText(rummy.getMessage());
        turnText.setText(rummy.getTurnMessage());

    }

    @FXML void playTurn(String move,int pile){
        rummy.takeTurn(rummy.getCurrentPlayer(),move,pile);
        setMessageText(rummy.getMessage());
        setTurnText(rummy.getTurnMessage());
       // System.out.println(game.getMessage());
       // System.out.println(game.getCardsString(currentPlayer));
    }

    @FXML void deckClicked(){

        if(draw){

            return;

        }
        playTurn("d",0);
        draw = true;

    }

    @FXML void discardClicked(){

        if(discard){

            return;

        }
        TextInputDialog cardAmount = new TextInputDialog("0");
        cardAmount.setTitle("Discard Pile");
        cardAmount.setContentText("Enter the amount of cards to draw from discard pile");
        Optional<String> amount = cardAmount.showAndWait();
        if(!amount.isPresent())
            return;
        int x = Integer.parseInt(amount.get());
        playTurn("p",x);
        discard = true;

    }

}
