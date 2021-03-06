package com.company;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class for the Go Fish game. Holds all of the
 * logic for taking turns, keeping score, swapping
 * cards, etc...
 */
public class GoFish{
    //Deck for the game
    private Deck gameDeck;
    //Players for the game
    private ArrayList<Player> players;
    //Number of cards for each player.
    private int numCards;
    //Turn index for the players.
    private int turn;
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
        resetGame(numPlayers);
    }

    /**
     * Resets the game, and sets the appropriate values to the
     * starting values in the constructor.
     */
    public void resetGame(int numPlayers){
        if (numPlayers > 4)
            numPlayers = 4;
        else if (numPlayers < 1)
            numPlayers = 1;

        this.numPlayers = numPlayers;

        //Set initial card amount.
        numCards = 5;

        turn = 0;
//        cons = new Scanner(System.in);
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
     * Takes a single turn for a player
     * @param playerIndex Which player to choose a card from (1-4)
     * @param requestVal Values of the card to request
     * @return True if the turn was valid. Fales if not.
     */
    public boolean takeTurn(int playerIndex, int requestVal){
        int index = playerIndex -1;

        if (index >= players.size() || index < 0 || index == turn){
            message = "That wasn't a valid player! Try again...";
            return true;
        }
        else if (players.get(index).getCards().size() < 1){
            message = "This player is out of cards! Try again...";
            return true;
        }

        else {
            Card tempCard = new Card(requestVal, -1);

            if (!checkValid(getPlayer(), tempCard)) {
                message = "You don't have this card! Request a different card. Try again...";
                return true;
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


        if (turn > numPlayers - 1){
            return false;
        }
        return true;
    }

    /**
     * Takes the entire turn for the bot. Checks their memory for their ideal cards,
     * or draws a random card from a player if they can't find a card in memory.
     * @return true if it is stil the bots turn. False if it is a player turn.
     */
    public boolean takeBotTurn(){
        GoFishAI currBot = getCurrentAI();
        if (currBot == null)
            return false;
        //System.out.println("Taking bot turn: " + currBot.getIndex());
        Card transfer;

        ArrayList<Integer> weightedCards = currBot.weightCards();
        boolean done = false;
        for (int val : weightedCards){
            if (!done) {
                for (Pair slot : currBot.getMemory()) {
                    if (val == slot.getVal()) {
                        //System.out.println("Found card in memory...");
                        Card tempCard = new Card(val, -1);
                        //Take all of the cards a user has
                        while (checkValid((players.get(slot.getIndex())), tempCard)) {
                            transfer = players.get(slot.getIndex()).takeCardFish(tempCard);
                            players.get(turn).giveCard(transfer);
                            //System.out.println("Bot has taken a " + transfer.getValue() + " from player: " + slot.getIndex());
                            message = "Bot has taken " + transfer.getValue() + " from player " + (slot.getIndex()+1) + "!";
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

            //Only take cards from the real players (makes the game harder for them)
            randomIndex = r.nextInt(numPlayers);

            //Create a temporary card from the most demanded weight of the bot.
            Card tempCard = new Card(weightedCards.get(0), -1);

            while (checkValid((players.get(randomIndex)), tempCard)) {
                transfer = players.get(randomIndex).takeCardFish(tempCard);
                players.get(turn).giveCard(transfer);
                done = true;
                message = "Bot has taken a " + transfer.getValue() + " From player " + (randomIndex+1) + "!";
            }
            if (done)
                turn = randomIndex;
        }

        //If the bot did not get the card from the player, then we have a
        //go fish.
        if (!done){
            message = "Bot requests a " + weightedCards.get(0) + " from player: " + (randomIndex+1);
            Card fish = gameDeck.removeTop();
            players.get(turn).giveCard(fish);
            if (fish.getValue() == weightedCards.get(0)){
                message += "\nBot fished the right card... His turn continues.";
            }
            else{
                turn = randomIndex;
                message += "\nGo fish! The bot draws a card from the deck.";
            }
        }

        //If the turn passes to another bot repeat the same process.
        if (turn > numPlayers - 1){
            return false;
        }
        return true;
    }

    /**
     * @return the current AI who is up for a turn.
     */
    public GoFishAI getCurrentAI(){
        for (GoFishAI bot : AI){
            if (bot.getIndex() == turn)
                return bot;
        }
        //Technically shouldn't happen.
        return null;
    }

    /**
     * Updates the memory of all of the bots in the game.
     * @param playerIndex The player who requested a card.
     * @param requestVal The value of the card the player requested.
     */
    private void setMem(int playerIndex, int requestVal){
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
        if (turn+1 > numPlayers){
            return "Bot " + (turn+1);
        }
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

    public boolean gameWon(){
        if (getGameDeckSize() < 1){
            return true;
        }
        return false;
    }

    public int getWinner(){
        int max = 0;
        int ind = -1;
        for (int i = 0; i < players.size(); i++){
            Player p = players.get(i);
            if (max < p.getCompleteNum()) {
                max = p.getCompleteNum();
                ind = i;
            }
        }
        return ind+1;
    }

}
