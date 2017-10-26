package com.company;

import java.util.ArrayList;
import java.util.Scanner;

/******************************************************************************
 * The underlying code for a game of Rummy. Currently uses text based input
 * and output for the player.
 *
 * @author Tanner Wysocki
 *****************************************************************************/
public class Rummy {

    /** The game deck **/
    private Deck deck;

    /** ArrayList of players **/
    private ArrayList<Player> players;

    /** ArrayList to hold discarded cards **/
    private ArrayList<Card> discard;

    /** Two dimensional array to hold player melds. First index is the player
     *  Second is the cards in the meld **/
    private ArrayList<ArrayList<Card>> melds;

    /** The number of cards to be dealt **/
    private int numCards;

    /** The number of players in the game **/
    private int numPlayers;

    /** Integer for player's turn (0 - player 1, 1 - player 2, and so on) **/
    private int turn;

    /** Scanner for player input **/
    private Scanner in;

    /** Flag to check is game is still in progress **/
    private boolean gameOver = false;

    /** Array to hold scores **/
    private int scores[];

    /**************************************************************************
     * Constructor for the game.
     *
     * @param numPlayers The amount of players to play the game.
     *************************************************************************/
    public Rummy(int numPlayers){

        //Cap the number of players
        if (numPlayers > 4) {
            this.numPlayers = 4;
        }
        //Sets the amount of cards to be dealt to the players. If two, each get
        //ten. If three or four, each get seven cards.
        if(numPlayers == 2) {
            numCards = 10;
            this.numPlayers = numPlayers;
        }
        else {
            numCards = 7;
            this.numPlayers = numPlayers;
        }
        //Assigns each player an array to hold their melds. Set the size to over
        //100 to ensure the array won't go out of bounds.
        melds = new ArrayList<>();
        deck = new Deck(false);
        players = new ArrayList<>();
        discard = new ArrayList<>();
        //Adds all players the the game.
        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
            melds.add(new ArrayList<Card>());
        }
        //Deals the players their hands.
        giveHand();
        in = new Scanner(System.in);
        //Player 1's turn.
        turn = 0;
        //Holds the score at the end of the round.
        scores = new int[numPlayers];
        //Starts the discard pile with the top card of the deck.
        discard.add(deck.removeTop());
        startGame();

    }

    /**************************************************************************
     * Initializes the game and loops until a player has run out of cards.
     *************************************************************************/
    private void startGame(){

        //Loops until the round is over, which is when a player
        //depletes their hands.
        while(gameOver == false){

            //Returns to player 1 if turn exceeds the amount of
            //players.
            if(turn >= numPlayers)
                turn = 0;
            System.out.println("Player "+(turn+1)+(", your move."));

            players.get(turn).printCards();
            takeTurn(players.get(turn));
            turn++;
            //Checks for any empty hands.
            for(int i = 0; i < numPlayers; i++){

                if(players.get(i).cardCount() == 0) {
                    gameOver = true;
                    break;
                }
            }

        }
        System.out.println("Game over!");
        int winner = tallyScore();
        System.out.println("The final scores are: ");
        for (int i = 0; i < scores.length; i++){

            System.out.println("Player "+(i+1)+": "+scores[i]);

        }
        System.out.println("Player "+winner+", you have won the game!");

    }

    /**************************************************************************
     * Method to give players their initial hands at the start of the round.
     *************************************************************************/
    public void giveHand(){

        for(int i = 0; i < numCards; i++){

            for(int j = 0; j < numPlayers; j++){

                players.get(j).giveCard(deck.removeTop());

            }

        }

    }

    /**************************************************************************
     * Method to handle the user input for the player's turn
     *
     * @param player The player with the current turn.
     *************************************************************************/
    public void takeTurn(Player player){

        System.out.println("The current discard pile: ");
        printDiscard();
        System.out.println("Draw card from deck or remove from discard pile?((p)ile/(d)eck)");
        String c = in.nextLine();
        while(true){

            if (c.equals("q")) {
                gameOver = true;
                return;
            }

            if (c.equals("p")) {

                System.out.println("How many cards would you like?");
                int amount = in.nextInt();
                while(amount > discard.size() || amount == 0){

                    if(amount > discard.size())
                        System.out.println("This exceeds the current amount in the discard pile. Enter a valid amount.");
                    else
                        System.out.println("You must take at least one card. Enter a valid amount.");
                    amount = in.nextInt();

                }
                for(int i = 0; i < amount; i++)
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
            in.nextLine();
            option = in.nextLine();
            if(option.equals("Y") && option.equals("y") && option.equals("N") && option.equals("n")){
                while(true){

                    System.out.println("Invalid option. Y/N");

                    option = in.nextLine();
                    if(option.equals("Y") || option.equals("y") || option.equals("N") || option.equals("n")){

                        break;

                    }

                }
            }
            if(option.equals("N") || option.equals("n")) {

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
            Card tempMeld[] = new Card[player.cardCount()];
            int i = 0;
            int meldAmount = 1;
            while(true){

                System.out.println("Enter card value");
                int val = in.nextInt();
                in.nextLine();
                System.out.println("Enter card suit");
                int suit = in.nextInt();
                in.nextLine();
                Card temp = new Card(val,suit);
                while(!(player.checkCard(temp))){

                    temp = new Card(val, suit);
                    if(!(player.checkCard(temp))){

                        System.out.println("You don't have this card! Enter a valid value of a card:  ");
                        val = in.nextInt();
                        in.nextLine();
                        System.out.println("Enter a valid suit:  ");
                        suit = in.nextInt();
                        in.nextLine();

                    }

                }
                tempMeld[i] = temp;
                i++;
                meldAmount++;
                if(meldAmount == player.getCards().size()){
                    gameOver = true;
                    break;
                }
                System.out.println("Add more cards? Y/N");
                option = in.nextLine();
                if(option.equals("N") || option.equals("n")){
                    break;
                }

            }
            if(checkValidMeld(tempMeld)){

                for(int j = 0; j < tempMeld.length; j++){

                    melds.get(turn).add(tempMeld[j]);

                }
                int k = 0;
                while(tempMeld[k] != null){

                    Card tempCard = tempMeld[k];
                    player.takeCard(tempCard);
                    k++;

                }
                if(player.getCards().size() == 1){
                    discard.add(player.getCards().get(0));
                    player.getCards().remove(0);
                    return;
                }
                System.out.println("Enter the value of discard:  ");
                int discard_val = in.nextInt();
                in.nextLine();
                System.out.println("Enter the suit of discard:   ");
                int discard_suit = in.nextInt();
                in.nextLine();
                Card tempCard = new Card(discard_val,discard_suit);
                while(!(player.checkCard(tempCard))){

                    tempCard = new Card(discard_val, discard_suit);
                    if(!(player.checkCard(tempCard))){

                        System.out.println("You don't have this card! Enter a valid value of a card:  ");
                        discard_val = in.nextInt();
                        in.nextLine();
                        System.out.println("Enter a valid suit:  ");
                        discard_suit = in.nextInt();
                        in.nextLine();

                    }

                }
                discard.add(tempCard);
                player.takeCard(tempCard);
                break;

            }
            else{

                System.out.println("Invalid move. End turn? Y/N");
                if(in.nextLine().equals("Y") || in.nextLine().equals("y"))
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

    private int tallyScore(){

        int winner = 0;
        for(int i = 0; i < numPlayers; i++){

            System.out.println("Player: "+(turn+1));
            int j = 0;
            if(melds.get(i).size() != 0) {
                while (melds.get(i).get(j) != null) {

                    System.out.println(melds.get(i).get(j).toString());
                    if (melds.get(i).get(j).getValue() >= 1 && melds.get(i).get(j).getValue() < 10)
                        scores[i] += 5;
                    else
                        scores[i] += 10;
                    j++;

                }
            }
            System.out.println("Raw score: "+scores[i]);
            ArrayList<Card> tempHand = players.get(i).getCards();
            int penalty = 0;
            for(j = 0; j < players.get(i).cardCount(); j++){

                System.out.println(tempHand.get(j).getValue());
                if(tempHand.get(j).getValue() >= 1 && tempHand.get(j).getValue() < 10) {

                    scores[i] -= 5;
                    penalty-=5;

                }
                else {

                    scores[i] -= 10;
                    penalty-=10;

                }
            }
            System.out.println("Penalty: "+penalty);

        }
        for(int i = 1; i < scores.length; i++){

            if(scores[i] > scores[i-1])
                winner = i+1;

        }
        return winner+1;

    }

    private void printDiscard(){

        for(Card i : discard) System.out.println("Value: " + i.getValue() + " Suit: " + i.getSuit());

    }

    public static void main(String[] args){Rummy rummy = new Rummy(2);}

}
