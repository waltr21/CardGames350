package com.company;

import java.util.ArrayList;

/**
 * Created by RyanWalt on 10/5/17.
 */
public class GoFish {
    Deck gameDeck;
    ArrayList<Player> players;

    public GoFish(int numPlayers){
        gameDeck = new Deck(false);
        players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }

        resetGame();
    }


    public void resetGame(){
        gameDeck.createDeck(false);
        gameDeck.shuffle();


        for (Player p : players){
            for (int i = 1; i <= 7; i++){
                p.giveCard(gameDeck.removeTop());
            }
        }
    }

    public static void main(String args[]){
        GoFish g = new GoFish(2);

    }

}
