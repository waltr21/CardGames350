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
    public Label player1Message, player2Message, player1CardText, player2CardText, gameMessage1, gameMessage2, gameMessage3;

    @FXML public void onButtonAction(){
        if (game.over()) {
            String winner = (game.player1.cardCount() >= 52) ? "Player 1" : "Player 2";
            set(gameMessage1, String.format("%s wins!", winner));
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

        set(gameMessage1, "");
        set(gameMessage2, "");

        // Draw player cards.
        Card player1Card = game.player1.takeTopCard();
        Card player2Card = game.player2.takeTopCard();

        // Put them on the table.
        game.table.add(player1Card);
        game.table.add(player2Card);

        set(player1CardText, player1Card.toString());
        set(player2CardText, player2Card.toString());

        // Variables for each card.
        int card1val = player1Card.getValue();
        int card2val = player2Card.getValue();

        while (card1val == card2val) {
            set(gameMessage1, "WAR!");
            set(gameMessage2, "Each player places three additional cards on the table.");

            if (game.player1.cardCount() <= 4 || game.player2.cardCount() <= 4) {
                break;
            }

            for (int j = 0; j < 4; j++) {
                // Draw player cards.
                player1Card = game.player1.takeTopCard();
                player2Card = game.player1.takeTopCard();

                // Put them on the table.
                game.table.add(player1Card);
                game.table.add(player2Card);
            }

            card1val = player1Card.getValue();
            card2val = player2Card.getValue();
        }

        if (card1val > card2val) {
            set(gameMessage3, String.format("Player 1 takes the pile! %d cards!", game.table.size()));
            for (Card temp: game.table) {
                game.player1.giveCard(temp);
            }
        } else {
            set(gameMessage3, String.format("Player 2 takes the pile! %d cards!", game.table.size()));
            for (Card temp: game.table) {
                game.player2.giveCard(temp);
            }
        }

        game.table.clear();

        set(player1Message, String.format("Player 1's cards: %d", game.player1.cardCount()));
        set(player2Message, String.format("Player 2's cards: %d", game.player2.cardCount()));
    }
}