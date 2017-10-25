package com.company;

public class Card{

    private int suit;
    private int value;

    /**
     * @param s - The suit of the card: 0 - Spades, 1 - Clubs,
     * 2 - Hearts, 3 - Diamonds.
     * @param v - The number value of the card (Ace-King)
     */

    public String toString() {
        String[] values = {"?", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"♠", "♣", "♥", "♦"};
        return String.format("%s of %s", values[value], suits[suit]);
    }


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

    public boolean equals(Card c){

        if(c.getValue() == value && c.getSuit() == suit)
            return true;
        return false;

    }

}
