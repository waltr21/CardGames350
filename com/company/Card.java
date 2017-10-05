package com.company;

public class Card{

    private int suit;
    private int value;

    /* @param s - The suit of the card: 0 - Spades, 1 - Clubs,
     * 2 - Hearts, 3 - Diamonds.
     *
     * @param v - The number value of the card (Ace-King)
     */
    public Card(int v, int s){
        suit = s;
        value = v;
    }

    public int getSuit(){
        return suit;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int v){
        value = v;
    }

}
