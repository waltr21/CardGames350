package com.company;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WarController extends Application implements Initializable {

    War game;

    @FXML
    public Label messageText, turnText, player1CardText, player2CardText;

    @FXML public void onButtonAction(){
        if (game.over()) {

        } else {
            play(game);
        }
    }

    public void set(Label label, String msg) {
        label.setText(msg);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        game = new War();
        game.setup();
        game.deal();
    }

    public WarController() {}

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("WarGUI.fxml"));
        Scene war = new Scene(root, 1000, 700);
        stage.setTitle("War!");
        stage.setScene(war);
        stage.show();
    }

    public void play(War game) {
        // Draw player cards.
        Card player1Card = game.player1.takeTopCard();
        out(String.format("Player 1 draws %s.", player1Card.toString()));
        Card player2Card = game.player2.takeTopCard();
        out(String.format("Player 2 draws %s.", player2Card.toString()));

        // Put them on the table.
        game.table.add(player1Card);
        game.table.add(player2Card);

        set(player1CardText, player1Card.toString());
        set(player2CardText, player2Card.toString());

        // Variables for each card.
        int card1val = player1Card.getValue();
        int card2val = player2Card.getValue();

        while (card1val == card2val) {
            out("WAR!");

            if (game.player1.cardCount() == 0 || game.player2.cardCount() == 0) {
                break;
            }

            // Draw player cards.
            player1Card = game.player1.takeTopCard();
            out(String.format("Player 1 draws %s.", player1Card.toString()));
            player2Card = game.player1.takeTopCard();
            out(String.format("Player 2 draws %s.", player2Card.toString()));

            // Put them on the table.
            game.table.add(player1Card);
            game.table.add(player2Card);

            card1val = player1Card.getValue();
            card2val = player2Card.getValue();
        }

        if (card1val > card2val) {
            out("Player 1 takes the pile!");
            for (Card temp: game.table) {
                game.player1.giveCard(temp);
            }
        } else {
            out("Player 2 takes the pile!");
            for (Card temp: game.table) {
                game.player2.giveCard(temp);
            }
        }

        game.table.clear();

        set(messageText, String.format("Player 1's cards: %d", game.player1.cardCount()));
        set(turnText, String.format("Player 2's cards: %d", game.player2.cardCount()));
    }


    /*public static void main(String args[]) {
        War game = new War();
        game.deal();
        while (true) {
            if (game.player1.cardCount() >= 52) {
                out("Player 1 wins!");
                break;
            }
            if (game.player2.cardCount() >= 52) {
                out("Player 2 wins!");
                break;
            }
            game.play();
        }
    }*/

    // HELPERS

    private void out(String msg) {
        System.out.println(msg);
        //set(messageText, msg);
    }

    private static void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

}