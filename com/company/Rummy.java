package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Rummy {

    private Deck deck;
    private ArrayList<Player> players;
    private ArrayList<Card> discard;
    private Card melds[][];
    private int numCards;
    private int numPlayers;
    private int turn;
    private Scanner in;
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
        melds = new Card[numPlayers][100];
        deck = new Deck(false);
        players = new ArrayList<>();
        discard = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }
        giveHand();
        in = new Scanner(System.in);
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

        System.out.println("The current discard pile: ");
        printDiscard();
        System.out.println("Draw card from deck or remove from discard pile?((p)ile/(d)eck)");
        String c = in.nextLine();
        while(true){

            if (c.equals("p")) {

                player.giveCard(discard.remove(discard.size() - 1));
                break;

            }
            else if(c.equals("d")) {

                player.giveCard(deck.removeTop());
                break;

            }
            System.out.println("Invalid choice. Please type p for pile or d for deck:  ");
            c = in.nextLine();

        }
        player.sortCards();
        player.printCards();
        while(true){

            String option;
            System.out.println("Would you like to play a meld (Y/N)? If none available, type N:  ");
            option = in.nextLine();
            if(option.equals("N") || option.equals("n"))
                break;
            Card tempMeld[] = new Card[player.cardCount()];
            int i = 0;
            while(true){

                System.out.println("Enter card value");
                int val = in.nextInt();
                in.nextLine();
                System.out.println("Enter card suit");
                int suit = in.nextInt();
                in.nextLine();
                Card temp = new Card(val, suit);
                tempMeld[i] = temp;
                i++;
                System.out.println("Add more cards? Y/N");
                option = in.nextLine();
                if(option.equals("N") || option.equals("n")){
                    break;
                }

            }
            int j = 0;
            if(checkValidMeld(tempMeld) == true){

                while(melds[turn][j] != null){

                    j++;

                }
                for(int k = 0; k < melds.length; k++){

                    melds[turn][j] = tempMeld[k];

                }
                for(int k = 0; k < tempMeld.length; k++){

                    Card tempCard = tempMeld[k];
                    player.takeCard(tempCard);

                }
                System.out.println("Enter the value of discard:  ");
                int discard_val = in.nextInt();
                in.nextLine();
                System.out.println("Enter the suit of discard:   ");
                int discard_suit = in.nextInt();
                in.nextLine();
                Card tempCard = new Card(discard_val,discard_suit);
                discard.add(tempCard);
                player.takeCard(tempCard);
                break;

            }
            else{

                System.out.println("Invalid move. End turn? Y/N");
                if(in.nextLine().equals("Y"))
                    break;

            }

        }

    }

    private boolean checkValidMeld(Card meld[]){

        if(meld.length < 3)
            return false;
        boolean valid = false;
        int i = 1;
        while(meld[i] != null){

            if(meld[i].getValue() == (meld[i-1].getValue()+1) && meld[i].getSuit() == meld[i-1].getSuit()){
                valid = true;
            }
            else if(meld[i].getValue() == meld[i-1].getValue()){
                valid = true;
            }
            else
                valid = false;
            i++;

        }
        return valid;

    }

    private void tallyScore(){



    }

    private void printDiscard(){

        for(Card i : discard) System.out.println("Value: " + i.getValue() + " Suit: " + i.getSuit());

    }

    public static void main(String[] args){Rummy rummy = new Rummy(2);}

}
