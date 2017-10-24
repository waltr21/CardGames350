package com.company;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by RyanWalt on 10/5/17.
 */
public class Deck {
    private Random r;
    private ArrayList<Card> myCards;
    private boolean joker;

    /**
     * Construcor for the Deck class.
     * @param j boolean value on whether or not the deck should include jokers.
     */
    public Deck(boolean j){
        myCards = new ArrayList<>();
        r = new Random();
        joker = j;
        createDeck();
    }

    /**
    * @Return returns the deck of cards.
    */
    public ArrayList<Card> getDeck(){
        return myCards;
    }

    /**
     * Selects one random card from the deck.
     * @Param A boolean to decide if the deck should contain jokers or not.
     * @Return returns the fully sorted deck.
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

    /**
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

    /**
     * @return the size of the current deck
     */
    public int getSize(){
        return myCards.size();
    }

    /**
     *
     * @param a Arraylist of cards to search through.
     * @param c Card to search for.
     * @param take true if the user wants to remove the card from the deck.
     *             else it will stay.
     * @return the card found in the deck. Null if nothing is found.
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

    /**
     * Prints all of the cards left in the deck.
     */
    public void printCards(){
        for (Card c : myCards){
            System.out.println("Value: " + c.getValue() + " Suit: " + c.getSuit());
        }
    }


}
