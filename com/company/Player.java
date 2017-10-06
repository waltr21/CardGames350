package com.company;

import java.util.ArrayList;

/**
 * Created by RyanWalt on 10/5/17.
 */
public class Player {
    ArrayList<Card> playerCards;

    public Player(){
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

    public ArrayList<Card> getCards(){
        return playerCards;
    }

    public void printCards(){
        for (Card c : playerCards){
            System.out.println("Value: " + c.getValue() + " Suit: " + c.getSuit());
        }
    }

}
