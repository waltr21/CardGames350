package com.company;

import java.util.ArrayList;

/******************************************************************************
 * The underlying code for a game of Rummy. Currently uses text based input
 * and output for the player.
 *
 * @author Tanner Wysocki
 *****************************************************************************/
public class Rummy {

    /** The game deck **/
    private final Deck deck;

    /** ArrayList of players **/
    private final ArrayList<Player> players;

    /** ArrayList to hold discarded cards **/
    private final ArrayList<Card> discard;

    /** Two dimensional array to hold player melds. First index is the player
     *  Second is the cards in the meld **/
    private final ArrayList<ArrayList<Card>> melds;

    /** The number of cards to be dealt **/
    private final int numCards;

    /** The number of players in the game **/
    private final int numPlayers;

    /** Integer for player's turn (0 - player 1, 1 - player 2, and so on) **/
    private int turn;

    /** Flag to check is game is still in progress **/
    private boolean gameOver = false;

    private String message;

    /** Array to hold scores **/
    private final int[] scores;

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
            melds.add(new ArrayList<>());
        }
        deck.shuffle();
        //Deals the players their hands.
        giveHand();
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
     * Method to give players their initial hands at the start of the round.
     *************************************************************************/
    private void giveHand(){

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
            else {
                valid = meld.get(i).getValue() == meld.get(i - 1).getValue();
            }
            i++;

        }
        return valid;

    }

    /**************************************************************************
     * Method to calculate the final scores for each player at the end of the
     * round. Adds up each card in the meld and deducts each remaining card
     * from the player's hand.
     *************************************************************************/
    private void tallyScore(){

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
     * Getter for the current turn.
     *
     * @return Int representing the current player's turn
     *************************************************************************/
    public int getTurn() {
        return turn+1;
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
        StringBuilder cardList = new StringBuilder();
        temp = p.getCards();
        for (Card aTemp : temp) {
            cardList.append(aTemp.toString()).append("\n");
        }
        return cardList.toString();

    }

    public String toString(ArrayList<Card> hand){

        StringBuilder cardList = new StringBuilder();
        for (Card aTemp : hand) {
            cardList.append(aTemp.toString()).append("\n");
        }
        return cardList.toString();

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

}
