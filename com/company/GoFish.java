package com.company;

import java.util.ArrayList;

/**
 * Created by RyanWalt on 10/5/17.
 */
public class GoFish {
    Deck gameDeck;
    ArrayList<Player> players;

    public GoFish(int numPlayers){
        if (numPlayers > 4)
            numPlayers = 4;
        gameDeck = new Deck(false);
        players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }

        resetGame();
    }


    public void resetGame(){
        gameDeck.createDeck();
        gameDeck.shuffle();


        for (Player p : players){
            for (int i = 1; i <= 7; i++){
                p.giveCard(gameDeck.removeTop());
            }
        }

        for (Player x : players){
            x.printCards();
        }
    }

    public static void main(String args[]){
        GoFish g = new GoFish(33);

    }

}
