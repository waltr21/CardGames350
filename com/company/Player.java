package com.company;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by RyanWalt on 10/5/17.
 */
public class Player {
    //ArrayList of the cards the user is holding.
    private ArrayList<Card> playerCards;
    //(GoFish) int to keep track of the users 4 of a kind.
    private int completeNum;
    private int score;

    /**
     * Constructor for the player class. Sets the number of 4 of a kinds to 0
     * Creates the empty ArrayList for the Player.
     */
    public Player(){
        completeNum = 0;
        playerCards = new ArrayList<>();
    }

    /**
     * Gives a card to the player.
     * @param c Card to give the user.
     */
    public void giveCard(Card c){
        playerCards.add(c);
    }

    /**
     * Card to take from the user.
     * @param c Card to take.
     */
    public void takeCard(Card c){
        playerCards.remove(c);
    }


    /**
     * Removing a card from the player specifically
     * for GoFish. This is because the suit of the card
     * does not matter for removal in GoFish.
     * @param c A card to remove.
     * @return the card that was taken from the player.
     */
    public Card takeCardFish(Card c){
       for (int i = 0; i < playerCards.size(); i++){
           if (playerCards.get(i).getValue() == c.getValue()){
               Card temp = playerCards.get(i);
               playerCards.remove(i);
               return temp;
           }
       }
        return null;
    }

    /**
     * Counts how maany 4 of a kind cards the
     * player has in possession.
     */
    public void completeCount(){
        int[] cards = new int[13];
        for (int i = 0; i < cards.length; i++){
            cards[i] = 0;
        }

        for (int i = 0; i < playerCards.size(); i++){
            int index = playerCards.get(i).getValue() - 1;
            cards[index]++;
            if (cards[index] > 3){
                completeNum++;
            }
        }

    }

    /**
     * @return the number of four of a kinds in the users hand.
     */
    public int getCompleteNum(){
        return completeNum;
    }

    /**
     * Sorts the players cards.
     */
    public void sortCards(){
        flopSort(0, playerCards.size()-1);
    }

    /**
     * Recursive sorting algorithm used for sortCards()
     * @param s starting point for the algorithm
     * @param e end point for the algorithm.
     * @return ArrayList of sorted cards.
     */
    private ArrayList<Card> flopSort(int s, int e){
        int i = s;
        int j = e;
        boolean iMove = true;
        int pos = 0;

        if ((e - s) < 1)
            return playerCards;

        while(i < j){
            if (isGreater(playerCards.get(i), playerCards.get(j))){
                Collections.swap(playerCards, i, j);

                if(iMove)
                    iMove = false;
                else
                    iMove = true;
            }

            if(iMove)
                i++;

            if (!iMove)
                j--;

            pos = j;
        }

        playerCards = flopSort(pos, e);
        return flopSort(s, pos-1);
    }

    /**
     * Comparison method so we dont have to overload the
     * compare operators.
     * @param c1 First card to compare.
     * @param c2 Second card to compare.
     * @return Boolean value true if card 1 is greater than
     * card 2. False if less or equal (equal not possible
     * for our deck of cards).
     */
    private boolean isGreater(Card c1, Card c2){
        if (c1.getValue() > c2.getValue()){
            return true;
        }
        else if (c1.getValue() == c2.getValue()) {
            if (c1.getSuit() > c2.getSuit()) {
                return true;
            }
            return false;
        }
        else
            return false;
    }

    /**
     * @return ArrayList of cards the user is holding.
     */
    public ArrayList<Card> getCards(){
        return playerCards;
    }

    /**
     * Void method to print the cards the user is holding.
     */
    public void printCards(){
        for (Card c : playerCards){
            System.out.println("Value: " + c.getValue() + " Suit: " + c.getSuit());
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int value) {
        score = value;
    }
    public int cardCount(){

        return playerCards.size();

    }

}
