package com.company;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by RyanWalt on 10/5/17.
 */
public class BasicFunction {
    Random r = new Random();

    /*
     * Shuffles the deck of cards.
     * @Param the requested deck to shuffle.
     * @Return returns the fully shuffled deck the user.
     */
    public ArrayList<Card> shuffle(ArrayList<Card> a){
        ArrayList<Card> newCards = new ArrayList<>();
        int size = a.size();

        for (int i = 0; i < size; i++){
            int num = r.nextInt(a.size());
            newCards.add(a.get(num));
            a.remove(num);
        }

        return newCards;
    }

    /*
     * Selects one random card from the deck.
     * @Param The deck to take a random card from.
     * @Return returns the fully shuffled deck the user.
     */
    public Card getRandom(ArrayList<Card> a){
        int num = r.nextInt(a.size());
        return a.get(num);
    }


    /*
     * Selects one random card from the deck.
     * @Param A boolean to decide if the deck should contain jokers or not.
     * @Return returns the fully sorted deck.
     */
    public ArrayList<Card> createDeck(boolean joker){
        ArrayList<Card> deck = new ArrayList<>();
        for (int value = 1; value <=13 ; value++){
            for (int suit = 0; suit <= 3; suit++){
                deck.add(new Card(value, suit));
            }
        }


        //Because Jokers are wild we'll represent their suit and value as a -1 by defualt.
        if (joker){
            deck.add(new Card(-1, -1));
            deck.add(new Card(-1, -1));
        }

        return deck;
    }

    /*
     * Searches for one card in the deck.
     * @Param ArrayList<Card> - the deck of cards to search from.
     * @Param value - the number value of the card to find.
     * @Param suit - the suit of the card to find.
     * @Return returns the fully sorted deck.
     */
    public Card findCard(ArrayList<Card> a, int value, int suit){

        for (int i = 0; i < a.size(); i++){
            Card current = a.get(i);
            if (current.getSuit() == suit && current.getValue() ==  value){
                return current;
            }
        }

        //Handle if a card not found (not an error)
        System.out.println("Card not found!");
        return null;

    }




}
