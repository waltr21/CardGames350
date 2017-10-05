package com.company;

import java.util.ArrayList;

/**
 * Created by RyanWalt on 10/5/17.
 */
public class Player {
    ArrayList<Card> playerCards;

    public Player(){
        playerCards = new ArrayList<>();

    }



    public void giveCard(Card c){
        playerCards.add(c);
    }

    public void takeCard(Card c){
        playerCards.remove(c);
    }

    public ArrayList<Card> getCards(){
        return playerCards;
    }

}
