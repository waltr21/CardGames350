package com.company;

import java.util.ArrayList;

public class War {

    Player player1;
    Player player2;
    Deck deck;
    ArrayList<Card> table;

    public void setup () {
        player1 = new Player();
        player2 = new Player();
        deck = new Deck(false);
        table = new ArrayList<Card>();
    }

    public boolean over() {
        if (player1.cardCount() >= 52) {
            out("Player 1 wins!");
            return true;
        } else if (player2.cardCount() >= 52) {
            out("Player 2 wins!");
            return true;
        }
        return false;
    }

    public void deal() {
        deck.shuffle();
        while (deck.getSize() > 0) {
            player1.giveCard(deck.removeTop());
            player2.giveCard(deck.removeTop());
        }
    }

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