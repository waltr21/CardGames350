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

    private String message;

    /** Array to hold scores **/
    private int scores[];

    /**************************************************************************
     * Constructor for the game.
     *
     * @param numPlayers The amount of players to play the game.
     *************************************************************************/
    public Rummy(int numPlayers){

        if(numPlayers > 4){

            System.out.println("The game only allows for up to 4 players. Defaulting to 4.");
            numPlayers = 4;

        }
        if(numPlayers < 2){

            System.out.println("The game must have at least 2 players. Defaulting to 2.");
            numPlayers = 2;

        }
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
        deck.shuffle();
        //Deals the players their hands.
        giveHand();
        in = new Scanner(System.in);
        //Player 1's turn.
        turn = 0;
        //Holds the score at the end of the round.
        scores = new int[numPlayers];
        //Starts the discard pile with the top card of the deck.
        discard.add(deck.removeTop());
        message = "Game initialized.";
        //startGame();

    }

    /**************************************************************************
     * Initializes the game and loops until a player has run out of cards.
     *************************************************************************/
   /* private void startGame(){

        //Loops until the round is over, which is when a player
        //depletes their hands.
        while(gameOver == false){

            //Returns to player 1 if turn exceeds the amount of
            //players.
            if(turn >= numPlayers)
                turn = 0;
            System.out.println(getTurnMessage());
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
        message = "Game over!";
        int winner = tallyScore();
        System.out.println("The final scores are: ");
        for (int i = 0; i < scores.length; i++){

            System.out.println("Player "+(i+1)+": "+scores[i]);

        }
        System.out.println("Player "+winner+", you have won the game!");

    }*/

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

    public void takeDiscard(int amount){

        for(int i = 0; i < amount; i++) {

            players.get(turn).giveCard(discard.remove(discard.size()-1));

        }
    }

    public void removeTopDeck(){

        players.get(turn).giveCard(deck.removeTop());

    }

    public void discard(Card c){

        players.get(turn).takeCard(c);
        discard.add(c);
        turn++;
        message = "Player "+(turn+1)+" discarded";
        if(players.get(turn).getCards().size() == 0){
            gameOver = true;
            tallyScore();
        }

    }

    public void playMeld(ArrayList<Card> meld){

        if(checkValidMeld(meld)){

            while(meld.size() != 0){

                Card temp = meld.remove(meld.size()-1);
                players.get(turn).getCards().remove(temp);
                melds.get(turn).add(temp);

            }
            message = "Meld played!";
            turn++;
        }
        else{
            message = "Invalid meld.";
        }
        if(players.get(turn).getCards().size() == 0){
            gameOver = true;
            tallyScore();
        }

    }

    private boolean checkValidMeld(ArrayList<Card> meld){

        if(meld.size() < 3)
            return false;
        boolean valid = false;
        int i = 1;
        while(i < meld.size()){

            if(meld.get(i).getValue() == (meld.get(i-1).getValue()+1) && meld.get(i).getSuit() == meld.get(i-1).getSuit()){
                valid = true;
            }
            else if(meld.get(i).getValue() == meld.get(i-1).getValue()){
                valid = true;
            }
            else
                valid = false;
            i++;

        }
        return valid;

    }

    /**************************************************************************
     * Method to calculate the final scores for each player at the end of the
     * round. Adds up each card in the meld and deducts each remaining card
     * from the player's hand.
     *
     * @return  Int representing the winning player.
     *************************************************************************/
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
        message = "Winner: Player "+(winner+1)+"!";
        return winner+1;

    }

    /**************************************************************************
     * Helper method to print all cards in the discard pile.
     *************************************************************************/
    private void printDiscard(){

        for(Card i : discard) System.out.println("Value: " + i.getValue() + " Suit: " + i.getSuit());
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getCards().size() == 0){
                message = "Game over!";
                gameOver = true;
            }
        }

    }

    /***************************************************************************
     * Getter for the game's deck.
     *
     * @return The game's current deck
     **************************************************************************/
    public Deck getDeck() {
        return deck;
    }

    /**************************************************************************
     * Setter for the game's deck
     *
     * @param deck The deck to replace the game's current deck
     *************************************************************************/
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    /*************************************************************************
     * Getter for the game's players
     *
     * @return ArrayList holding each player object.
     ************************************************************************/
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /*************************************************************************
     * Getter for the discard pile
     *
     * @return The current discard pile
     ************************************************************************/
    public ArrayList<Card> getDiscard() {
        return discard;
    }

    /**************************************************************************
     * Getter for the melds for each player
     *
     * @return An ArrayList of size equaling the amount of player with each
     * element holding an ArrayList representing each card in a meld.
     *************************************************************************/
    public ArrayList<ArrayList<Card>> getMelds() {
        return melds;
    }

    /**************************************************************************
     * Getter for the number of cards to deal out.
     *
     * @return The number of cards to deal out.
     *************************************************************************/
    public int getNumCards() {
        return numCards;
    }

    /**************************************************************************
     * Getter for the number of players in the game
     *
     * @return The number of players in the game.
     *************************************************************************/
    public int getNumPlayers() {
        return numPlayers;
    }

    /**************************************************************************
     * Setter for the number of players in the game
     *
     * @param numPlayers The number of players to play the game.
     *************************************************************************/
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    /**************************************************************************
     * Getter for the current turn.
     *
     * @return Int representing the current player's turn
     *************************************************************************/
    public int getTurn() {
        return turn+1;
    }

    /**************************************************************************
     * Checks if game is over
     *
     * @return True if game is over, false if game is ongoing.
     *************************************************************************/
    public boolean isGameOver() {
        return gameOver;
    }

    /**************************************************************************
     * Sets the state of the game
     *
     * @param gameOver Sets to true if game is over or false if game is to
     * start.
     *************************************************************************/
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**************************************************************************
     * Gets the scores for each of the players
     *
     * @return Array holding scores for each player.
     *************************************************************************/
    public int[] getScores() {
        return scores;
    }

    public String toString(Player p){

        ArrayList<Card> temp;
        String cardList = "";
        temp = p.getCards();
        for (Card aTemp : temp) {
            cardList += aTemp.toString() + "\n";
        }
        return cardList;

    }

    public String toString(ArrayList<Card> hand){

        String cardList = "";
        for (Card aTemp : hand) {
            cardList += aTemp.toString() + "\n";
        }
        return cardList;

    }

    public Player getCurrentPlayer(){

        return players.get(turn);

    }

    public String getTurnMessage(){

        return "Player "+(turn+1)+", it's your turn.";

    }

    public boolean getStatus(){

        return gameOver;

    }

    public String getMessage(){

        return message;

    }

    public static void main(String[] args){Rummy rummy = new Rummy(2);}

}
