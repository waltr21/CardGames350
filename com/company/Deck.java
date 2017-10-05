package com.company;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by RyanWalt on 10/5/17.
 */
public class Deck {
    Random r = new Random();
    ArrayList<Card> myCards;

    public Deck(boolean joker){
        myCards = new ArrayList<>();
        createDeck(joker);
    }

    /*
   * @Return returns the deck of cards.
   */
    public ArrayList<Card> getDeck(){
        return myCards;
    }

    /*
     * Selects one random card from the deck.
     * @Param A boolean to decide if the deck should contain jokers or not.
     * @Return returns the fully sorted deck.
     */
    public void createDeck(boolean joker){
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



    /*
     * Shuffles the deck of cards.
     * @Param the requested deck to shuffle.
     * @Return returns the fully shuffled deck the user.
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

    /*
     * Selects one random card from the deck and removes it.
     * @Param The deck to take a random card from.
     * @Return Random card object from the deck.
     */
    public Card getRandom(){
        int num = r.nextInt(myCards.size());
        myCards.remove(myCards.get(num));
        return myCards.get(num);
    }

    public Card removeTop(){
        Card temp = myCards.get(0);
        myCards.remove(0);
        return temp;
    }



    /*
     * Searches for one card in the deck.
     * @Param ArrayList<Card> - the deck of cards to search from.
     * @Param value - the number value of the card to find.
     * @Param suit - the suit of the card to find.
     * @Return returns the fully sorted deck.
     */
    public Card findCard(ArrayList<Card> a, Card c, boolean take){
        for (int i = 0; i < a.size(); i++){
            if (a.get(i) == c ){
                if (take){
                    a.remove(c);
                    return a.get(i);
                }
                else
                    return a.get(i);
            }
        }

        return null;
    }


}
