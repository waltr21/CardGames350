package com.company;

public class Card{

    private int suit;
    private int value;


    public String toString() {
        String[] values = {"?", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"♠", "♣", "♥", "♦"};
        return String.format("%s of %s", values[value], suits[suit]);
    }

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
     * or jokers have specific values.)
     * @param v value to override with.
     */
    public void setValue(int v){
        value = v;
    }

    public void setSuit(int s ){
        suit = s;
    }

    public boolean equals(Card c){
        return c.getValue() == value && c.getSuit() == suit;
    }

}
