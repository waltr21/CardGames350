package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Rummy {

    private Deck deck;
    private ArrayList<Player> players;
    private ArrayList<Card> discard;
    private int numCards;
    private int numPlayers;
    private int turn;
    private Scanner scanner;
    private boolean gameOver = false;
    private int scores[];

    public Rummy(int numPlayers){
        //Cap the number of players
        if (numPlayers > 4) {
            this.numPlayers = 4;
        }
        if(numPlayers == 2) {
            numCards = 10;
            this.numPlayers = numPlayers;
        }
        else {
            numCards = 7;
            this.numPlayers = numPlayers;
        }
        deck = new Deck(false);
        players = new ArrayList<>();
        discard = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }
        giveHand();
        scanner = new Scanner(System.in);
        turn = 0;
        scores = new int[numPlayers];
        discard.add(deck.removeTop());
        startGame();

    }

    private void startGame(){

        while(gameOver == false){


            if(turn >= numPlayers)
                turn = 0;
            System.out.println("Player "+(turn+1)+(", your move."));
            for(int i = 0; i < numPlayers; i++){

                if(players.get(i).cardCount() == 0)
                    gameOver = true;

            }
            players.get(turn).printCards();
            takeTurn(players.get(turn));
            turn++;

        }

    }

    public void giveHand(){

        for(int i = 0; i < numCards; i++){

            for(int j = 0; j < numPlayers; j++){

                players.get(j).giveCard(deck.removeTop());

            }

        }

    }

    public void takeTurn(Player player){

        boolean valid = false;
        int index = -1;
        while(!valid){

            System.out.println("The current discard pile: ");
            printDiscard();
            System.out.println("Draw card from deck or remove from discard pile?(p/d)");
            String c = scanner.nextLine();
            if(c.equals("p"))
                player.giveCard(discard.remove(discard.size()-1));
            else
                player.giveCard(deck.removeTop());
            break;

        }



    }
    private void tallyScore(){



    }

    private void printDiscard(){

        for(Card i : discard) System.out.println("Value: " + i.getValue() + " Suit: " + i.getSuit());

    }

    public static void main(String[] args){Rummy rummy = new Rummy(2);}

}
