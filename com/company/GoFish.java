package com.company;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by RyanWalt on 10/5/17.
 */
public class GoFish {
    //Deck for the game
    private Deck gameDeck;
    //Players for the game
    private ArrayList<Player> players;
    //Number of cards for each player.
    private int numCards;
    //Turn index for the players.
    private int turn;
    //Scanner for testing in terminal game.
    private Scanner cons;

    /**
     * Constructor for the class.
     * @param numPlayers The number of players to play the game.
     *                   (anything above 4 will be set back to 4).
     */
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
        cons = new Scanner(System.in);
        gameDeck = new Deck(false);
        players = new ArrayList<>();

        //Add players to the game.
        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }

        resetGame();
        startGame();
    }

    /**
     * The start of the game. Continues to play until the deck is
     * empty.
     */
    public void startGame(){
        while (gameDeck.getSize() > 0) {
            System.out.println("Player " + (turn + 1) + " it is your turn!");
            takeTurn(players.get(turn));
            System.out.println("");
        }

        System.out.println("The game is complete!");
        int high = -1;
        int indexWin = -1;
        //Get the winner with the most matches.
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getCompleteNum() > high) {
                high = players.get(i).getCompleteNum();
                indexWin = i;
            }
        }

        System.out.println("Winner is: " + (indexWin + 1));
    }

    /**
     * Takes the turn of a single user. Makes sure that the user
     * has made a valid move (according to the rules of go fish)
     * @param p The player to take a turn for.
     */
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
                    //Continuously give the player each card of the requested type.
                    while (checkValid((players.get(index)), tempCard)) {
                        Card transfer = players.get(index).takeCardFish(tempCard);
                        players.get(turn).giveCard(transfer);
                    }
                    validMove = true;
                }
            }
        }
        printAll();
        turn = index;

        for (Player tempPlayer : players) {
            tempPlayer.completeCount();
        }
    }

    /**
     * Checks to see if the specific player is holding a specific card.
     * @param p Player to search
     * @param c card to find
     * @return True if the user holds the card. Else false.
     */
    public boolean checkValid(Player p, Card c){
        ArrayList<Card> temp;
        temp = p.getCards();

        for (Card c1 : temp){
            if (sameValue(c, c1))
                return true;
        }

        return false;
    }

    /**
     * Resets the game, and sets the appropriate values to the
     * starting values in the constructor.
     */
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

    /**
     * Return true if the cards are the same value.
     * @param c A card to compare
     * @param c1 A card to compare
     * @return True or false depending on if the cards hold the
     * same value.
     */
    public boolean sameValue(Card c, Card c1){
        if (c.getValue() == c1.getValue())
            return true;
        else
            return false;
    }


    /**
     * Helper method to print the cards each user holds. (temporary)
     */
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

    /**
     * Creates a printable string of the cards the user is holding.
     * @param p Player to get cards from
     * @return String with the card Value and Suit
     */
    public String getCardsString(Player p){
        ArrayList<Card> temp = new ArrayList<>();
        String cardList = "";
        temp = p.getCards();
        for (int i = 0; i < temp.size(); i++){
            cardList += "Value: " + temp.get(i).getValue() + "Suit: " +
                    temp.get(i).getSuit() + "\n";
        }

        return cardList;
    }


    public static void main(String args[]){
        GoFish g = new GoFish(2);
    }

}
