package com.company;

import java.util.ArrayList;

public class War {

    Player player1;
    Player player2;
    Deck deck;
    ArrayList<Card> table;


    public War () {
        player1 = new Player();
        player2 = new Player();
        deck = new Deck(false);
        table = new ArrayList<Card>();
    }

    public static void main(String args[]) {
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
    }

    private void deal() {
        deck.shuffle();
        while (deck.getSize() > 0) {
            player1.giveCard(deck.removeTop());
            player2.giveCard(deck.removeTop());
        }
    }

    private void play() {
        // Draw player cards.
        Card player1Card = player1.takeTopCard();
        out(String.format("Player 1 draws %s.", player1Card.toString()));
        Card player2Card = player2.takeTopCard();
        out(String.format("Player 2 draws %s.", player2Card.toString()));

        // Put them on the table.
        table.add(player1Card);
        table.add(player2Card);

        // Variables for each card.
        int card1val = player1Card.getValue();
        int card2val = player2Card.getValue();

        while (card1val == card2val) {
            out("WAR!");

            if (player1.cardCount() == 0 || player2.cardCount() == 0) {
                break;
            }

            // Draw player cards.
            player1Card = player1.takeTopCard();
            out(String.format("Player 1 draws %s.", player1Card.toString()));
            player2Card = player1.takeTopCard();
            out(String.format("Player 2 draws %s.", player2Card.toString()));

            // Put them on the table.
            table.add(player1Card);
            table.add(player2Card);

            card1val = player1Card.getValue();
            card2val = player2Card.getValue();
        }

        if (card1val > card2val) {
            out("Player 1 takes the pile!");
            for (Card temp: table) {
                player1.giveCard(temp);
            }
        } else {
            out("Player 2 takes the pile!");
            for (Card temp: table) {
                player2.giveCard(temp);
            }
        }

        table.clear();

        out(String.format("Player 1's cards: %d", player1.cardCount()));
        out(String.format("Player 2's cards: %d", player2.cardCount()));
    }


    // HELPERS

    private static void out(String msg) {
        System.out.println(msg);
    }

}
