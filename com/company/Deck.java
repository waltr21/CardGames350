package com.company;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by RyanWalt on 10/5/17.
 * Class to hold all of the cards in a deck that are not
 * in the users hands.
 */
public class Deck {
    private final Random r;
    private ArrayList<Card> myCards;
    private final boolean joker;

    /**
     * Constructor for the Deck class.
     * @param j boolean value on whether or not the deck should include jokers.
     */
    public Deck(boolean j){
        myCards = new ArrayList<>();
        r = new Random();
        joker = j;
        createDeck();
    }

    /**
     * Creates a new standard deck.
     */
    public void createDeck(){
        ArrayList<Card> tempDeck = new ArrayList<>();
        for (int value = 1; value <=13 ; value++){
            for (int suit = 0; suit <= 3; suit++){
                tempDeck.add(new Card(value, suit));
            }
        }

        //Because Jokers are wild we'll represent their suit and value as a -1 by default.
        if (joker){
            tempDeck.add(new Card(-1, -1));
            tempDeck.add(new Card(-1, -1));
        }

        myCards = tempDeck;
    }

    /**
     * Shuffles the deck of cards.
     */
    public void shuffle(){
        ArrayList<Card> newCards = new ArrayList<>();
        int size = myCards.size();

        for (int i = 0; i < size; i++){
            int num = r.nextInt(myCards.size());
            newCards.add(myCards.get(num));
            myCards.remove(num);
        }

        myCards = newCards;
    }

    public Card removeTop(){
        Card temp = myCards.get(0);
        myCards.remove(0);
        return temp;
    }

    /**
     * @return the size of the current deck
     */
    public int getSize(){
        return myCards.size();
    }


}
