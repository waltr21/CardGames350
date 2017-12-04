package com.company;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Class for the Go Fish game. Holds all of the
 * logic for taking turns, keeping score, swapping
 * cards, etc...
 */
public class GoFish{
    //Deck for the game
    private final Deck gameDeck;
    //Players for the game
    private final ArrayList<Player> players;
    //Number of cards for each player.
    private final int numCards;
    //Turn index for the players.
    private int turn;
    //Scanner for testing in terminal game.
    private final Scanner cons;
    //message to update the game.
    private String message;
    //ArrayList for the possible AI in the game.
    private ArrayList<GoFishAI> AI;
    //Number of real players in the game.
    private int numPlayers;

    /**
     * Constructor for the class.
     * @param numPlayers The number of players to play the game.
     *                   (anything above 4 will be set back to 4).
     */
    public GoFish(int numPlayers){
        //Cap the number of players
        if (numPlayers > 4)
            numPlayers = 4;

        else if (numPlayers < 1)
            numPlayers = 1;

        this.numPlayers = numPlayers;

        //Set initial card amount.
        numCards = 5;


        turn = 0;
        cons = new Scanner(System.in);
        gameDeck = new Deck(false);
        players = new ArrayList<>();
        message = "Starting game ...";
        AI = new ArrayList<>();


        //Add AI to the game.
        int AIIndex = numPlayers;
        for (int i = 0; i < 4-numPlayers; i++){
            AI.add(new GoFishAI(5, AIIndex));
            AIIndex++;
        }

        //Add players to the game.
        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }
        //Add the AI players into the game.
        for (GoFishAI b : AI){
            players.add(b.getPlayer());
        }

