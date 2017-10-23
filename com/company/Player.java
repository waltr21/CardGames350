package com.company;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by RyanWalt on 10/5/17.
 */
public class Player {
    private ArrayList<Card> playerCards;
    //(GoFish) ArralyList to keep track of the users 4 of a kind.
    private int completeNum;
    private int score;

    public Player(){
        completeNum = 0;
        playerCards = new ArrayList<>();
    }

    public void giveCard(Card c){
        playerCards.add(c);
    }

    public void takeCard(Card c){
        playerCards.remove(c);
    }


    /******************************************
     * Removing a card from the player specifically
     * for GoFish. This is because the suit of the card
     * does not matter for removal in GoFish.
     * @param c A card to remove.
     * @return the card that was taken from the player.
     ******************************************/
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

    /******************************************
     * Counts how maany 4 of a kind cards the
     * player has in possession.
     ******************************************/
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

    public int getCompleteNum(){
        return completeNum;
    }

    public void sortCards(){
        flopSort(0, playerCards.size()-1);
    }


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

    public ArrayList<Card> getCards(){
        return playerCards;
    }

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

}
