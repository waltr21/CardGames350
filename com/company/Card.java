package com.company;

public class Card{

    private int suit;
    private int value;

    /**
     * @param s - The suit of the card: 0 - Spades, 1 - Clubs,
     * 2 - Hearts, 3 - Diamonds.
     * @param v - The number value of the card (Ace-King)
     */
    public Card(int v, int s){
        suit = s;
        value = v;
    }

    /**
     * @return suit value of the card.
     */
    public int getSuit(){
        return suit;
    }


    /**
     * @return the value of the card.
     */
    public int getValue(){
        return value;
    }

    /**
     * Overrides the value of a card. (used if ace is high,
     * or jokers have specific valeus.)
     * @param v value to override with.
     */
    public void setValue(int v){
        value = v;
    }

}
