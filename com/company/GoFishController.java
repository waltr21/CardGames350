package com.company;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Go Fish fxml file.
 * Connects all of the buttons and images to some
 * sort of action.
 */
public class GoFishController extends Application implements Initializable {
    private int playerIndex;
    public static int numPlayers;
    private boolean showing;
    private GoFish game;

    @FXML public Label messageText, turnText, userCardsLabel;
    @FXML public Button showButton, moveButton;
    @FXML public ChoiceBox valueChoice;
    @FXML public Rectangle showImage;
    @FXML public ImageView cardImage1,cardImage2,
            cardImage3, cardImage4;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        showing = false;
        resetChoiceBox();
        userCardsLabel.setLayoutX(cardImage1.getLayoutX());
        userCardsLabel.setLayoutY(cardImage1.getLayoutY());
        showImage.setLayoutX(cardImage1.getLayoutX());
        showImage.setLayoutY(cardImage1.getLayoutY());
        setTurnText("Player 1 it is your turn!");
        setMessageText("");
    }

    public GoFishController(){
        game = new GoFish(numPlayers);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GoFishGUI.fxml"));
        Scene goFish = new Scene(root, 1000, 700);
        stage.setTitle("Go Fish!");
        stage.setScene(goFish);
        stage.show();
    }

    /**
     * Handles the hide button action.
     */
    @FXML public void onButtonAction(){
        userCardsLabel.setText(game.getCardsString(game.getPlayer()));
        if (game.getPlayerIndex() == 1){
            showImage.setLayoutX(cardImage1.getLayoutX());
            showImage.setLayoutY(cardImage1.getLayoutY());
            userCardsLabel.setLayoutX(cardImage1.getLayoutX());
            userCardsLabel.setLayoutY(cardImage1.getLayoutY());
        }
        else if (game.getPlayerIndex() == 2){
            showImage.setLayoutX(cardImage2.getLayoutX());
            showImage.setLayoutY(cardImage2.getLayoutY());
            userCardsLabel.setLayoutX(cardImage2.getLayoutX());
            userCardsLabel.setLayoutY(cardImage2.getLayoutY());
        }
        else if (game.getPlayerIndex() == 3){
            showImage.setLayoutX(cardImage3.getLayoutX());
            showImage.setLayoutY(cardImage3.getLayoutY());
            userCardsLabel.setLayoutX(cardImage3.getLayoutX());
            userCardsLabel.setLayoutY(cardImage3.getLayoutY());
        }
        else if (game.getPlayerIndex() == 4){
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

    /**
     * Handles when card 1 is clicked.
     */
    @FXML public void onCardClicked1(){
        playerIndex = 1;
        setChoiceBoxPos(cardImage1.getLayoutX(), cardImage1.getLayoutY());
        valueChoice.setVisible(true);
    }

    /**
     * handles when card 2 is clicked.
     */
    @FXML public void onCardClicked2(){
        playerIndex = 2;
        setChoiceBoxPos(cardImage2.getLayoutX(), cardImage2.getLayoutY());
        valueChoice.setVisible(true);
    }

    /**
     * Handles when card 3 is clicked.
     */
    @FXML public void onCardClicked3(){
        playerIndex = 3;
        setChoiceBoxPos(cardImage3.getLayoutX(), cardImage3.getLayoutY());
        valueChoice.setVisible(true);
    }

    /**
     * Handles when card 4 is clicked.
     */
    @FXML public void onCardClicked4(){
        playerIndex = 4;
        setChoiceBoxPos(cardImage4.getLayoutX(), cardImage4.getLayoutY());
        valueChoice.setVisible(true);
    }

    /**
     * Updates the game message
     * @param m message to set fot the game.
     */
    @FXML public void setMessageText(String m){
        messageText.setText(m);
    }

    /**
     * Updates the turn text message.
     * @param m message to set for the turn.
     */
    @FXML public void setTurnText(String m){
        turnText.setText(m);
    }

    /**
     * Initializes the variables for the choice box.
     */
    @FXML public void resetChoiceBox(){
        valueChoice.setItems(FXCollections.observableArrayList(
                "Ace", "2", "3", "4", "5", "6", "7",
                "8", "9", "10", "Jack", "Queen", "King"));
        valueChoice.setVisible(false);
        valueChoice.setLayoutX(0.0);
        valueChoice.setLayoutY(0.0);
    }

    /**
     * Changes the position of the choicebox according to where
     * the cards are placed.
     * @param x Position X for the box.
     * @param y Position Y for the box.
     */
    @FXML public void setChoiceBoxPos(double x, double y){
        valueChoice.setLayoutX(x);
        valueChoice.setLayoutY(y);
        valueChoice.show();
    }

    /**
     * Handles how a move should be made when the user clicks the
     * move button.
     */
    @FXML public void onMoveClicked(){
        String x = valueChoice.getValue() + "";
        switch (x) {
            case "Ace":
                x = "1";
                break;
            case "Jack":
                x = "11";
                break;
            case "Queen":
                x = "12";
                break;
            case "King":
                x = "13";
                break;
        }

        int value = Integer.parseInt(x);
        System.out.println(x);
        playTurn(playerIndex, value);
        valueChoice.setValue(null);
        valueChoice.setVisible(false);
        showImage.setVisible(false);
        userCardsLabel.setVisible(false);
    }

    /**
     * Plays one turn for the Go Fish game.
     * @param playerIndex player to choose a card from.
     * @param value value of the card to take.
     */
    @FXML public void playTurn(int playerIndex, int value){
        boolean playerTurn;
        playerTurn = game.takeTurn(playerIndex, value);

        setMessageText(game.getMessage());
        if (playerTurn){
            setTurnText(game.getTurnMessage());
        }

        while (!playerTurn){
            //Temp String to store the bot turn string.
            String temp = game.getTurnMessage();

            //Delay for updating the bot thinking text
            Timeline timer = new Timeline(
                    new KeyFrame(Duration.seconds(4), event -> setBotThink(temp))
            );
            timer.play();

            playerTurn = game.takeBotTurn();

            //Delay for updating the bot turn text.
            Timeline timer1 = new Timeline(
                    new KeyFrame(Duration.seconds(6), event -> setBotText())
            );
            timer1.play();Las
            if (game.gameWon()){
                setNulls();
            }
        }
        if (game.gameWon()){
            setNulls();
        }

    }

    /**
     * Set all of the visuals to not be visible.
     */
    @FXML public void setNulls(){
        setTurnText("Game over! Winner is: " + game.getWinner());
        messageText.setVisible(false);
        cardImage1.setVisible(false);
        cardImage2.setVisible(false);
        cardImage3.setVisible(false);
        cardImage4.setVisible(false);
        moveButton.setVisible(false);
        showButton.setVisible(false);
        messageText.setVisible(false);
        valueChoice.setVisible(false);
        userCardsLabel.setVisible(false);
        showImage.setVisible(false);
    }

    /**
     * Updates the bot result text
     */
    public void setBotText(){
        setTurnText(game.getTurnMessage());
        setMessageText(game.getMessage());
    }

    /**
     * Updates the bot thinking text/animation
     * @param s Turn string from the game.
     */
    public void setBotThink(String s){
        setTurnText(s);
        setMessageText("Bot thinking ");
    }
}