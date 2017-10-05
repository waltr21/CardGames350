package com.company;

import java.util.ArrayList;

/**
 * Created by RyanWalt on 10/5/17.
 */
public class GoFish {
    BasicFunction b = new BasicFunction();
    ArrayList<Card> gameCards = new ArrayList<>();

    public GoFish(){
        gameCards = b.createDeck(false);

        gameCards = b.shuffle(gameCards);

        gameCards = b.createDeck(false);
        for (int i = 0; i < gameCards.size(); i ++){
            System.out.println("Number: " + gameCards.get(i).getValue() +
                    " Suit: " + gameCards.get(i).getSuit());
        }
    }

    public static void main(String args[]){
        GoFish g = new GoFish();
        System.out.println("test");
    }

}
