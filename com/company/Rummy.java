package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Rummy {

    private Deck deck;
    private ArrayList<Card> discard;
    private ArrayList<Player> players;
    private int turn;
    private Scanner scan;
    private boolean gameOver;

    public Rummy(int players){

        deck = new Deck(false);
        discard = new ArrayList<>();
        //players = new ArrayList<>();
        for (int i = 0; i < players; i++){

        }


    }

    private void dealHands(int player_amount){

        int cardAmount;
        if(player_amount == 2)
            cardAmount = 10;
        else
            cardAmount = 7;
        for(int i = 0; i < cardAmount; i++){

            int j = 0;
            while(j < player_amount){

                players.get(j).giveCard(deck.removeTop());
                j++;

            }

        }

    }

    public static void main(String[] args){



    }

}
