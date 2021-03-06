package com.company;

import java.util.ArrayList;
import java.util.Scanner;

// TODO: Change all the confusing mod _player_nums to currentPlayerNum() function
// Add river, turn, flop
// Figure out how to test it properly and format text
// Add card comparisons for different hands


public class HoldEm
{
    Deck deck;
    private ArrayList<Player> players;
    private ArrayList<Card> board;
    private int _player_num;
    private int chip = 0;
    private int pot;
    private int highbet;
    private int smallBlind;
    private int bigBlind;
    private int stage;
    private boolean over;
    private boolean betting;
    private int i;
    private int[] bets;
    private boolean[] acted;

    private Scanner in = new Scanner(System.in);

    public HoldEm(int player_num) {
        _player_num = player_num;
        deck = new Deck(false);
        players = new ArrayList<Player>();
        board = new ArrayList<Card>();
        bets = new int[player_num];
        acted = new boolean[player_num];
        // The main pot
        pot = 0;
        // The current high bet
        highbet = 0;
        // Small / big blind constants
        smallBlind = 1;
        bigBlind = 2;
        // Stage, not being used maybe later
        stage = 1;
        // Initialize the players, add them to player ArrayList
        for (int i = 0; i < player_num; i++) {
            Player p = new Player();
            p.active = true;

            out("Enter the player's name.");

            p.setName(in.nextLine());

            p.setScore(100); // Start with 100 money

            players.add(p);
            // Initialize the bets amount to 0.
            bets[i] = 0;
            acted[i] = false;
        }
        // Boolean to control game finish
        over = false;
        while (!over) {
            out("============================");
            // Start the hand.
            i = 0;

            // Shuffle the deck.
            deck.shuffle();

            // Verbalize who has the dealer chip.
            out(String.format("Player %s is the dealer.", currentPlayerName()));

            // First  phase of holdem, big blind & small blind
            i++;
            out(String.format("$1 small blind from Player %s's stack.", currentPlayerName()));
            place_bet(players.get((chip + 1) % player_num), smallBlind);
            i++;
            out(String.format("$2 big blind from Player %s's stack.", currentPlayerName()));
            place_bet(players.get((chip + 2) % player_num), bigBlind);
            i++;

            // Next phase of holdem, deal 2 cards to every player, one at a time, starting w/ small blind position.
            for (int j = 1; j < (player_num * 2) + 1; j++) {
                // Give each player a card. With respect to the dealer chip.
                players.get((chip + j) % player_num).giveCard(deck.removeTop());
            }

            //  Betting starts, this is where user input begins.
            for (i = i; !bettingRoundOver(); i++) {

                out("__________________________________");

                // Play the turn. i is a class variable, should persist without passing to play turn function
                play_turn(players.get((chip + i) % _player_num));

            }

            // FLOP
            highbet = 0;
            i = chip + 1;
            out("Flop:");
            for (int j = 0; j < 3; j++) {
                board.add(deck.removeTop());
            }
            for (i = i; !bettingRoundOver(); i++) {
                out("_____________________");
                out("Board:");
                showBoard();

                play_turn(players.get(currentPlayerNum()));
            }

            // TURN
            highbet = 0;
            i = chip + 1;
            out("Turn:");
            board.add(deck.removeTop());
            for (i = i; !bettingRoundOver(); i++) {
                out( "________________");
                out("Board:");
                showBoard();

                play_turn(players.get(currentPlayerNum()));
            }

            // RIVER
            highbet = 0;
            i = chip + 1;
            out("Turn:");
            board.add(deck.removeTop());
            for (i = i; !bettingRoundOver(); i++) {
                out( "________________");
                out("Board:");
                showBoard();

                play_turn(players.get(currentPlayerNum()));
            }

            // SHOW DOWN

            // Have to make card ranking system...
            // 


        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void showBoard() {
        for (int j = 0; j < board.size(); j++) {
            out(board.get(j).toString());
        }
    }

    private void collectBets() {
        pot = calcPot();
        for (int j = 0; j < bets.length; j++) {
            bets[j] = 0;
            acted[j] = false;
        }
    }

    private boolean bettingRoundOver() {
        int val = bets[0];
        for (int j = 0; j < bets.length; j++) {
            if ((bets[j] != val && players.get(j).active) || !acted[j]) {
                return false;
            }
        }
        collectBets();
        return true;
    }

    private int currentPlayerNum() {
        return ((chip + i)%_player_num);
    }

    private String currentPlayerName() { return players.get(currentPlayerNum()).getName(); }

    public void out(String msg) {
        System.out.println(msg);
    }

    private void place_bet(Player p, int amount) {
        // Take a player bet, put it on the board.
        highbet = Math.max(highbet, amount - bets[currentPlayerNum()]);
        if (p.getScore() < amount) {
            bets[(chip + i)%_player_num] += p.getScore();
            p.setScore(0);
        } else {
            bets[(chip + i)%_player_num] += amount;
            p.setScore(p.getScore() - amount);
        }
    }

    private int calcPot() { // Return the value of the pot plus all the bets on the table. Helps decision making.
        int value = pot;
        for (int j = 0; j < bets.length; j++) {
            value += bets[j];
        }
        return value;
    }

    private void show_hole_cards(Player p) {
        out("Your hole cards: ");
        for (int j = 0; j < 2; j++) {
            System.out.println(p.getCards().get(j));
        }
    }

    private void play_turn(Player p) {

        acted[currentPlayerNum()] = true;

        // If they are folded then they don't play
        if (!p.active) return;

        // If they're all in they can't do anything anyway.
        if (p.getScore() == 0) return;

        out(String.format("Player %ss turn.", currentPlayerName()));

        show_hole_cards(p);

        // Player options: check, bet(amount), fold
        out(String.format("You have $%d in chips. It's your turn.", p.getScore()));
        out(String.format("The pot size is $%d.", calcPot()));
        out(String.format("Your current bet is $%d. The high best is $%d. What do you want to do?", bets[currentPlayerNum()], highbet));

        // Player can only check if their bet is greater than or equal to the high bet.
        boolean canCheck = false;

        if (highbet <= bets[(chip + i)%_player_num]) {
            canCheck = true;
        }

        // Time to take input
        boolean valid = false;
        while (!valid) {


            String input = in.nextLine().toLowerCase();

            // F is for fold
            if (input.equals("f")) {
                p.active = false;
                out(String.format("Player %d folded!", currentPlayerNum()));
                return;
            }

            // C is for check
            else if (input.equals("c")) {
                if (!canCheck) {
                    out("You can't check!");
                    continue;
                }
                out(String.format("Player %d checked!", (i % _player_num)));
                return;
            }

            // B is for bet, this is where the magic happens
            else if (input.equals("b")) {
                out("How much to bet? (Total, including current bet)");
                boolean validbet = false;
                int bet = 0;
                while (!validbet) {
                    try {
                        bet = Integer.parseInt(in.nextLine());
                        if (bet - bets[currentPlayerNum()]<= p.getScore() && bet >= highbet) {
                            validbet = true;
                        } else {
                            out("Invalid bet. Try again!");
                            continue;
                        }
                    } catch (NumberFormatException ex) {
                        out("Invalid bet. Try again!");
                        continue;
                    }
                }
                // Now we've got a valid bet that is less than or equal to the player's amount.
                out(String.format("Player %s increases his bet to $%d!", currentPlayerName(), bet));
                place_bet(p, bet-bets[currentPlayerNum()]);
                return;
            } else {
                out("Invalid input! Try again.");
            }
        }

    }

    public static void main(String args[]) {
        HoldEm game = new HoldEm(4);
    }

}
