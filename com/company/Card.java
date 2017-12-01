package com.company;


/**
 * Class to keep track of card values and suits.
 */
public class Card{
    private int suit;
    private int value;

    /**
     * Contructor function for the card class.
     * @param s - The suit of the card: 0 - Spades, 1 - Clubs,
     * 2 - Hearts, 3 - Diamonds.
     * @param v - The number value of the card (Ace-King)
     */
    public Card(int v, int s){
        suit = s;
        value = v;
    }

    /**
     * Gets the suit for the card.
     * @return suit value of the card.
     */
    public int getSuit(){
        return suit;
    }


    /**
     * Gets the value of the card.
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

    /**
     * Sets the suit of the card.
     * @param s Suit value.
     */
    public void setSuit(int s ){
        suit = s;
    }

    /**
     * Whether or not a card is equal to the current card.
     * @param c Card to compare to.
     * @return True if they are equal. False if else.
     */
    public boolean equals(Card c){
        return c.getValue() == value && c.getSuit() == suit;
    }

    /**
     * Makes a string for printing out the user cards.
     * @return Formatted string for the user to print.
     */
    public String toString() {
        String[] values = {"?", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"♠", "♣", "♥", "♦"};
        return String.format("%s of %s", values[value], suits[suit]);
    }

    public String imagePath() {
        String suitPath = "";
        String valuePath = "";
        switch (suit) {
            case 0:
                suitPath = "S";
                break;
            case 1:
                suitPath = "C";
                break;
            case 2:
                suitPath = "H";
                break;
            case 3:
                suitPath = "D";
                break;
            default:
                break;
        }
        //System.out.println(value);
        switch (value) {
            case 1:
                valuePath = "A";
                break;
            case 11:
                valuePath = "J";
                break;
            case 12:
                valuePath = "Q";
                break;
            case 13:
                valuePath = "K";
                break;
            default:
                valuePath = Integer.toString(value);
                break;
        }
        String mainPath = System.getProperty("user.dir");
        return mainPath + "\\Images\\png\\" + valuePath + suitPath + ".png";
    }


}