        resetGame();
        //startGame();
        printAll();

    }

    /**
     * The start of the game. Continues to play until the deck is
     * empty. (Not for GUI use!!!!)
     */
    private void startGame(){
        while (gameDeck.getSize() > 0) {
            System.out.println("Player " + (turn + 1) + " it is your turn!");

            //System.out.print("Player to request a card from: ");
            int indexS = Integer.parseInt(cons.nextLine());

            //System.out.print("Value to request: ");
            int valueS = Integer.parseInt(cons.nextLine());

            //boolean tempValid = takeTurn(indexS, valueS);
//            if (!tempValid){
//                System.out.println("Not valid turn! Try again!");
//            }

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
     * Takes a single turn for a player
     * @param playerIndex Which player to choose a card from (1-4)
     * @param requestVal Values of the card to request
     * @return True if the turn was valid. Fales if not.
     */
    public void takeTurn(int playerIndex, int requestVal){
        int index = playerIndex -1;

        if (index >= players.size() || index < 0 || index == turn){
            message = "That wasn't a valid player! Try again...";
            return;
        }
        else if (players.get(index).getCards().size() < 1){
            message = "This player is out of cards! Try again...";
            return;
        }

        else {
            Card tempCard = new Card(requestVal, -1);

            if (!checkValid(getPlayer(), tempCard)) {
                message = "You don't have this card! Request a different card. Try again...";
                return;
            } else if (!checkValid(players.get(index), tempCard)) {
                message = "Player " + (turn+1) + " Go Fish!\n";
                Card fish = gameDeck.removeTop();
                players.get(turn).giveCard(fish);
                if (fish.getValue() == requestVal) {
                    index = turn;
                    message = "You got the card you wanted! The turn continues.";
                }

            } else {
                message = "Player " + (turn+1) + " takes the cards from player " + (playerIndex) + "!";

                //Continuously give the player each card of the requested type.
                while (checkValid((players.get(index)), tempCard)) {
                    Card transfer = players.get(index).takeCardFish(tempCard);
                    players.get(turn).giveCard(transfer);
                }

                for (GoFishAI b : AI){
                    b.removeMemory(index, tempCard.getValue());
                }

            }

        }

        //update the memory for the bots.
        setMem(turn, requestVal);
        turn = index;
        players.forEach(Player::completeCount);

        printAll();

        if (turn > numPlayers - 1){
            System.out.println("Bot turn is about to happen!");
            takeBotTurn();
        }

    }

    public void takeBotTurn(){
        GoFishAI currBot = getCurrentAI();
        System.out.println("Taking bot turn: " + currBot.getIndex());

        ArrayList<Integer> weightedCards = currBot.weightCards();
        boolean done = false;
        for (int val : weightedCards){
            if (!done) {
                for (Pair slot : currBot.getMemory()) {
                    if (val == slot.getVal()) {
                        System.out.println("Found card in memory...");
                        Card tempCard = new Card(val, -1);
                        //Take all of the cards a user has
                        while (checkValid((players.get(slot.getIndex())), tempCard)) {
                            Card transfer = players.get(slot.getIndex()).takeCardFish(tempCard);
                            players.get(turn).giveCard(transfer);
                            System.out.println("Bot taking: " + transfer.getValue() + " From: " + slot.getIndex());
                        }
                        //Set the turn.
                        turn = slot.getIndex();
                        done = true;
                    }
                }
            }
            else
                break;
        }

        //If the bot can't find any of the cards they want in their memory
        //then they take the highest weighted card and take it from a random player.
        int randomIndex = 0;
        if (!done){
            Random r = new Random();
            randomIndex = r.nextInt(4);
            System.out.println("RANDOM VAL: " + randomIndex);

            //Makes sure the bot cant pick from itself.
            while (randomIndex == currBot.getIndex()){
                randomIndex = r.nextInt(4);
            }

            Card tempCard = new Card(weightedCards.get(0), -1);
            System.out.println("No cards of bot have been found in memory. Trying player: "
                    + randomIndex + "Card: " + tempCard.getValue());

            while (checkValid((players.get(randomIndex)), tempCard)) {
                Card transfer = players.get(randomIndex).takeCardFish(tempCard);
                players.get(turn).giveCard(transfer);
                done = true;
                System.out.println("Bot taking: " + transfer.getValue());
            }
            if (done)
                turn = randomIndex;
        }

        //If the bot did not get the card from the player, then we have a
        //go fish.
        if (!done){

            Card fish = gameDeck.removeTop();
            System.out.println("Go fish for the bot. Got: " + fish.getValue());
            players.get(turn).giveCard(fish);
            if (fish.getValue() == weightedCards.get(0)){
                System.out.println("Bot fished the right card... Continues...");
                message = "Bot fished the right card... Continues...";
            }
            else{
                turn = randomIndex;
            }
        }

        printAll();
        //If the turn passes to another bot repeat the same process.
        if (turn > numPlayers - 1){
            takeBotTurn();
        }
    }

    public GoFishAI getCurrentAI(){
        for (GoFishAI bot : AI){
            if (bot.getIndex() == turn)
                return bot;
        }
        //Technically shouldn't happen.
        return null;
    }

    public void setMem(int playerIndex, int requestVal){
        for (GoFishAI bot : AI){
            bot.addMemory(playerIndex, requestVal);
        }
    }

    /**
     * Checks to see if the specific player is holding a specific card.
     * @param p Player to search
     * @param c card to find
     * @return True if the user holds the card. Else false.
     */
    private boolean checkValid(Player p, Card c){
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

        for (Player p : players){
            p.resetCards();
        }

        //Give each player X cards and remove them from the deck.
        for (int i = 1; i <= numCards; i++){
            for (Player p : players) {
                p.giveCard(gameDeck.removeTop());
            }
        }

        //printAll();
    }

    /**
     * Return true if the cards are the same value.
     * @param c A card to compare
     * @param c1 A card to compare
     * @return True or false depending on if the cards hold the
     * same value.
     */
    private boolean sameValue(Card c, Card c1){
        return c.getValue() == c1.getValue();
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
     * @return the size of the game deck.
     */
    public int getGameDeckSize(){
        return gameDeck.getSize();
    }

    /**
     * @return the current message of the game.
     */
    public String getMessage(){
        return message;
    }

    /**
     * Gets the current player object that is up to play
     * @return Player object
     */
    public Player getPlayer(){
        return players.get(turn);
    }

    /**
     * Gets the integer value of the player who is up
     * @return Player number integer.
     */
    public int getPlayerIndex(){
        return turn + 1;
    }

    /**
     * Gets the message of the player who is up
     * @return message String.
     */
    public String getTurnMessage(){
        return "Player " + (turn + 1) + " it is your turn!";
    }

    /**
     * Creates a printable string of the cards the user is holding.
     * @param p Player to get cards from
     * @return String with the card Value and Suit
     */
    public String getCardsString(Player p){
        ArrayList<Card> temp;
        String cardList = "";
        p.sortCards();
        temp = p.getCards();
        for (Card aTemp : temp) {
            cardList += aTemp.toString() + "\n";
        }
        return cardList;
    }

    /**
     * Gets the players in the game. (used for testing)
     * @return Array List of players.
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     * Gets the current turn
     * @return int of the turn value.
     */
    public int getTurn(){
        return turn;
    }

    public static void main(String args[]){
        GoFish g = new GoFish(2);
        g.startGame();
    }

}
