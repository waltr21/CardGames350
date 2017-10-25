package com.company;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WarController {

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLFiles/WarGUI.fxml"));
        Scene goFish = new Scene(root, 1000, 700);
        stage.setTitle("War!");
        stage.setScene(goFish);
        stage.show();
    }

    public static void main(String args[]) {
        WarController gameController = new WarController();
        War game = new War();
        game.setup();
        game.deal();
        while (!game.over()) {
            gameController.play(game);
        }
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

        out(String.format("Player 1's cards: %d", game.player1.cardCount()));
        out(String.format("Player 2's cards: %d", game.player2.cardCount()));
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
        pause();
    }

    private static void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

}