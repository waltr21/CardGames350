package com.company;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by RyanWalt on 10/5/17.
 */
public class GoFish {
    private Deck gameDeck;
    private ArrayList<Player> players;
    private int numCards;
    private int turn;
    //private Console cons;
    Scanner cons = new Scanner(System.in);

    public GoFish(int numPlayers){
        //Cap the number of players
        if (numPlayers > 4)
            numPlayers = 4;

        //Set initial card amount.
        if(numPlayers > 2)
            numCards = 5;
        else
            numCards = 7;

        turn = 0;
        //cons = System.console();
        gameDeck = new Deck(false);
        players = new ArrayList<>();

        //Add players to the game.
        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }

        resetGame();
        startGame();
    }

    public void startGame(){
        while (gameDeck.getSize() > 0) {
            System.out.println("Player " + (turn + 1) + " it is your turn!");
            takeTurn(players.get(turn));
            System.out.println("");
        }
    }

    public void takeTurn(Player p){
        boolean validMove = false;
        int index = -1;
        while (!validMove) {
            System.out.print("Player to request a card from: ");
            String indexS = cons.nextLine();
            index = Integer.parseInt(indexS) - 1;

            if (index >= players.size() || index < 0 || index == turn){
                System.out.println("That wasn't a valid player! Try again...");
            }
            else if (players.get(index).getCards().size() < 1){
                System.out.println("This player is out of cards!");
            }

            else {
                System.out.print("Value of card to request: ");
                String requestValue = cons.nextLine();
                int tempVal = Integer.parseInt(requestValue);

                Card tempCard = new Card(tempVal, -1);

                if (!checkValid(p, tempCard)) {
                    System.out.println("You dont have this card!");
                } else if (!checkValid(players.get(index), tempCard)) {
                    System.out.println("Go Fish!");
                    Card fish = gameDeck.removeTop();
                    players.get(turn).giveCard(fish);
                    System.out.println("Card got: Value: " + fish.getValue() + " Suit: " + fish.getSuit());
                    if (fish.getValue() == tempVal) {
                        index = turn;
                        System.out.println("You got the card you wanted. The turn continues.");
                    }
                    validMove = true;
                } else {
                    System.out.println("The player has this card!");
                    Card transfer = players.get(index).takeCardFish(tempCard);
                    players.get(turn).giveCard(transfer);
                    validMove = true;
                }
            }
        }
        printAll();
        turn = index;
    }

    public boolean checkValid(Player p, Card c){
        ArrayList<Card> temp;
        temp = p.getCards();

        for (Card c1 : temp){
            if (sameValue(c, c1))
                return true;
        }

        return false;
    }


    public void resetGame(){
        gameDeck.createDeck();
        gameDeck.shuffle();
        turn = 0;

        //Give each player X cards and remove them from the deck.
        for (int i = 1; i <= numCards; i++){
            for (Player p : players) {
                p.giveCard(gameDeck.removeTop());
            }
        }

        printAll();
    }

    /******************************************
     * Return true if the cards are the same value.
     * @param c A card to compare
     * @param c1 A card to compare
     * @return True or false depending on if the cards hold the
     * same value.
     ******************************************/
    public boolean sameValue(Card c, Card c1){
        if (c.getValue() == c1.getValue())
            return true;
        else
            return false;
    }

    private void printAll(){
        int test = 1;

        for (Player x : players){
            x.sortCards();
            System.out.println("Player " + test + ":");
            x.printCards();
            test++;
            System.out.println("");
        }
    }

    public static void main(String args[]){
        GoFish g = new GoFish(2);
    }

}
